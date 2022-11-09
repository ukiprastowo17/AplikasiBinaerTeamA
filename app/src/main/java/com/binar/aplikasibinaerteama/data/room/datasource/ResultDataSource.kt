package com.binar.aplikasibinaerteama.data.room.datasource

import com.binar.aplikasibinaerteama.data.room.dao.MemberDao
import com.binar.aplikasibinaerteama.data.room.dao.ResultDao
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.data.room.entity.ResultData
import com.binar.aplikasibinaerteama.model.ResultModel


interface ResultDataSource {
    suspend fun getAllResult(): List<ResultModel>

    suspend fun getAllResultById(id: String): List<ResultData>

    suspend fun insertResult(resultData: ResultData): Long

    suspend fun deleteResult(resultData: ResultData): Int

    suspend fun updateResult(resultData: ResultData): Int

}

class ResultDataSourceImpl(private val dao: ResultDao) : ResultDataSource {
    override suspend fun getAllResult(): List<ResultModel> {
        return dao.getAllResult()
    }

    override suspend fun getAllResultById(id: String): List<ResultData>  {
       return dao.getAllResultById(id)
    }


    override suspend fun insertResult(resultData: ResultData): Long {
        return dao.insertResult(resultData)
    }

    override suspend fun deleteResult(resultData: ResultData): Int {
        return dao.deleteResult(resultData)
    }

    override suspend fun updateResult(resultData: ResultData): Int {
        return dao.updateResult(resultData)
    }


}