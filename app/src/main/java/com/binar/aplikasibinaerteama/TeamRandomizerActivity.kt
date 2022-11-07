package com.binar.aplikasibinaerteama

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.binar.aplikasibinaerteama.databinding.TeamrandBinding
import com.binar.aplikasibinaerteama.dialogs.NewSaveChangesDialog
import com.binar.aplikasibinaerteama.dialogs.NumberOfTeamsDialog
import com.binar.aplikasibinaerteama.dialogs.SavePresetAsDialog


class TeamRandomizerActivity : Activity(), View.OnClickListener,
    OnTouchListener {


    var playersArrList: ArrayList<String>? = null
    private var updateList: ArrayList<UpdateObj>? = null

    private lateinit var binding: TeamrandBinding

    private val playerList: MutableList<String>? by lazy {
        intent.getStringArrayListExtra("playerArrList")
    }


    var isPresetLoaded = false
        set(presetLoaded) {
            field = presetLoaded
            isChangesMade = false
        }
    var isChangesMade = false
    var isRedirectedFromNew = false
        private set
    private val yGravity = 270
    private val gravityType = Gravity.TOP
    var numberOfteamsPrevious: String? = null
    private var etNameYCoordinate = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TeamrandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        playersArrList = ArrayList()
    }



    override fun onPause() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etPlayerName.windowToken, 0)
        super.onPause()
    }



    private fun initializeViews() {


        binding.bDelName.post {
            val rectf = Rect()
            binding.bDelName.getLocalVisibleRect(rectf)
            val params = binding.bPresetOptions.layoutParams as LinearLayout.LayoutParams
            params.width = rectf.width()
            params.height = rectf.height()
            binding.bPresetOptions.layoutParams = params
        }

        binding.etPlayerName.setOnEditorActionListener { exampleView, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL || event.keyCode == KeyEvent.KEYCODE_ENTER)
                && event.action == KeyEvent.ACTION_UP
            ) {
                addName()
            }
            true
        }
        binding.etPlayerName!!.post {
            val coordinates = intArrayOf(0, 0)
            binding.etPlayerName.getLocationInWindow(coordinates)
            etNameYCoordinate = coordinates[1]
        }


        val decorView = this.window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener {
            //Calculations to detect if keyboard is present or not
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            val displayHeight = rect.bottom - rect.top
            val height = decorView.height
            val keyboardHiddenTemp = displayHeight.toDouble() / height > 0.8
            if (keyboardHiddenTemp != keyboardHidden) {
                keyboardHidden = keyboardHiddenTemp
                if (!keyboardHidden) {
                    //keyboard shown
                    binding.svScroll.postDelayed({
                        val coordinates = intArrayOf(0, 0)
                        binding.etPlayerName.getLocationInWindow(coordinates)
                        //beginning coordinates subtract the new shifted coordinates is the amount shifted
                        val shiftInLayoutByKeyboard = etNameYCoordinate - coordinates[1]
                        val params = binding.rlLabels.layoutParams as RelativeLayout.LayoutParams
                        params.setMargins(0, shiftInLayoutByKeyboard, 0, 0)
                        binding.rlLabels.layoutParams = params
                        binding.svScroll.post { //scrolls the scrollview to the bottom
                            binding.svScroll.fullScroll(View.FOCUS_DOWN)
                        }
                    }, 225)
                } else {
                    //keyboard hidden
                    val params = binding.rlLabels.layoutParams as RelativeLayout.LayoutParams
                    params.setMargins(0, 0, 0, 0)
                    binding.rlLabels.layoutParams = params
                }
            }
        }
        binding.bDelName.setOnClickListener(this)
        binding.bPresetOptions.setOnClickListener(this)
        findViewById<View>(R.id.bRandomize).setOnClickListener(this)
        binding.addActionButton.setOnClickListener(this)
        findViewById<View>(R.id.rlRoot).setOnTouchListener(this)
        binding.svScroll.setOnTouchListener(this)
    }

    private fun addName() {
        var name: String
        name = binding.etPlayerName.text.toString().trim { it <= ' ' }
        if (name.isEmpty()) {
            val t = Toast.makeText(applicationContext, "Please enter a name!", Toast.LENGTH_SHORT)
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            binding.etPlayerName.setText("") //reset edittext
            return
        } else if (name.contains("~") || name.contains("'")) {
            val t = Toast.makeText(
                applicationContext,
                "Name cannot contain the symbols ~ or ' .",
                Toast.LENGTH_SHORT
            )
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            return
        } else if (playersArrList!!.contains(name)) {
            val t = Toast.makeText(
                applicationContext,
                "Please enter a unique name.",
                Toast.LENGTH_SHORT
            )
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            return  //exit if duplicate names
        } else if (name.length > 26) {
            val t = Toast.makeText(
                applicationContext,
                "Name must be 1-26 characters.",
                Toast.LENGTH_SHORT
            )
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            return  //exit if duplicate names
        }

        //--------------------------------Update Total
        var total = binding.tvTotalPlayers.text.toString().toInt()
        total++
        binding.tvTotalPlayers.text = total.toString()

        //---------------------------------------------
        if (!isChangesMade) {
            isChangesMade = true //set changes made to true on add
        }

        //always add to playerList, only add to updateList if preset is loaded for more efficient saves
        playersArrList!!.add(name)
        if (isPresetLoaded) { //adds to update List
            updateList!!.add(UpdateObj("add", name))
        }
        //if name is valid add a number to it
        name = total.toString() + ". " + name

        binding.llPlayerList.removeView(binding.tvPlayerList)
        addNameToScreen(name)

        binding.etPlayerName.setText("")
        binding.svScroll.post {
            binding.svScroll.fullScroll(View.FOCUS_DOWN)
        }
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        when (v.id) {
            R.id.add_action_button -> addName()
            R.id.bDelName -> if (playersArrList!!.size > 0) {
                val i = Intent(this, DeleteNameActivity::class.java)
                i.putStringArrayListExtra("playerList", playersArrList)
                i.putExtra("currentPreset", binding.tvCurrentPreset.text.toString())
                if (isPresetLoaded) { //if preset is loaded pass in updatelist
                    i.putExtra("updateList", updateList)
                }
                startActivityForResult(i, 1) //0 is default request code
            } else {
                val t = Toast.makeText(applicationContext, "Nothing to delete.", Toast.LENGTH_SHORT)
                t.setGravity(gravityType, 0, yGravity)
                t.show()
            }
            R.id.bRandomize -> if (playersArrList!!.size >= 2) {
                val manager = fragmentManager
                val notd = NumberOfTeamsDialog()
                notd.show(manager, "notd") //adds fragment to the manager
            } else {
                val t = Toast.makeText(
                    applicationContext,
                    "Please enter at least 2 players.",
                    Toast.LENGTH_SHORT
                )
                t.setGravity(gravityType, 0, yGravity)
                t.show()
            }
            R.id.bPresetOptions -> openOptionsMenu()
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val eventID = event.action
        when (eventID) {
            MotionEvent.ACTION_DOWN -> {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etPlayerName.windowToken, 0)
                return true
            }
        }
        return false
    }

    override fun openOptionsMenu() {
        super.openOptionsMenu()
        val config = resources.configuration
        if (config.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK > Configuration.SCREENLAYOUT_SIZE_LARGE) {
            val originalScreenLayout = config.screenLayout
            config.screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
            super.openOptionsMenu()
            config.screenLayout = originalScreenLayout
        } else {
            super.openOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { //called once on load to load menu aka setting button
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu)
        val blowUp = menuInflater
        blowUp.inflate(R.menu.teamrand_menu, menu) //converts(inflates) the xml to a menu object
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //will call this when you click an menu item
        // TODO Auto-generated method stub
        when (item.itemId) {
            R.id.newPreset -> if (isChangesMade) {
                openSaveNewChangesDialog()
            } else {
                clearAll()
            }
            R.id.loadPreset -> {
                val i = Intent(this, LoadPreseActivity::class.java)
                i.putExtra("changesMade", isChangesMade)
                i.putExtra("presetLoaded", isPresetLoaded)
                i.putExtra("updateList", updateList)
                i.putStringArrayListExtra("playerList", playersArrList)
                i.putExtra("currentPreset", binding.tvCurrentPreset!!.text.toString())
                startActivityForResult(i, 2) //0 is default request code
            }
            R.id.savePresetAs -> openSavePresetAsDialog()
            R.id.savePreset -> savePreset()
            R.id.deletePreset -> {
                val `in` = Intent(this, DeletePresetActivity::class.java)
                if (isPresetLoaded) {
                    `in`.putExtra("currentPreset", binding.tvCurrentPreset!!.text.toString())
                }
                startActivityForResult(`in`, 3) //0 is default request code
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data)
        Log.v("dataRequest",requestCode.toString() + "|" + resultCode.toString() + "|"+ data.toString())
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    if (playersArrList!!.size == data.getStringArrayListExtra("playerList")!!.size) {
                        return  //exit if playerList size did not change
                    }
                    if (!isChangesMade) {
                        isChangesMade = true //set changes made to true on delete
                    }
                    playersArrList = data.getStringArrayListExtra("playerList")
                    if (data.getSerializableExtra("updateList") != null) {
                        updateList =
                            data.getSerializableExtra("updateList") as ArrayList<UpdateObj>?
                    }
                    populatePlayerList()
                }
                2 -> {
                    binding.tvCurrentPreset.text = data.getStringExtra("presetName")
                    playersArrList = data.getStringArrayListExtra("playerNames")
                    populatePlayerList()
                    isPresetLoaded =
                        true //make sure preset is loaded so we can start up initializeupdatelist
                    initializeUpdateList()
                }
                3 -> if (data.getBooleanExtra("currPresetDeleted", false)) {
                    clearAll()
                }
            }
        }
    }

    fun populatePlayerList() {
        binding.llPlayerList!!.removeAllViews()
        if (playersArrList!!.size == 0) {
            binding.llPlayerList!!.addView(binding.tvPlayerList)
            binding.tvTotalPlayers.text = Integer.toString(playersArrList!!.size)
            isChangesMade = false
            return  //exit if playerArrList is 0
        }
        var i = 1
        for (nameString in playersArrList!!) {
            //add name to players list
            val name = Integer.toString(i) + ". " + nameString
            addNameToScreen(name)
            i++
        }
        //tvList.setText(revisedList); //set the tv to the new List after revision(delete/update)
        binding.tvTotalPlayers.text = Integer.toString(playersArrList!!.size)
    }

    fun initializeUpdateList() { //must initialize update if you load a preset
        if (isPresetLoaded == true) {
            updateList = ArrayList()
        }
    }

    fun clearAll() {
        isPresetLoaded = false
        binding.tvCurrentPreset.text = "None"
        playersArrList!!.clear()
        updateList = null
        populatePlayerList()
    }

    private fun openSavePresetAsDialog() {
        val manager = fragmentManager
        val savepreset = SavePresetAsDialog()
        savepreset.show(manager, "savepreset") //adds fragment to the manager
    }

    fun openSaveNewChangesDialog() {
        val manager = fragmentManager
        val newscd = NewSaveChangesDialog()
        newscd.show(manager, "newscd") //adds fragment to the manager
    }

    fun savePreset() {
        if (isPresetLoaded) {
            val db = DbHelper(this)
            db.open()
            db.updatePreset(binding.tvCurrentPreset.getText().toString(), updateList)
            db.close()
            val t = Toast.makeText(applicationContext, "Preset saved.", Toast.LENGTH_SHORT)
            t.setGravity(gravityType, 0, yGravity)
            t.show()
            updateList!!.clear()
            isChangesMade = false //when saved = blank slate
        } else {
            openSavePresetAsDialog()
        }
    }



    private fun addNameToScreen(name: String) {
        //add name to players list
        val tvNewName = TextView(this)
        tvNewName.text = name.replaceFirst(
            "\\s".toRegex(),
            "\u00A0"
        ) //replace first space with unicode no break space);
        tvNewName.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.rtg_text_size)
        )
        binding.llPlayerList.addView(tvNewName)
    }

    fun setRedirectFromNew(redirectFromNew: Boolean) {
        isRedirectedFromNew = redirectFromNew
    }

    fun setCurrentPresetText(currentPreset: String?) {
        binding.tvCurrentPreset.text = currentPreset
    }

    val currentPreset: String
        get() = binding.tvCurrentPreset.text.toString()
//    val totalPlayer: String
//        get() =  binding.tvTotalPlayers.text.toString()

    companion object {
        private var keyboardHidden = true
    }
}

