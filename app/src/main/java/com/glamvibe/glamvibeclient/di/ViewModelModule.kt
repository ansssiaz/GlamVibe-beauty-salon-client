package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.favourites.FavouritesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.newClient.NewClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.service.ServiceViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.services.ServicesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ClientViewModel(get(), get())
    }

    viewModel {
        NewClientViewModel(get())
    }

    viewModel {
        ServicesViewModel(get(), get())
    }

    viewModel { (serviceId: Int, clientId: Int) ->
        ServiceViewModel(get(), get(), serviceId, clientId)
    }

    viewModel { (clientId: Int) ->
        FavouritesViewModel(get(), clientId)
    }
}