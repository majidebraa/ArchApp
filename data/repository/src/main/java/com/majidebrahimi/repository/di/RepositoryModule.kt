package com.majidebrahimi.repository.di

import com.majidebrahimi.domain.repositories.UserRepository
import com.majidebrahimi.domain.utils.AppDispatchers
import com.majidebrahimi.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        AppDispatchers(
            Dispatchers.Main,
            Dispatchers.IO
        )
    }
    factory { UserRepositoryImpl(
        get(),
        get(),
        get()
    ) as UserRepository
    }
}