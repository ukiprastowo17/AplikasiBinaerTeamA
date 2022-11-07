package com.binar.aplikasibinaerteama.ui.about.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.databinding.ActivityAboutBinding
import com.binar.aplikasibinaerteama.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val name: String? by lazy {
        intent.getStringExtra(CommonConstant.EXTRAS_NAME)
    }

    private val job: String? by lazy {
        intent.getStringExtra(CommonConstant.EXTRAS_JOB)
    }

    private val desc: String? by lazy {
        intent.getStringExtra(CommonConstant.EXTRAS_DESC)
    }

    private val img: String? by lazy {
        intent.getStringExtra(CommonConstant.EXTRAS_IMG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initView()
    }


    fun initView(){
        binding.tvName.text = name
        binding.tvJob.text = job
        binding.tvDesc.text = desc
        binding.ivImg.load(img.toString()) {
            crossfade(true)
        }
    }

}