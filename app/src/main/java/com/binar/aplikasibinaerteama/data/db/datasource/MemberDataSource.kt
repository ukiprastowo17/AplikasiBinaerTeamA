package com.binar.aplikasibinaerteama.data.db.datasource

import com.binar.aplikasibinaerteama.data.db.dao.MemberDao
import com.binar.aplikasibinaerteama.data.db.entity.Member


interface MemberDataSource {
    suspend fun getAllMember(): List<Member>

    suspend fun getAllMembersById(id: Int): Member


    suspend fun insertMember(member: Member): Long

    suspend fun deleteMember(member: Member): Int

    suspend fun updateMember(member: Member): Int
}

class MemberDataSourceImpl(private val dao: MemberDao) : MemberDataSource {
    override suspend fun getAllMember(): List<Member> {
        return dao.getAllMember()
    }

    override suspend fun getAllMembersById(id: Int): Member {
       return dao.getAllMembersById(id)
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