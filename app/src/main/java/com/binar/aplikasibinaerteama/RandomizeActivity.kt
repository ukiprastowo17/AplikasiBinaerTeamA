package com.binar.aplikasibinaerteama

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.binar.aplikasibinaerteama.databinding.RandomizeBinding
import java.util.*

class RandomizeActivity : AppCompatActivity() {
    private val ERROR_TAG = "Randomize"
    private lateinit var binding: RandomizeBinding
    private var randomizeThread: Thread? = null


    private val currentPreset: String? by lazy {
        intent.getStringExtra("currentPreset")
    }

    private val playerList: MutableList<String>? by lazy {
        intent.getStringArrayListExtra("playerArrList")
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
        binding.tvRandomizeCurrentPreset.text = currentPreset
        binding.tvRandomizeTotalPlayers.text = playerList?.size.toString()
        binding.bRandomizeAgain.setOnClickListener(View.OnClickListener { startRandomizeThread() })
    }

    private fun randomize() {
        var team = 0
        var playerNumCounter = 1
        val llTeam = numberOfTeams?.let { arrayOfNulls<LinearLayout>(it) }
        llTeam!!.indices.forEach { i ->
            llTeam!![i] = LinearLayout(this)
            llTeam[i]!!.id = i
            llTeam[i]!!.orientation = LinearLayout.VERTICAL
            val tvTeamNum = TextView(this)
            tvTeamNum.text = Html.fromHtml("<u><b>Team " + (i + 1) + "</b></u>")
            tvTeamNum.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            lp.gravity = Gravity.CENTER_HORIZONTAL
            if (i > 1) {
                lp.topMargin = 50
            }
            tvTeamNum.layoutParams = lp
            if (team == 0) {
                binding.llTeamLeft.addView(llTeam[i])
                llTeam[i]!!.addView(tvTeamNum)
                team = 1
            } else if (team == 1) {
                binding.llTeamRight.addView(llTeam[i])
                llTeam[i]!!.addView(tvTeamNum)
                team = 0
            }
        }
        team = 0

        playerList!!.shuffle()
        for (i in playerList!!.indices) {
            val playerName = playerList!![i]
            val tvName = TextView(this)
            tvName.text = (Integer.toString(playerNumCounter) + ". " + playerName).replaceFirst(
                "\\s".toRegex(),
                "\u00A0"
            )
            tvName.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
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
    }


    private fun startRandomizeThread() {
        binding.bRandomizeAgain.setVisibility(View.INVISIBLE)
        randomizeThread = Thread {
            synchronized(this@RandomizeActivity) {
                try {
                    val rand = Random()
                    for (i in 0 until rand.nextInt(3) + 5) {
                        if (i != 0) {
                            Thread.sleep(330)
                        }

                        this@RandomizeActivity.runOnUiThread(Runnable {
                            binding.llTeamLeft.removeAllViews()
                            binding.llTeamRight.removeAllViews()
                            randomize()
                        })
                    }
                } catch (e: InterruptedException) {
                    Log.e(ERROR_TAG, e.message!!)
                    Log.e(ERROR_TAG, Log.getStackTraceString(e))
                } finally {
                    binding.bRandomizeAgain.post(Runnable {
                        binding.bRandomizeAgain.visibility = View.VISIBLE
                    })
                }
            }
        }
        randomizeThread!!.start()
    }

    override fun onStop() {
        if (randomizeThread != null) randomizeThread!!.interrupt()
        super.onStop()
    }
}