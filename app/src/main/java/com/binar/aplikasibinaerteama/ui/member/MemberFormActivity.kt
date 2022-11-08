package com.binar.aplikasibinaerteama.ui.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.RandomizeActivity
import com.binar.aplikasibinaerteama.base.BaseActivity
import com.binar.aplikasibinaerteama.base.GenericViewModelFactory
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ActivityMemberFormBinding
import com.binar.aplikasibinaerteama.di.ServiceLocator
import com.binar.aplikasibinaerteama.dialogs.AddMemberDialog
import com.binar.aplikasibinaerteama.ui.member.adapter.MemberAdapter
import com.binar.aplikasibinaerteama.wrapper.Resource


class MemberFormActivity : BaseActivity<ActivityMemberFormBinding>(ActivityMemberFormBinding::inflate) , AddMemberDialog.DialogListener {

    private val viewModel: MemberViewModel by lazy {
        GenericViewModelFactory(MemberViewModel(ServiceLocator.provideLocalRepository(this))).create(
            MemberViewModel::class.java
        )
    }


    var playersArrList: ArrayList<String>? = null

    private val nameGroup: String? by lazy {
        intent.getStringExtra("name_group")
    }

    private val idGroup: String? by lazy {
        intent.getStringExtra("id_group")
    }

    private val adapter: MemberAdapter by lazy {
        MemberAdapter {

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
        binding.btnRandom.visibility = View.GONE
        binding.fabAdd.setOnClickListener {
            AddMemberDialog().show(supportFragmentManager, "List member")
        }


        binding.btnRandom.setOnClickListener {
            val intent = Intent(this@MemberFormActivity, RandomizeActivity::class.java)
            intent.putExtra("id_group", idGroup)
            intent.putExtra("name_group", nameGroup)
            intent.putExtra("numberOfTeams", Integer.parseInt(binding.edtJumlahTim.text.toString()))
            intent.putStringArrayListExtra("data", playersArrList)
            startActivity(intent)
            Toast.makeText(this@MemberFormActivity, "Go To About Activity", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun initData() {
        binding.tvTitleNotes.text = nameGroup
        Log.d("datagroup",idGroup.toString())
        idGroup?.let { viewModel.getAllGroupByGroup(it) }
    }



    private fun observeData() {
        viewModel.initialDataResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    showError(it.message)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    showData(it.data)

                    playersArrList!!.clear()
                    it.data?.forEach {
                        playersArrList!!.add(it.nameMember)
                    }
                    binding.btnRandom.visibility = View.VISIBLE
                    Log.d("datakonver", playersArrList.toString())




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

    private fun saveNote() {
        if (checkFormValidation()) {
            if (isEditAction()) {
                viewModel.updateMember(parseFormIntoEntity())
            } else {
                viewModel.insertMember(parseFormIntoEntity())
            }
        }
    }

    private fun deleteNote() {
        if (isEditAction()) {
            viewModel.deleteMember(parseFormIntoEntity())
        }
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


    private fun showData(data: List<Member>?) {




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
        binding.rvNotes.adapter = this@MemberFormActivity.adapter
    }

    companion object {
        fun startActivity(context: Context, id: Int? = null) {
            context.startActivity(Intent(context, MemberFormActivity::class.java).apply {
                id?.let {
                    putExtra(CommonConstant.EXTRAS_ID_NOTE, id)
                }
            })
        }
    }

    override fun processDialog(memberName: String) {
            val data = Member(idGroup = idGroup.toString(), nameMember = memberName)
            viewModel.insertMember(data)

    }
}


