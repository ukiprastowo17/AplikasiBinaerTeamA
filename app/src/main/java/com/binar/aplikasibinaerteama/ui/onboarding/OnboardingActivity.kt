package com.binar.aplikasibinaerteama.ui.onboarding


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.RandomizeActivity
import com.binar.aplikasibinaerteama.TeamRandomizerActivity
import com.binar.aplikasibinaerteama.adapter.ViewPagerAdapter
import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSourceImpl
import com.binar.aplikasibinaerteama.databinding.ActivityOnboardingBinding
import com.binar.aplikasibinaerteama.model.SliderData
import com.binar.aplikasibinaerteama.ui.main.HomeActivity
import com.binar.aplikasibinaerteama.ui.slider.SliderFragment
import com.binar.aplikasibinaerteama.util.getNextIndex
import com.binar.aplikasibinaerteama.util.getPreviousIndex

class OnboardingActivity : AppCompatActivity() {
    private val binding: ActivityOnboardingBinding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }


    private val pagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initFragmentViewPager()
        setOnClickListeners()
    }

    private fun initAdapter() {
        pagerAdapter.apply {
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        "Easy \nTo Use",
                        "Membuat grup menjadi lebih mudah \nhanya dari ponsel Anda.",
                        R.drawable.ic_landpage_1
                    )
                )
            )
            addFragment(
                SliderFragment.newInstance(
                    SliderData(
                        "Fast & \nBest Solution",
                        "Sebagai sebuah solusi yang cepat dan tepat. Anda juga bisa memberikan nama grup.",
                        R.drawable.ic_landpage_2
                    )
                )
            )
        }
    }

    private fun setupViewPager(){
        binding.vpIntro.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when{
                        position == 0 -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled =  true
                            binding.tvPrevious.isInvisible = true
                            binding.tvPrevious.isEnabled =  true
                            binding.tvMenu.isGone = true

                        }
                        position < pagerAdapter.getMaxIndex() ->{

                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled =  true
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled =  true
                            binding.tvMenu.isGone = true

                        }
                        position == pagerAdapter.getMaxIndex()  ->{
                            binding.tvNext.isInvisible = true
                            binding.tvNext.isEnabled =  false
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled =  true
                            binding.tvMenu.isVisible = true



                        }
                    }
                }
            })
        }
        binding.dotsIndicator.attachTo(binding.vpIntro)
    }

    private fun initFragmentViewPager(){
        initAdapter()
        setupViewPager()
    }

    private fun setOnClickListeners(){
        binding.tvNext.setOnClickListener {
            navigateToNextFragment()
        }

        binding.tvPrevious.setOnClickListener {
            navigateToPreviosFragment()
        }


        binding.tvMenu.setOnClickListener {
            navigateToMenuFragment()
        }
    }

    private fun navigateToPreviosFragment() {
      val nextIndex =  binding.vpIntro.getPreviousIndex()
          if (nextIndex != -1){
              binding.vpIntro.setCurrentItem(nextIndex, true)

      }
    }

    private fun navigateToNextFragment(){
        val nextIndex =  binding.vpIntro.getNextIndex()
        if (nextIndex != -1){
            binding.vpIntro.setCurrentItem(nextIndex, true)

        }

    }

    private fun navigateToMenuFragment(){

        val i = Intent(this@OnboardingActivity, HomeActivity::class.java)
        startActivity(i)
    }





}