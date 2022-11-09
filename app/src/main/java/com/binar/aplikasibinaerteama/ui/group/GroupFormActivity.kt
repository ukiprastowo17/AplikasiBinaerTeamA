package com.binar.aplikasibinaerteama.ui.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.base.BaseActivity
import com.binar.aplikasibinaerteama.base.GenericViewModelFactory
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ActivityGroupFormBinding
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.dialogs.GroupListDialog
import com.binar.aplikasibinaerteama.ui.group.adapter.GroupAdapter
import com.binar.aplikasibinaerteama.ui.member.MemberFormActivity
import com.binar.aplikasibinaerteama.wrapper.Resource


class GroupFormActivity : BaseActivity<ActivityGroupFormBinding>(ActivityGroupFormBinding::inflate), GroupListDialog.DialogListener, GroupAdapter.OnCLickListener {

    private val viewModel: GroupViewModel by lazy {
        GenericViewModelFactory(GroupViewModel(ServiceLocator.provideLocalRepository(this))).create(
            GroupViewModel::class.java
        )
    }


    private lateinit var adapter: GroupAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        observeData()


        initRecyclerView()
        initData()

    }

    private fun initData() {
        binding.btnAddList.setOnClickListener {
            GroupListDialog().show(supportFragmentManager, "Group List Dialog")
        }
        binding.btnBack.setOnClickListener {
          finish()
        }
        viewModel.getAllGroups()
    }



    private fun observeData() {
        viewModel.initDataGroupType.observe(this) {
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
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbForm.isVisible = false

                    initData()
                    Toast.makeText(this, "Insert data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.pbForm.isVisible = false
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateResult.observe(this) {
            when (it) {
                is Resource.Loading -> {

                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {

                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Update data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.deleteResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Delete data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.pbForm.isVisible = false
                    finish()
                    Toast.makeText(this, "Error when delete data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }











    private fun showData(data: List<Group>?) {
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
        adapter = GroupAdapter(this)
        binding.rvNotes.adapter = this@GroupFormActivity.adapter

    }

    companion object {
        fun startActivity(context: Context, id: Int? = null) {
            context.startActivity(Intent(context, GroupFormActivity::class.java).apply {
                id?.let {
                    putExtra(CommonConstant.EXTRAS_ID_NOTE, id)
                }
            })
        }
    }

    override fun onDeleteClickListener(group: Group) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_delete_dialog))
            .setMessage("Are you sure want to delete ${group.name_group} ?")
            .setPositiveButton(getString(R.string.text_yes_dialog)) { dialog, _ ->
                viewModel.deleteGroup(group)
                Toast.makeText(this, "${group.name_group} Successfully Deleted", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.text_no_dialog)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun processDialog(group: Group) {
        viewModel.insertGroup(group)
    }
}