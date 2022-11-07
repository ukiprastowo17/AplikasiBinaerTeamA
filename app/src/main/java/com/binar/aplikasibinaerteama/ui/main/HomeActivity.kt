package com.binar.aplikasibinaerteama.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binar.aplikasibinaerteama.LoadPreseActivity
import com.binar.aplikasibinaerteama.TeamRandomizerActivity
import com.binar.aplikasibinaerteama.databinding.ActivityHomeBinding
import com.binar.aplikasibinaerteama.ui.about.MenuAboutActivity


class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        navigateToMenu()
    }

    private fun navigateToMenu() {
        with(binding) {
            cvMember.setOnClickListener {
                val intent = Intent(this@HomeActivity, TeamRandomizerActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@HomeActivity, "Go To Member Activity", Toast.LENGTH_SHORT)
                    .show()
            }
            cvGroup.setOnClickListener {
                val intent = Intent(this@HomeActivity, LoadPreseActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@HomeActivity, "Go To Group Activity", Toast.LENGTH_SHORT)
                    .show()
            }
            cvHistory.setOnClickListener {
                /*val intent = Intent(this@HomeActivity, HistoryActivity::class.java)
                startActivity(intent)*/
                Toast.makeText(this@HomeActivity, "Go To History Activity", Toast.LENGTH_SHORT)
                    .show()
            }
            cvAbout.setOnClickListener {
                val intent = Intent(this@HomeActivity, MenuAboutActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@HomeActivity, "Go To About Activity", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}