package com.binar.aplikasibinaerteama.di

import android.content.Context
import com.binar.aplikasibinaerteama.data.pref.Preference
import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSource
import com.binar.aplikasibinaerteama.data.pref.PreferenceDataSourceImpl
import com.binar.aplikasibinaerteama.data.repository.LocalRepository
import com.binar.aplikasibinaerteama.data.repository.LocalRepositoryImpl
import com.binar.aplikasibinaerteama.data.room.dao.GroupDao
import com.binar.aplikasibinaerteama.data.room.dao.MemberDao
import com.binar.aplikasibinaerteama.data.room.dao.ResultDao
import com.binar.aplikasibinaerteama.data.room.datasource.*
import com.catnip.notepadku.data.AppDatabase


object ServiceLocator {

    fun provideUserPreference(context: Context): Preference {
        return Preference(context)
    }

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideMemberDao(context: Context): MemberDao {
        return provideAppDatabase(context).memberDao()
    }


    fun provideGroupDao(context: Context): GroupDao {
        return provideAppDatabase(context).groupDao()
    }

    fun provideResultDao(context: Context): ResultDao {
        return provideAppDatabase(context).resultDao()
    }

    fun provideMemberDataSource(context: Context): MemberDataSource {
        return MemberDataSourceImpl(provideMemberDao(context))
    }


    fun provideGroupDataSource(context: Context): GroupDataSource {
        return GroupDataSourceImpl(provideGroupDao(context))
    }


    fun provideResultDataSource(context: Context): ResultDataSource {
        return ResultDataSourceImpl(provideResultDao(context))
    }


    fun providePreferenceDataSource(context: Context): PreferenceDataSource {
        return PreferenceDataSourceImpl(provideUserPreference(context))
    }

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            providePreferenceDataSource(context),
            provideMemberDataSource(context),
            provideGroupDataSource(context),
            provideResultDataSource(context)
        )
    }


}