package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.presentation.viewmodel.rescheduleAppointment.RescheduleAppointmentViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.appointments.AppointmentsViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.favourites.FavouritesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.masters.MastersViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.newAppointment.NewAppointmentViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.newClient.NewClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.promotion.PromotionViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.promotions.PromotionsViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.recommendations.RecommendationsViewModel
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

    viewModel {
        MastersViewModel(get())
    }

    viewModel { (clientId: Int) ->
        AppointmentsViewModel(get(), clientId)
    }

    viewModel { (appointmentId: Int, clientId: Int) ->
        RescheduleAppointmentViewModel(get(), get(), appointmentId, clientId)
    }

    viewModel { (clientId: Int) ->
        NewAppointmentViewModel(get(), get(), get(), clientId)
    }

    viewModel {
        PromotionsViewModel(get())
    }

    viewModel { (clientId: Int) ->
        PromotionViewModel(get(), clientId)
    }

    viewModel {
        RecommendationsViewModel(get())
    }
}