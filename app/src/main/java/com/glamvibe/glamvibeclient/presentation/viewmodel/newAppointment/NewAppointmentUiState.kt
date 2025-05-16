package com.glamvibe.glamvibeclient.presentation.viewmodel.newAppointment

import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.domain.model.CurrentAppointment
import com.glamvibe.glamvibeclient.domain.model.Master
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.domain.model.WorkingDay
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class NewAppointmentUiState(
    val appointment: Appointment? = null,
    val services: List<Service> = emptyList(),
    val masters: List<Master> = emptyList(),
    val mastersNames: List<String> = emptyList(),
    val lastSelectedService: String? = null,
    val lastSelectedMaster: String? = null,
    val filteredMasters: List<Master> = emptyList(),
    val masterSchedule: List<WorkingDay> = emptyList(),
    val masterCurrentAppointments: List<CurrentAppointment> = emptyList(),
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val availableSlots: List<LocalTime> = emptyList(),
    val status: Status = Status.Idle,
)
