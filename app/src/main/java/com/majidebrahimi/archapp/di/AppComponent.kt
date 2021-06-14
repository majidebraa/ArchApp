package com.majidebrahimi.archapp.di

import com.majidebrahimi.detail.di.featureDetailModule
import com.majidebrahimi.home.di.featureHomeModule
import com.majidebrahimi.local.di.localModule
import com.majidebrahimi.remote.di.createRemoteModule
import com.majidebrahimi.repository.di.repositoryModule

val appComponent= listOf(createRemoteModule("https://api.github.com/"), repositoryModule, featureHomeModule, featureDetailModule, localModule)