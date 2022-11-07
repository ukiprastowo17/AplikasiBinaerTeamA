package com.binar.aplikasibinaerteama.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.databinding.ItemProfileMemberBinding
import com.binar.aplikasibinaerteama.ui.about.team.TeamActivity


class TeamAdapter(private val list: List<TeamActivity.teams>)
    : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemProfileMemberBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tvName.text = name.toString()
                binding.tvJob.text = job.toString()
                binding.ivImg.load(image.toString()) {
                    crossfade(true)
                }
            }
        }
    }

    inner class TeamViewHolder(val binding: ItemProfileMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

}