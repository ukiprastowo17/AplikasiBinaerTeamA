package com.binar.aplikasibinaerteama.ui.about

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.aplikasibinaerteama.databinding.ActivityMenuAboutBinding
import com.binar.aplikasibinaerteama.ui.about.about.AboutActivity
import com.binar.aplikasibinaerteama.ui.about.team.TeamActivity


class MenuAboutActivity : AppCompatActivity() {
    private val binding: ActivityMenuAboutBinding by lazy {
        ActivityMenuAboutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setMenuAboutClickListener()
        supportActionBar?.hide()
    }

    private fun setMenuAboutClickListener(){
        binding.ivBgAbout.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        binding.ivBgTeam.setOnClickListener{
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
        }
    }
}