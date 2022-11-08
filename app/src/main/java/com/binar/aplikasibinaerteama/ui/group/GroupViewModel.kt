package com.binar.aplikasibinaerteama.ui.group

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.repository.LocalRepository
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.wrapper.Resource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


typealias InitDataGroupType = Resource<List<Group>>


class GroupViewModel(private val repository: LocalRepository) : ViewModel() {

    var groupId: Int = CommonConstant.UNSET_ID

    val initDataGroupType = MutableLiveData<InitDataGroupType>()
    val insertResult = MutableLiveData<Resource<Number>>()
    val deleteResult = MutableLiveData<Resource<Number>>()
    val updateResult = MutableLiveData<Resource<Number>>()




    fun getAllGroups(){
        viewModelScope.launch(Dispatchers.IO){
            initDataGroupType.postValue(Resource.Loading())
            delay(1000)
            initDataGroupType.postValue(repository.getAllGroups())
        }
    }

    fun deleteGroup(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteResult.postValue(Resource.Loading())
            delay(1000)
            deleteResult.postValue(repository.deleteGroup(group))
        }
    }

    fun updateGroup(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            updateResult.postValue(Resource.Loading())
            delay(1000)
            updateResult.postValue(repository.updateGroup(group))
        }
    }

    fun insertGroup(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            insertResult.postValue(Resource.Loading())
            delay(1000)
            insertResult.postValue(repository.insertGroup(group))
        }
    }

//    fun setIntentData(intent: Intent) {
//        memberId = intent.getIntExtra(CommonConstant.EXTRAS_ID_NOTE, CommonConstant.UNSET_ID)
//    }

}


