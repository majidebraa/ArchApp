package com.majidebrahimi.home.di

import com.majidebrahimi.home.HomeViewModel
import com.majidebrahimi.domain.usecase.home.GetTopUsersUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    factory { GetTopUsersUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }
}