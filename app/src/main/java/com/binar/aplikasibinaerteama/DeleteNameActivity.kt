package com.binar.aplikasibinaerteama

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.binar.aplikasibinaerteama.databinding.DeletenameBinding



class DeleteNameActivity : Activity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private var playerList: ArrayList<String>? = null
    private var updateList: ArrayList<UpdateObj>? = null
    private lateinit var ch: Array<CheckBox?>

    private lateinit var binding: DeletenameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeletenameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        playerList = intent.getStringArrayListExtra("playerList")
        if (intent.getSerializableExtra("updateList") != null) {
            updateList = intent.getSerializableExtra("updateList") as ArrayList<UpdateObj>?
        }
        ch = arrayOfNulls(playerList!!.size)
        initializeViews()
    }



    private fun initializeViews() {
        for (i in playerList!!.indices) {
            ch[i] = CheckBox(this)
            ch[i]!!.setOnCheckedChangeListener(this)
            ch[i]!!.id = i
            ch[i]!!.text = playerList!![i]
            ch[i]!!.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.rtg_text_size)
            )
            binding.layoutCheckBoxes.addView(ch[i])
        }


        val delete = findViewById<View>(R.id.bDelete) as Button
        binding.tvDelNameTotalPlayers.text = playerList!!.size.toString()
        binding.tvDelNameCurrentPreset.text = intent.getStringExtra("currentPreset")
        binding.bSelectAll.setOnClickListener(this)
        delete.setOnClickListener(this)
    }

    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        for (cb in ch) {

            if (!cb!!.isChecked) {
                binding.bSelectAll.text = "Select All"
                return
            }
        }
        binding.bSelectAll.text = "Deselect All"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bSelectAll -> if (binding.bSelectAll.text.toString() == "Select All") {
                for (c in ch) {
                    c!!.isChecked = true
                }
                binding.bSelectAll.text = "Deselect All"
            } else if (binding.bSelectAll.text.toString() == "Deselect All") {
                for (c in ch) {
                    c!!.isChecked = false
                }
                binding.bSelectAll.text = "Select All"
            }
            R.id.bDelete -> {
                val yGravity = 270
                val gravityType = Gravity.TOP
                var fatalityBool = false
                for (c in ch) {
                    if (c!!.isChecked && c.visibility == CheckBox.VISIBLE) {
                        playerList!!.remove(c.text.toString())
                        binding.tvDelNameTotalPlayers.text =
                            playerList!!.size.toString() //update total
                        if (updateList != null) {
                            updateList!!.add(
                                UpdateObj(
                                    "delete",
                                    c.text.toString()
                                )
                            )
                        }
                        binding.layoutCheckBoxes.removeView(c) //remove checkbox from view
                        c.visibility = View.GONE
                        if (!fatalityBool) {
                            fatalityBool = true
                        }
                    }
                }
                if (fatalityBool) {
                    val t = Toast.makeText(applicationContext, "Name deleted.", Toast.LENGTH_SHORT)
                    t.setGravity(gravityType, 0, yGravity)
                    t.show()

                }
                if (binding.layoutCheckBoxes.childCount == 0) {
                    returnDataToMain()
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        // TODO Auto-generated method stub
        returnDataToMain()
        super.onBackPressed()
    }

    private fun returnDataToMain() {
        val returnToMain = Intent()
        returnToMain.putStringArrayListExtra("playerList", playerList)
        if (updateList != null) {
            returnToMain.putExtra("updateList", updateList)
        }
        setResult(RESULT_OK, returnToMain)
    }
}