package com.binar.aplikasibinaerteama.ui.about.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.aplikasibinaerteama.R
import com.binar.aplikasibinaerteama.adapter.TeamAdapter
import com.binar.aplikasibinaerteama.databinding.ActivityTeamBinding


class TeamActivity : AppCompatActivity() {

    data class teams(val name: String, val job: String,  val desc: String, val image: String)



    private val teamsApp = listOf(
        teams("Uki Prastowo" , "Leader",  "I'm a fullstack developer from Tulungagung, East Java. Graduate from Gunadarma University in 2015, working at pharmaceutical company private companies since 7 years ago","https://firebasestorage.googleapis.com/v0/b/latihan-e23ac.appspot.com/o/ic_uki.jpeg?alt=media&token=6783d6de-5148-4c8d-bcfa-650ff824c0da"),
        teams("Adhi Setyo Nugroho" , "Scrum",  "Hi i\\'m Adhi Setyo Nugoroho graduate from University of Atma Jaya Yogyakarta in 2020, working at contact center / customer service at Bukalapak since 2021.I\\'m currently studying about android programming in my spare time","https://firebasestorage.googleapis.com/v0/b/latihan-e23ac.appspot.com/o/ic_adhi.jpg?alt=media&token=84614d0a-fded-4160-a4ad-c63a8524570d") ,
        teams("Lukas Agus Budi Prasetya" , "Design",  "Since childhood, I always wondering everything about technology like phone apps, games, television or anything tech-related. I always want to find out how those things work.  Today, not much has changed. I\\'m still wondering about technology. The difference is now I also develop mobile apps and websites to helping people and company through online interactions. My recent website project is developing website for UMKM to selling their products. Also, my recent android developing project is creating group maker apps. Currently, I’m trying to build my skills and experience with Android developing in my spare time","https://firebasestorage.googleapis.com/v0/b/latihan-e23ac.appspot.com/o/ic_lukas.jpg?alt=media&token=a2eb9620-69d6-4efc-8b24-0aaf87d5a435"),
        teams("Muhammad Rodan Duamar" , "Developer",  "Hi i\\'m Muhammad Rodan Duamar and currently 19 years old. I\\'m graduated from SMA Negeri 4 Yogyakarta, who has an interest in programming especially in the development of Android-based mobile applications.","https://firebasestorage.googleapis.com/v0/b/latihan-e23ac.appspot.com/o/ic_rodan.jpg?alt=media&token=57c52247-bf82-4249-ae6e-d79ed02f0c10") ,
        teams("Eri Samsudin" , "Developer",  "I\\'m Eri Samsudin, currently i learning android programming at Binar Academy. In this chapter, i have project with my group to make a application about Group Maker","raising_arizona.jpg") ,
        teams("Habio Fatwa Sidiq" , "Developer",  "Hi i\\'m Habio Fatwa Sidiq, currently 31 years old and next year 32 years. At this time I\\'m learning android programming","https://firebasestorage.googleapis.com/v0/b/latihan-e23ac.appspot.com/o/ic_habio.jpg?alt=media&token=f66ec042-4241-4624-b236-2fd692c81d79") ,

        )

    private val binding: ActivityTeamBinding by lazy {
        ActivityTeamBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.listRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TeamActivity)
            adapter = TeamAdapter(teamsApp)
        }
    }


}