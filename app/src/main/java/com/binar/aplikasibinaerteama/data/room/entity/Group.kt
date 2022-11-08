package com.binar.aplikasibinaerteama.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.aplikasibinaerteama.constant.CommonConstant
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = CommonConstant.DATABASE_TABLE_GROUP)
data class Group(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CommonConstant.KEY_ROWID)
    var id: Int? = null,

    @ColumnInfo(name = CommonConstant.KEY_GROUP_NAME)
    var name_group: String,

    @ColumnInfo(name = CommonConstant.KEY_GROUP_DESC)
    var desc_group: String
) : Parcelable
