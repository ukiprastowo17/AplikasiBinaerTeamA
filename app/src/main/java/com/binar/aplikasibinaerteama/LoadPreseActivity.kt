package com.binar.aplikasibinaerteama

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.binar.aplikasibinaerteama.databinding.LoadpresetBinding
import com.binar.aplikasibinaerteama.dialogs.LoadSaveChangesDialog
import com.binar.aplikasibinaerteama.dialogs.LoadSavePresetAsDialog


 class LoadPreseActivity : Activity(), View.OnClickListener {

    private lateinit var binding: LoadpresetBinding
    private lateinit var radio: Array<RadioButton?>
    private val yGravity = 270
    private val gravityType = Gravity.TOP
    private var checkedRadio = 0
    var playersArrList: ArrayList<String>? = null
    private var changesMade = false

    private val dataCurrentPreset: String? by lazy {
        intent.getStringExtra("currentPreset")
    }
    private val dataPlayerList: ArrayList<String>? by lazy {
        intent.getStringArrayListExtra("playerList")
    }
    private val dataChangesMade: Boolean by lazy {
        intent.getBooleanExtra("changesMade", false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        binding = LoadpresetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViews()
        loadPresets()

        binding.tvLoadCurrrentPreset!!.text = dataCurrentPreset
        playersArrList = dataPlayerList
        changesMade = dataChangesMade
    }

    fun initializeViews() {
        binding.bLoadPreset.setOnClickListener(this)
        binding.bViewPreset.setOnClickListener(this)
    }

    fun loadPresets() {
        val presetNames: Array<String>
        val db = DbHelper(this)
        db.open()
        presetNames = db.presetNames
        if (presetNames.isEmpty()) {
            binding.tvEmptyPresets!!.visibility = View.VISIBLE
            return
        }
        radio = arrayOfNulls(presetNames.size)
        for (i in radio.indices) {
            radio[i] = RadioButton(this)
            radio[i]!!.text = presetNames[i]
            radio[i]!!.id = i
            radio[i]!!.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            binding.rgLoadPreset!!.addView(radio[i])
        }
        db.close()
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        checkedRadio =  binding.rgLoadPreset!!.checkedRadioButtonId

        if (checkedRadio < 0) {
            showPickAPresetToast()
            return
        }
        when (v.id) {
            R.id.bLoadPreset ->
                if (changesMade) {
                val manager = fragmentManager
                val loadscd = LoadSaveChangesDialog()
                loadscd.show(manager, "newscd")
            } else {
                loadPresetInMain()
            }
            R.id.bViewPreset -> {
                val presetName = radio[checkedRadio]!!.text.toString()
                val data = Intent(this, ViewPresetActivity::class.java)
                data.putExtra("presetName", presetName)
                startActivityForResult(data, 2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                2 -> if (changesMade) {
                    val manager = fragmentManager
                    LoadSaveChangesDialog().show(manager, "newscd")

                } else {
                    loadPresetInMain()
                }
            }
        }
    }

    fun loadPresetInMain() {
        val presetName = radio[checkedRadio]!!.text.toString()
        val data = Intent()
        data.putExtra("presetName", presetName)
        val db = DbHelper(this)
        db.open()
        data.putStringArrayListExtra(
            "playerNames",
            db.getPlayersByPreset(presetName)
        )
        db.close()
        setResult(RESULT_OK, data)
        val t = Toast.makeText(applicationContext, "Preset loaded.", Toast.LENGTH_SHORT)
        t.setGravity(gravityType, 0, yGravity)
        t.show()
        finish()
    }

    fun savePreset() {
        if (intent.getBooleanExtra("presetLoaded", false)) {
            val db = DbHelper(this)
            db.open()
            db.updatePreset(
                binding.tvLoadCurrrentPreset!!.text.toString(),
                intent.getSerializableExtra("updateList") as ArrayList<UpdateObj?>?
            )
            db.close()
            val t = Toast.makeText(applicationContext, "Preset saved.", Toast.LENGTH_SHORT)
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            loadPresetInMain()
        } else {
            val manager = fragmentManager
            val loadSavePreset = LoadSavePresetAsDialog()
            loadSavePreset.show(manager, "loadSavePreset")
        }
    }




    fun getCurrentPreset(): String? {
        return binding.tvLoadCurrrentPreset!!.text.toString()
    }

    private fun showPickAPresetToast() {
        val t = Toast.makeText(applicationContext, "Please pick a preset.", Toast.LENGTH_SHORT)
        t.setGravity(gravityType, 0, yGravity)
        t.show()
    }
}
