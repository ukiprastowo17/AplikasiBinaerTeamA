package com.binar.aplikasibinaerteama.data.repository

import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.MemberDataSource
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.wrapper.Resource


interface LocalRepository {
    suspend fun getAllMember(): Resource<List<Member>>
    suspend fun deleteMember(member: Member): Resource<Number>
    suspend fun updateMember(member: Member): Resource<Number>
    suspend fun insertMember(member: Member): Resource<Number>
    suspend fun getMemberById(id : Int): Resource<Member>

}

class LocalRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource,
    private val memberDataSource: MemberDataSource,

    ) : LocalRepository {
    override suspend fun getAllMember(): Resource<List<Member>> {
        return proceed { memberDataSource.getAllMember() }
    }

    override suspend fun deleteMember(member: Member): Resource<Number> {
        return proceed { memberDataSource.deleteMember(member) }
    }

    override suspend fun updateMember(member: Member): Resource<Number> {
        return proceed { memberDataSource.updateMember(member) }
    }

    override suspend fun insertMember(member: Member): Resource<Number> {
        return proceed { memberDataSource.insertMember(member) }
    }

    override suspend fun getMemberById(id: Int): Resource<Member> {
        return proceed { memberDataSource.getAllMembersById(id) }
    }



    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(message = exception.message)
        }
    }
}