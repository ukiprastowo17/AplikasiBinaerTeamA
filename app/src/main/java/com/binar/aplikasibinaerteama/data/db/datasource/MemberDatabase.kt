package com.binar.aplikasibinaerteama.data.db.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.binar.aplikasibinaerteama.data.db.entity.Member
import com.binar.aplikasibinaerteama.data.db.dao.MemberDao

@Database(entities = [Member::class], version = 1)
abstract class MemberDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao

    companion object {
        private var instance: MemberDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MemberDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, MemberDatabase::class.java,
                    "db_member")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: MemberDatabase) {

        }
    }
}