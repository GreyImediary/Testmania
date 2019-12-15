package com.grayimediary.testmania.di

import com.grayimediary.testmania.viewModel.LogInViewModel
import com.grayimediary.testmania.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LogInViewModel(get()) }
    viewModel { MainViewModel(get()) }
}