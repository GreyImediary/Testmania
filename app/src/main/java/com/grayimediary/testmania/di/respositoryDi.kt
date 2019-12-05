package com.grayimediary.testmania.di

import com.grayimediary.testmania.repository.TestRepository
import com.grayimediary.testmania.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
    single { TestRepository(get()) }
}