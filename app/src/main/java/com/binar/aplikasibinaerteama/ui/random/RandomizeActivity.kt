package com.binar.aplikasibinaerteama.ui.random

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.binar.aplikasibinaerteama.databinding.RandomizeBinding
import java.util.*
import android.graphics.Typeface
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.base.GenericViewModelFactory
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.data.room.entity.ResultData
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.model.ResultModel
import com.binar.aplikasibinaerteama.ui.member.MemberViewModel
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class RandomizeActivity : AppCompatActivity() {
    private val ERROR_TAG = "Randomize"
    private lateinit var binding: RandomizeBinding
    private var randomizeThread: Thread? = null
    var playerNameData: String? = null
    var dataTeam: String? = null
    lateinit var currentDate : String

    lateinit var dataArray : ArrayList<ResultData>

    private val viewModel: ResultViewModel by lazy {
        GenericViewModelFactory(ResultViewModel(ServiceLocator.provideLocalRepository(this))).create(
            ResultViewModel::class.java
        )
    }

    private val currentPreset: String? by lazy {
        intent.getStringExtra("name_group")
    }

    private val playerList: MutableList<String>? by lazy {
        intent.getStringArrayListExtra("data")
    }




    private val numberOfTeams: Int? by lazy {
        intent.getIntExtra("numberOfTeams", 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RandomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initializeViews()
        startRandomizeThread()
    }

    private fun initializeViews() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        binding.tvRandomizeCurrentPreset.text = currentPreset
        binding.tvRandomizeTotalPlayers.text = playerList?.size.toString()
        Log.d("dataok", playerList.toString())
        binding.btnBack.setOnClickListener(View.OnClickListener { finish()})
    }

    @SuppressLint("ResourceAsColor")
    private fun randomize() {
        var team = 0

        var playerNumCounter = 1
        val llTeam = numberOfTeams?.let { arrayOfNulls<LinearLayout>(it) }

        llTeam!!.indices.forEach { i ->
            llTeam!![i] = LinearLayout(this)
            llTeam[i]!!.id = i
            llTeam[i]!!.orientation = LinearLayout.VERTICAL
            llTeam[i]!!.marginTop

            val tvTeamNum = TextView(this)
            tvTeamNum.text = "TEAM-" + (i + 1).toString()
            Log.d("datamasterteam",tvTeamNum.text.toString())
            tvTeamNum.setTypeface(null, Typeface.BOLD)
            tvTeamNum.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            lp.topMargin = 50

//            if (i > 1) {
//                lp.topMargin = 50
//            }
            tvTeamNum.layoutParams = lp
                binding.llTeamLeft.addView(llTeam[i])
                llTeam[i]!!.addView(tvTeamNum)
                team = 1
        }
        team = 0




        playerList!!.shuffle()
        for (i in playerList!!.indices) {
            var playerName = playerList!![i]
            val tvName = TextView(this)

            playerNameData = playerName.toString()
            dataTeam = (team + 1).toString()

            tvName.text = (playerNumCounter.toString() + ". " + playerName +"-"+ team.toString() )

                    val data = playerNameData?.let { ResultData(nameResult = currentDate.toString(), dateResult = currentDate.toString(), memberResult= it, teamResult=dataTeam.toString(), groupNameResult = currentPreset.toString()) }
        if (data != null) {
            viewModel.insertResult(data)
        }




            Log.d("datamastermember",tvName.text.toString())
            tvName.setSingleLine()
            tvName.ellipsize = TextUtils.TruncateAt.END
            if (numberOfTeams!! > 3 && i == playerList!!.size - 1 && (team + 1) % 2.0f != 0f) {
                llTeam[numberOfTeams!! - 1]!!.addView(tvName)
            } else {
                llTeam[team]!!.addView(tvName)
            }
            team++
            if (team == numberOfTeams) {
                team = 0
                playerNumCounter++
            }
        }

//        val data = playerNameData?.let { ResultData(nameResult = currentDate.toString(), dateResult = currentDate.toString(), memberResult= it, teamResult=dataTeam.toString(), groupNameResult = currentPreset.toString()) }
//        if (data != null) {
//            viewModel.insertResult(data)
//        }

    }


    private fun startRandomizeThread() {
//        binding.bRandomizeAgain.visibility = View.INVISIBLE
        randomize()

//        randomizeThread = Thread {
//            synchronized(this@RandomizeActivity) {
//                try {
//                    val rand = Random()
//                    for (i in 0 until rand.nextInt(3) + 5) {
//                        if (i != 0) {
//                            Thread.sleep(330)
//                        }
//
//                        this@RandomizeActivity.runOnUiThread(Runnable {
//                            binding.llTeamLeft.removeAllViews()
//                            randomize()
//
//                        })
//                    }
//                } catch (e: InterruptedException) {
//                    Log.e(ERROR_TAG, e.message!!)
//                    Log.e(ERROR_TAG, Log.getStackTraceString(e))
//                } finally {
//                    binding.bRandomizeAgain.post(Runnable {
//
//
//
//
//
//                        binding.bRandomizeAgain.visibility = View.VISIBLE
//                    })
//                }
//            }
//        }
//        randomizeThread!!.start()
    }

    override fun onStop() {
        if (randomizeThread != null) randomizeThread!!.interrupt()
        super.onStop()
    }
}