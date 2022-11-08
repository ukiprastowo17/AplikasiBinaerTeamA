package com.binar.aplikasibinaerteama.ui.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.RandomizeActivity
import com.binar.aplikasibinaerteama.base.BaseActivity
import com.binar.aplikasibinaerteama.base.GenericViewModelFactory
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ActivityGroupFormBinding
import com.binar.aplikasibinaerteama.databinding.ActivityMemberFormBinding
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.dialogs.GroupListDialog
import com.binar.aplikasibinaerteama.ui.about.MenuAboutActivity
import com.binar.aplikasibinaerteama.ui.group.adapter.GroupAdapter
import com.binar.aplikasibinaerteama.ui.member.MemberFormActivity
import com.binar.aplikasibinaerteama.wrapper.Resource


class GroupFormActivity : BaseActivity<ActivityGroupFormBinding>(ActivityGroupFormBinding::inflate), GroupListDialog.DialogListener {

    private val viewModel: GroupViewModel by lazy {
        GenericViewModelFactory(GroupViewModel(ServiceLocator.provideLocalRepository(this))).create(
            GroupViewModel::class.java
        )
    }

    private val adapter: GroupAdapter by lazy {
        GroupAdapter {
            val intent = Intent(this@GroupFormActivity, MemberFormActivity::class.java)
            intent.putExtra("id_group", it.id.toString())
            intent.putExtra("name_group", it.name_group)
            startActivity(intent)
            Toast.makeText(this@GroupFormActivity, "Go To About Activity", Toast.LENGTH_SHORT)
                .show()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//        initToolbar()
        observeData()


        initRecyclerView()
        initData()
        binding.btnAddList.setOnClickListener {
            GroupListDialog().show(supportFragmentManager, "Group List Dialog")
        }
    }

    private fun initData() {
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
                    Log.d("datasayaok", it.data!!.toString() )
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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_member_note, menu)
//        val menuDelete = menu?.findItem(R.id.menu_delete)
//        menuDelete?.isVisible = isEditAction()
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_save -> {
//                saveNote()
//                true
//            }
//            R.id.menu_delete -> {
//                deleteNote()
//                true
//            }
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> {
//                true
//            }
//        }
//    }

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

    private fun saveNote() {
        if (checkFormValidation()) {
            if (isEditAction()) {
                viewModel.updateGroup(parseFormIntoEntity())
            } else {
                viewModel.insertGroup(parseFormIntoEntity())
            }
        }
    }

    private fun deleteNote() {
        if (isEditAction()) {
            viewModel.deleteGroup(parseFormIntoEntity())
        }
    }

    private fun parseFormIntoEntity(): Group {
        return Group(
            name_group = "",
                    desc_group = ""
        ).apply {
            if (isEditAction()) {
                id = viewModel.groupId
            }
        }
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
        return viewModel.groupId != CommonConstant.UNSET_ID
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

    override fun processDialog(group: Group) {
        viewModel.insertGroup(group)
    }
}