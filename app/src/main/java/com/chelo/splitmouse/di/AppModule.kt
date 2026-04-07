package com.chelo.splitmouse.di

import androidx.room.Room
import com.chelo.splitmouse.data.local.AppDatabase
import com.chelo.splitmouse.data.repositories.EventRepositoryImpl
import com.chelo.splitmouse.data.repositories.ExpenseRepositoryImpl
import com.chelo.splitmouse.data.repositories.ParticipantRepositoryImpl
import com.chelo.splitmouse.domain.repositories.EventRepository
import com.chelo.splitmouse.domain.repositories.ExpenseRepository
import com.chelo.splitmouse.domain.repositories.ParticipantRepository
import com.chelo.splitmouse.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val appModule = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_db").build()
    }

    single {
        get<AppDatabase>().eventDao()
    }

    single<EventRepository> {
        EventRepositoryImpl(get())
    }

    single {
        get<AppDatabase>().participantDao()
    }

    single<ParticipantRepository> {
        ParticipantRepositoryImpl(get())
    }


    single {
        get<AppDatabase>().expenseDao()
    }

    single<ExpenseRepository> {
        ExpenseRepositoryImpl(get())
    }



    viewModel {
        MainViewModel(get())
    }


}
