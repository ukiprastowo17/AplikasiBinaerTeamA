package com.binar.aplikasibinaerteama.data.room.dao

import androidx.room.*
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Member

@Dao
interface MemberDao {

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE)
    suspend fun getAllMember() : List<Member>


    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE + " WHERE "+ CommonConstant.KEY_ROWID +" == :id")
    suspend fun getAllMembersById(id : Int) : Member

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(member: List<Member>)

    @Delete
    suspend fun deleteMember(member: Member) : Int

    @Update
    suspend fun updateMember(member: Member) : Int
}