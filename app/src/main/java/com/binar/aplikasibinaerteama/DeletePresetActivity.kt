package com.binar.aplikasibinaerteama

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.binar.aplikasibinaerteama.databinding.DeletepresetBinding
import com.binar.aplikasibinaerteama.dialogs.DeleteCurrentPresetDialog


class DeletePresetActivity : Activity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private var currentPreset: String? = null
    private lateinit var ch: Array<CheckBox?>
    private var cbCurrPreset: CheckBox? = null
    private var db: DbHelper? = null
    private var i: Intent? = null
    private val yGravity = 270
    private val gravityType = Gravity.TOP
    private lateinit var binding: DeletepresetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeletepresetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        loadPresets()
        i = Intent()
        if (intent.getStringExtra("currentPreset") != null) {
            currentPreset = intent.getStringExtra("currentPreset")
            binding.tvPresetCurrentPreset.text = currentPreset
        }
    }



    private fun initializeViews() {


        binding.bPresetSelectAll.setOnClickListener(this)
        binding.bPresetDelete.setOnClickListener(this)

    }

    private fun loadPresets() {
        db = DbHelper(this)
        db!!.open()
        val presetNames: Array<String> = db!!.presetNames
        db!!.close()
        if (presetNames.isEmpty()) {
            binding.tvNoPresets.visibility = View.VISIBLE
        }
        ch = arrayOfNulls(presetNames.size)
        for (i in presetNames.indices) {
            ch[i] = CheckBox(this)
            ch[i]!!.setOnCheckedChangeListener(this)
            ch[i]!!.id = i
            ch[i]!!.text = presetNames[i]
            ch[i]!!.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            binding.layoutPresetCheckBoxes.addView(ch[i])
        }
    }

    override fun onCheckedChanged(view: CompoundButton, arg1: Boolean) {
        for (cb in ch) {
            //if even one checkbox is unchecked set it back to "Select All"
            if (!cb!!.isChecked) {
                binding.bPresetSelectAll.text = "Select All"
                return
            }
        }
        binding.bPresetSelectAll.text = "Deselect All"
    }

    override fun onClick(v: View) {
        var fatalityBool = false
        when (v.id) {
            R.id.b_preset_SelectAll -> if ( binding.bPresetSelectAll.text.toString() == "Select All") {
                for (c in ch) {
                    c!!.isChecked = true
                }
                binding.bPresetSelectAll.text = "Deselect All" //if select all change to deselect all
            } else if ( binding.bPresetSelectAll.text.toString() == "Deselect All") {
                for (c in ch) {
                    c!!.isChecked = false
                }
                binding.bPresetSelectAll.text = "Select All" //if deselect all change to select all
            }
            R.id.b_preset_Delete -> {
                db!!.open()
                db!!.dbBeginTransaction()
                for (c in ch) {
                    if (c!!.isChecked && c.visibility == CheckBox.VISIBLE) {
                        if (c.text.toString() == currentPreset && currentPreset != null) {
                            cbCurrPreset = c
                            val manager = fragmentManager
                            val delcurr = DeleteCurrentPresetDialog()
                            delcurr.show(manager, "delcurr") //adds fragment to the manager
                        } else {
                            db!!.deletePreset(c.text.toString())
                            binding.layoutPresetCheckBoxes.removeView(c) //remove checkbox from view
                            c.visibility = View.GONE
                            if (!fatalityBool) {
                                fatalityBool = true
                            }
                        }
                    }
                }
                if (fatalityBool) {
                    val t =
                        Toast.makeText(applicationContext, "Preset deleted.", Toast.LENGTH_SHORT)
                    t.setGravity(gravityType, 0, yGravity)
                    t.show()
                }
                db!!.dbSetTransactionSuccessful()
                db!!.dbEndTransaction()
                db!!.close()
                if (  binding.layoutPresetCheckBoxes.childCount == 0) {
                    returnDataToMain()
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        returnDataToMain()
        super.onBackPressed() //super does what the default back button does
    }

    private fun returnDataToMain() {
        setResult(RESULT_OK, i) //result code and intent
    }

    fun setDeleteCurrentPresetExtraTrue() { //will be called by dialog fragment if he deletes current preset
        i!!.putExtra("currPresetDeleted", true)
    }

    fun deleteCurrentPreset() {
        val dbh = DbHelper(this)
        dbh.open()
        dbh.deletePreset(cbCurrPreset!!.text.toString())
        dbh.close()
        binding.layoutPresetCheckBoxes.removeView(cbCurrPreset)
        cbCurrPreset!!.visibility = View.GONE
        val t = Toast.makeText(applicationContext, "Preset deleted.", Toast.LENGTH_SHORT)
        t.setGravity(gravityType, 0, yGravity)
        t.show()
        binding.tvPresetCurrentPreset.text = "None"
        setDeleteCurrentPresetExtraTrue()
        if (binding.layoutPresetCheckBoxes.childCount == 1 &&   binding.layoutPresetCheckBoxes.getChildAt(0).id == R.id.tvNoPresets) {
            returnDataToMain()
            finish()
        }
    }
}
