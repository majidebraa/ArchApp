package com.majidebrahimi.detail.di

import com.majidebrahimi.detail.DetailImageViewModel
import com.majidebrahimi.detail.DetailViewModel
import com.majidebrahimi.domain.usecase.detail.GetUserDetailUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetailModule = module {
    factory { GetUserDetailUseCase(get()) }
    viewModel { params -> DetailViewModel(get(), get(), params.get()) }
    viewModel { params ->DetailImageViewModel(params.get()) }
}