package com.binar.aplikasibinaerteama.ui.member.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.aplikasibinaerteama.data.room.entity.Member
import com.binar.aplikasibinaerteama.databinding.ItemMemberBinding
import com.binar.aplikasibinaerteama.model.ResultModel


class MemberAdapter( private val listener: OnCLickListenerMember) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    private var items: MutableList<Member> = mutableListOf()

    fun setItems(items: List<Member>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MemberViewHolder(private val binding: ItemMemberBinding,  private val listener: OnCLickListenerMember) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Member) {
            binding.tvIdMember.text = item.id.toString()
            binding.tvNameMember.text = item.nameMember


            binding.ivDeleteMember.setOnClickListener {
                listener.onDeleteClickListenerMember(item)
            }

        }
    }


    interface OnCLickListenerMember {
        fun onDeleteClickListenerMember(member: Member)
    }
}