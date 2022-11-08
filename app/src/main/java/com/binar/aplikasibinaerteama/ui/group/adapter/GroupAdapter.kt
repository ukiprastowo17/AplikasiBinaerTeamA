package com.binar.aplikasibinaerteama.ui.group.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.aplikasibinaerteama.data.room.entity.Group
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ItemGroupRowBinding
import com.binar.aplikasibinaerteama.databinding.ItemMemberBinding


class GroupAdapter(private val itemClick: (Group) -> Unit) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {


    private var items: MutableList<Group> = mutableListOf()

    fun setItems(items: List<Group>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class GroupViewHolder(private val binding: ItemGroupRowBinding, val itemClick: (Group) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Group) {
            binding.tvTitleList.text = item.name_group
            binding.tvDescList.text = item.desc_group


            with(item) {
                binding.btnAddMemberList .setOnClickListener {

                    itemClick(this)


                }

                binding.btnDeleteMemberList .setOnClickListener {

                    itemClick(this)


                }
            }

        }


    }

}

