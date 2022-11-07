package com.binar.aplikasibinaerteama.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.aplikasibinaerteama.constant.CommonConstant
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = CommonConstant.DATABASE_TABLE)
data class Member(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CommonConstant.KEY_ROWID)
    val id: Int? = null,

    @ColumnInfo(name = CommonConstant.KEY_PRESET_NAME)
    val presetName: String,

    @ColumnInfo(name = CommonConstant.KEY_PLAYERS)
    val players: String
) : Parcelable
