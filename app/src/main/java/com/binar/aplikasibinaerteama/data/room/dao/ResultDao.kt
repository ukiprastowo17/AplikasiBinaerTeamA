package com.binar.aplikasibinaerteama.data.room.dao

import androidx.room.*
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member

@Dao
interface ResultDao {

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE_GROUP)
    suspend fun getAllHistory() : List<Group>





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: List<Group>)

    @Delete
    suspend fun deleteGroup(group: Group) : Int

    @Update
    suspend fun updateGroup(group: Group) : Int

}