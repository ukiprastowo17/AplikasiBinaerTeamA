package com.binar.aplikasibinaerteama.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.binar.aplikasibinaerteama.databinding.ActivitySplashBinding
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.ui.main.HomeActivity
import com.binar.aplikasibinaerteama.ui.member.MemberFormActivity
import com.binar.aplikasibinaerteama.ui.onboarding.OnboardingActivity



class SplashActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null

    private val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTimerSplashScreen()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }
    private fun setTimerSplashScreen() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if ( ServiceLocator.providePreferenceDataSource(this@SplashActivity).isSkipIntro()){
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            }
        }
        timer?.start()
    }
}