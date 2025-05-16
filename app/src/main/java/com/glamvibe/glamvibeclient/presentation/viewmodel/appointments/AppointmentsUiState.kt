package com.glamvibe.glamvibeclient.presentation.viewmodel.appointments

import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.utils.Status

data class AppointmentsUiState(
    val currentAppointments: List<Appointment> = emptyList(),
    val lastAppointments: List<Appointment> = emptyList(),
    val status: Status =  Status.Idle
)
