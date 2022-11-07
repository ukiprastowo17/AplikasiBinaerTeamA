package com.binar.aplikasibinaerteama.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@kotlinx.parcelize.Parcelize
data class SliderData(
    val title: String,
    val desc: String,
    val imgSlider: Int
): Parcelable