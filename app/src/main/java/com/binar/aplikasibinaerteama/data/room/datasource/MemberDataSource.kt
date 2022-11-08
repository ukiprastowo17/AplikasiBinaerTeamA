package com.binar.aplikasibinaerteama.data.room.datasource

import com.binar.aplikasibinaerteama.data.room.dao.MemberDao
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member


interface MemberDataSource {
    suspend fun getAllMember(): List<Member>

    suspend fun getAllMembersById(id: Int): Member

    suspend fun getPlayersByPreset(id: String): List<Member>

    suspend fun insertMember(member: Member): Long

    suspend fun deleteMember(member: Member): Int

    suspend fun updateMember(member: Member): Int

    suspend fun getAllGroupByGroup(id:String): List<Member>
}

class MemberDataSourceImpl(private val dao: MemberDao) : MemberDataSource {
    override suspend fun getAllMember(): List<Member> {
        return dao.getAllMember()
    }

    override suspend fun getAllMembersById(id: Int): Member {
       return dao.getAllMembersById(id)
    }

    override suspend fun getAllGroupByGroup(id: String): List<Member> {
        return dao.getAllGroupByGroup(id)
    }

    override suspend fun getPlayersByPreset(id: String): List<Member> {
        return dao.getPlayersByPreset(id)
    }

    override suspend fun insertMember(member: Member): Long {
        return dao.insertMember(member)
    }

    override suspend fun deleteMember(member: Member): Int {
        return dao.deleteMember(member)
    }

    override suspend fun updateMember(member: Member): Int {
        return dao.updateMember(member)
    }


}