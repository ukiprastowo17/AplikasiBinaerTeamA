package com.binar.aplikasibinaerteama.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.ui.random.RandomizeActivity
import com.binar.aplikasibinaerteama.base.BaseActivity
import com.binar.aplikasibinaerteama.base.GenericViewModelFactory
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ActivityHistoryFormBinding
import com.binar.aplikasibinaerteama.databinding.ActivityMemberFormBinding
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.dialogs.AddMemberDialog
import com.binar.aplikasibinaerteama.model.ResultModel
import com.binar.aplikasibinaerteama.ui.history.adapter.HistoryAdapter
import com.binar.aplikasibinaerteama.ui.member.adapter.MemberAdapter
import com.binar.aplikasibinaerteama.wrapper.Resource


class HistoryFormActivity : BaseActivity<ActivityHistoryFormBinding>(ActivityHistoryFormBinding::inflate) , AddMemberDialog.DialogListener {

    private val viewModel: HistoryViewModel by lazy {
        GenericViewModelFactory(HistoryViewModel(ServiceLocator.provideLocalRepository(this))).create(
            HistoryViewModel::class.java
        )
    }


    var playersArrList: ArrayList<String>? = null

    private val nameGroup: String? by lazy {
        intent.getStringExtra("name_group")
    }

    private val idGroup: String? by lazy {
        intent.getStringExtra("id_group")
    }

    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter {
            val intent = Intent(this@HistoryFormActivity, HistoryDetailFormActivity::class.java)
            intent.putExtra("name_result", it.name_result.toString())
            startActivity(intent)
            Toast.makeText(this@HistoryFormActivity, "Go To About Activity", Toast.LENGTH_SHORT)
                .show()
        }
    }



    override fun onStart() {
        super.onStart()
        viewModel.setIntentData(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        observeData()
        playersArrList = ArrayList()
        initRecyclerView()
        initData()




    }

    private fun initData() {

        Log.d("datagroup",idGroup.toString())
       viewModel.getAllResult()
    }



    private fun observeData() {
        viewModel.initialDataResultHistory.observe(this) {
            when (it) {
                is Resource.Error -> {
                    showError(it.message)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    showData(it.data)






                }
            }
        }

        viewModel.insertResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
//                    finish()
                    initData()
                    Toast.makeText(this, "Insert data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
//                    finish()
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Update data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.deleteResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Delete data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Error when delete data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setFormEnabled(isFormEnabled: Boolean) {
        with(binding) {
//            tilNoteTitle.isEnabled = isFormEnabled
        }
    }

    private fun bindDataToForm(data: List<Member>?) {
        data?.let {
//            binding.etNoteTitle.setText(data.)
        }
    }



    private fun checkFormValidation(): Boolean {
//        val title = binding.etNoteTitle.text.toString()
//        var isFormValid = true
//        if (title.isEmpty()) {
//            isFormValid = false
//            binding.tilNoteTitle.isErrorEnabled = true
//            binding.tilNoteTitle.error = getString(R.string.text_error_empty_title)
//        } else {
//            binding.tilNoteTitle.isErrorEnabled = false
//        }


        return true
    }





    private fun parseFormIntoEntity(): Member {
        return idGroup?.let {
            Member(
                nameMember = "",
                idGroup = it
            ).apply {
                if (isEditAction()) {
                    id = viewModel.memberId
                }
            }
        }!!
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =
            if (isEditAction())
                getString(R.string.text_toolbar_edit)
            else
                getString(R.string.text_toolbar_insert)
    }

    private fun isEditAction(): Boolean {
        return viewModel.memberId != CommonConstant.UNSET_ID
    }


    private fun showData(data: List<ResultModel>?) {




        data?.let { listData ->
            binding.pbForm.isVisible = false
            binding.tvError.isVisible = false
            binding.rvNotes.isVisible = true
            if (listData.isNotEmpty()) {
                adapter.setItems(listData)



            } else {
                showEmptyData()
            }
        }

    }

    private fun showEmptyData() {
        binding.tvError.isVisible = true
        binding.tvError.text = getString(R.string.text_empty_notes)

    }

    private fun showLoading() {
        binding.pbForm.isVisible = true
        binding.tvError.isVisible = false
        binding.rvNotes.isVisible = false
    }

    private fun showError(message: String?) {
        binding.pbForm.isVisible = false
        binding.tvError.isVisible = true
        binding.rvNotes.isVisible = false
        message?.let {
            binding.tvError.text = it
        }
    }

    private fun initRecyclerView() {
        binding.rvNotes.adapter = this@HistoryFormActivity.adapter
    }

    companion object {
        fun startActivity(context: Context, id: Int? = null) {
            context.startActivity(Intent(context, HistoryFormActivity::class.java).apply {
                id?.let {
                    putExtra(CommonConstant.EXTRAS_ID_NOTE, id)
                }
            })
        }
    }

    override fun processDialog(memberName: String) {
            val data = Member(idGroup = idGroup.toString(), nameMember = memberName)
//            viewModel.insertMember(data)

    }
}


