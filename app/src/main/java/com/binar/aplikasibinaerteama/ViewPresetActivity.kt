package com.binar.aplikasibinaerteama

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.binar.aplikasibinaerteama.databinding.ViewpresetBinding

class ViewPresetActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ViewpresetBinding
    private val presetName: String? by lazy {
        intent.getStringExtra("presetName")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewpresetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initializeViews()
    }

    private fun initializeViews() {
        val playerNames: ArrayList<String>
        binding.tvViewedPreset.text = presetName
        val db = DbHelper(this)
        db.open()
        playerNames = db.getPlayersByPreset(presetName)
        db.close()
        binding.tvViewTotalPlayers.text = playerNames.size.toString()
        if (playerNames.size == 0) {
            val tvViewName = TextView(this)
            tvViewName.text = "Empty"
            tvViewName.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            binding.llViewPreset.addView(tvViewName)
        } else {
            for (i in playerNames.indices) {
                val tvViewName = TextView(this)
                tvViewName.text = (i + 1).toString() + ". " + playerNames[i]
                tvViewName.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.rtg_text_size)
                )
                binding.llViewPreset.addView(tvViewName)
            }
        }
        binding.bViewPresetLoad.setOnClickListener(this@ViewPresetActivity)
    }

    override fun onClick(p0: View?) {
        // TODO Auto-generated method stub
        if (p0!!.getId() == R.id.bViewPresetLoad) {
            setResult(RESULT_OK)
            finish()
        }
    }
}