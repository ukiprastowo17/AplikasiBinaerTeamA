package com.binar.aplikasibinaerteama.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@kotlinx.parcelize.Parcelize
data class ResultModel(
    val name_result : String,
    val group_name_result: String,
): Parcelable