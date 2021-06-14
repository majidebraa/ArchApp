package com.majidebrahimi.local.base

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.majidebrahimi.local.ArchAppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

private const val DATABASE = "DATABASE"

@RunWith(AndroidJUnit4::class)
abstract class BaseTest : KoinTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    protected val database: ArchAppDatabase by inject()

    @Before
    open fun setUp() {
        this.configureDi()
    }

    @After
    open fun tearDown() {
        stopKoin()
    }

    // CONFIGURATION
    private fun configureDi() {
        startKoin { listOf(configureLocalModuleTest(ApplicationProvider.getApplicationContext())) }
    }

    private fun configureLocalModuleTest(context: Context) {
        loadKoinModules(listOf(module {
            single(named(DATABASE)) {
                Room.inMemoryDatabaseBuilder(context, ArchAppDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()
            }
            factory { (get(named(DATABASE)) as ArchAppDatabase).userDao() }
        }))
    }
}