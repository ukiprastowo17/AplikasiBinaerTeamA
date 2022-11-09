package com.binar.aplikasibinaerteama.ui.history.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.aplikasibinaerteama.databinding.ItemHistoryBinding
import com.binar.aplikasibinaerteama.databinding.ItemMemberBinding
import com.binar.aplikasibinaerteama.model.ResultModel


class HistoryAdapter(private val itemClick: (ResultModel) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var playersArrList: ArrayList<String>? = null
    private var items: MutableList<ResultModel> = mutableListOf()

    fun setItems(items: List<ResultModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class HistoryViewHolder(private val binding: ItemHistoryBinding, val itemClick: (ResultModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ResultModel) {
            binding.tvIdMember.text = item.name_result
            binding.tvNameMember.text = item.group_name_result


            with(item) {
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}