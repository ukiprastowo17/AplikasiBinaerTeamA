package com.binar.aplikasibinaerteama.data.room.dao

import androidx.room.*
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member

@Dao
interface MemberDao {

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE)
    suspend fun getAllMember() : List<Member>


    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE + " WHERE "+ CommonConstant.KEY_ROWID +" == :id")
    suspend fun getAllMembersById(id : Int) : Member

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE + " WHERE "+  CommonConstant.KEY_NAME_MEMBER +" == :id")
    suspend fun getPlayersByPreset(id : String) : List<Member>

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE+ " where " + CommonConstant.KEY_ID_GROUP + "== :id")
    suspend fun getAllGroupByGroup(id: String) : List<Member>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(member: List<Member>)

    @Delete
    suspend fun deleteMember(member: Member) : Int

    @Update
    suspend fun updateMember(member: Member) : Int
//
//    @Query("UPDATE " + CommonConstant.DATABASE_TABLE + " SET order_price=:price WHERE order_id = :id")
//    suspend fun updateMember(id : String) : Int
}