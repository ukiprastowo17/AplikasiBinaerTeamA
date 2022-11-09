package com.binar.aplikasibinaerteama.ui.group.adapter;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.binar.aplikasibinaerteama.constant.CommonConstant
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ItemGroupRowBinding
import com.binar.aplikasibinaerteama.databinding.ItemMemberBinding
import com.binar.aplikasibinaerteama.ui.member.MemberFormActivity


class GroupAdapter(private val listener:OnCLickListener) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {


    private var items: MutableList<Group> = mutableListOf()

    fun setItems(items: List<Group>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class GroupViewHolder(private val binding: ItemGroupRowBinding,
    private val listener: OnCLickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Group) {
            binding.tvTitleList.text = item.name_group
            binding.tvDescList.text = item.desc_group

            binding.btnAddMemberList.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(CommonConstant.GROUP_ID, item.id.toString())
                    putString(CommonConstant.KEY_GROUP_NAME, item.name_group.toString())
                }
                val intent = Intent(it.context, MemberFormActivity::class.java)
                intent.putExtras(bundle)
                it.context.startActivity(intent)
            }

            binding.btnDeleteMemberList.setOnClickListener {
                listener.onDeleteClickListener(item)
            }


            }

        }

    interface OnCLickListener {
        fun onDeleteClickListener(group: Group)
    }

    }





