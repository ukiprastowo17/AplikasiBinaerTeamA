package com.binar.aplikasibinaerteama.data.repository

import androidx.lifecycle.MutableLiveData
import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.GroupDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.MemberDataSource
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.wrapper.Resource


interface LocalRepository {
    suspend fun getAllMember(): Resource<List<Member>>
    suspend fun deleteMember(member: Member): Resource<Number>
    suspend fun updateMember(member: Member): Resource<Number>
    suspend fun insertMember(member: Member): Resource<Number>
    suspend fun getMemberById(id : Int): Resource<Member>
    suspend fun getPlayersByPreset(id : String): Resource<List<Member>>
    suspend fun getAllGroupByGroup(id : String): Resource<List<Member>>

    suspend fun getAllGroups(): Resource<List<Group>>
    suspend fun deleteGroup(group: Group): Resource<Number>
    suspend fun updateGroup(group: Group): Resource<Number>
    suspend fun insertGroup(group: Group): Resource<Number>

}

class LocalRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource,
    private val memberDataSource: MemberDataSource,
    private val groupDataSource: GroupDataSource,

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
        TODO("Not yet implemented")
    }


    override suspend fun getPlayersByPreset(id: String): Resource<List<Member>>{
        return proceed { memberDataSource.getPlayersByPreset(id) }
    }

    override suspend fun getAllGroupByGroup(id: String): Resource<List<Member>> {
        return proceed { memberDataSource.getAllGroupByGroup(id) }
    }


    override suspend fun getAllGroups(): Resource<List<Group>> {
        return proceed { groupDataSource.getAllGroup() }
    }

    override suspend fun deleteGroup(group: Group): Resource<Number> {
        return proceed { groupDataSource.deleteGroup(group) }
    }

    override suspend fun updateGroup(group: Group): Resource<Number> {
        return proceed { groupDataSource.updateGroup(group) }
    }

    override suspend fun insertGroup(group: Group): Resource<Number> {
        return proceed { groupDataSource.insertGroup(group) }
    }


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(message = exception.message)
        }
    }
}