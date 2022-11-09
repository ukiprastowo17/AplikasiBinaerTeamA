package com.binar.aplikasibinaerteama.ui.random

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.repository.LocalRepository
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.data.room.entity.ResultData
import com.binar.aplikasibinaerteama.model.ResultModel
import com.binar.aplikasibinaerteama.wrapper.Resource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


typealias InitialDataResultTypeHistory = Resource<List<ResultModel>>
typealias InitialDataResultTypeHistoryLine = Resource<List<ResultData>>
class ResultViewModel(private val repository: LocalRepository) : ViewModel() {

    var memberId: Int = CommonConstant.UNSET_ID

    val initialDataResultHistory = MutableLiveData<InitialDataResultTypeHistory>()
    val initialDataResultHistoryLine = MutableLiveData<InitialDataResultTypeHistoryLine>()
    val insertResult = MutableLiveData<Resource<Number>>()
    val deleteResult = MutableLiveData<Resource<Number>>()
    val updateResult = MutableLiveData<Resource<Number>>()



    fun getAllResultById(id : String){
        viewModelScope.launch(Dispatchers.IO){
            initialDataResultHistoryLine.postValue(Resource.Loading())
            delay(1000)
            initialDataResultHistoryLine.postValue(repository.getAllResultById(id))
        }
    }





    fun getAllResult(){
        viewModelScope.launch(Dispatchers.IO){
            initialDataResultHistory.postValue(Resource.Loading())
            delay(1000)
            initialDataResultHistory.postValue(repository.getAllResult())
        }
    }

    fun deleteResult(resultData: ResultData) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteResult.postValue(Resource.Loading())
            delay(1000)
            deleteResult.postValue(repository.deleteResult(resultData))
        }
    }

    fun updateResult(resultData: ResultData) {
        viewModelScope.launch(Dispatchers.IO) {
            updateResult.postValue(Resource.Loading())
            delay(1000)
            updateResult.postValue(repository.updateResult(resultData))
        }
    }

    fun insertResult(resultData: ResultData) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)
            insertResult.postValue(repository.insertResult(resultData))
        }
    }

    fun setIntentData(intent: Intent) {
        memberId = intent.getIntExtra(CommonConstant.EXTRAS_ID_NOTE, CommonConstant.UNSET_ID)
    }

}


