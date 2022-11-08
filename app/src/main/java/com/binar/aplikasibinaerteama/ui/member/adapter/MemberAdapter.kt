package com.binar.aplikasibinaerteama.ui.member.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ItemMemberBinding


class MemberAdapter(private val itemClick: (Member) -> Unit) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    var playersArrList: ArrayList<String>? = null
    private var items: MutableList<Member> = mutableListOf()

    fun setItems(items: List<Member>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MemberViewHolder(private val binding: ItemMemberBinding, val itemClick: (Member) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Member) {
            binding.tvIdMember.text = item.id.toString()
            binding.tvNameMember.text = item.nameMember


            with(item) {
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}