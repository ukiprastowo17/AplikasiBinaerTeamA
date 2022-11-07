package com.binar.aplikasibinaerteama.ui.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.binar.aplikasibinaerteama.databinding.FragmentSliderBinding
import com.binar.aplikasibinaerteama.model.SliderData


class SliderFragment : Fragment() {
    private lateinit var binding: FragmentSliderBinding

    private var sliderData: SliderData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSliderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSliderData()
    }

    private fun bindSliderData() {
        with(binding){
            tvTitleLandpage.text = sliderData?.title.orEmpty()
            tvDescLandpage.text = sliderData?.desc.orEmpty()
            ivLandpage.load(sliderData?.imgSlider){
                crossfade(true)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sliderData = it.getParcelable(ARG_SLIDER_DATA)
        }
    }

    companion object {
        const val ARG_SLIDER_DATA = "ARG_SLIDER_DATA"

        @JvmStatic
        fun newInstance(sliderData: SliderData) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SLIDER_DATA,sliderData)

                }
            }
    }
}