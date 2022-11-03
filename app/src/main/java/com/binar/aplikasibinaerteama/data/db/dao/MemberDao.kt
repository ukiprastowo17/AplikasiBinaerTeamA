package com.binar.aplikasibinaerteama.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binar.aplikasibinaerteama.data.db.entity.Member

@Dao
interface MemberDao {

    @Query("SELECT * FROM tb_member")
    suspend fun getAllMember() : List<Member>


    @Query("SELECT * FROM tb_member WHERE id == :id")
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