package com.binar.aplikasibinaerteama.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.aplikasibinaerteama.constant.CommonConstant
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = CommonConstant.DATABASE_TABLE_RESULT)
data class ResultData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CommonConstant.KEY_RESULT_ID)
    var id: Int? = null,

    @ColumnInfo(name = CommonConstant.KEY_RESULT_NAME)
    var nameResult: String,

    @ColumnInfo(name = CommonConstant.KEY_RESULT_DATE)
    var dateResult: String,

    @ColumnInfo(name = CommonConstant.KEY_RESULT_MEMBER)
    var memberResult: String,

    @ColumnInfo(name = CommonConstant.KEY_RESULT_TEAMS)
    var teamResult: String,

    @ColumnInfo(name = CommonConstant.KEY_RESULT_GROUP_NAME)
    var groupNameResult: String
) : Parcelable
