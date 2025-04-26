package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.newClient.NewClientViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ClientViewModel(get(), get())
    }

    viewModel {
        NewClientViewModel(get())
    }
}