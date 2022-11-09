package com.binar.aplikasibinaerteama.data.repository

import androidx.lifecycle.MutableLiveData
import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.GroupDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.MemberDataSource
import com.binar.aplikasibinaerteama.data.room.datasource.ResultDataSource
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.data.room.entity.ResultData
import com.binar.aplikasibinaerteama.model.ResultModel
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


    suspend fun getAllResult(): Resource<List<ResultModel>>
    suspend fun getAllResultById(id : String): Resource<List<ResultData>>
    suspend fun deleteResult(resultData: ResultData): Resource<Number>
    suspend fun updateResult(resultData: ResultData): Resource<Number>
    suspend fun insertResult(resultData: ResultData): Resource<Number>

}

class LocalRepositoryImpl(
    private val preferenceDataSource: PreferenceDataSource,
    private val memberDataSource: MemberDataSource,
    private val groupDataSource: GroupDataSource,
    private val resultDataSource : ResultDataSource

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

    override suspend fun getAllResult(): Resource<List<ResultModel>> {
        return proceed { resultDataSource.getAllResult() }
    }

    override suspend fun getAllResultById(id: String): Resource<List<ResultData>> {
        return proceed { resultDataSource.getAllResultById(id) }
    }

    override suspend fun deleteResult(resultData: ResultData): Resource<Number> {
        return proceed { resultDataSource.deleteResult(resultData) }
    }

    override suspend fun updateResult(resultData: ResultData): Resource<Number> {
        return proceed { resultDataSource.updateResult(resultData) }
    }

    override suspend fun insertResult(resultData: ResultData): Resource<Number> {
        return proceed { resultDataSource.insertResult(resultData) }
    }


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(message = exception.message)
        }
    }
}