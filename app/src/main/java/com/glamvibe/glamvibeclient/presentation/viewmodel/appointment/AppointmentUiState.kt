package com.glamvibe.glamvibeclient.presentation.viewmodel.appointment

import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.domain.model.CurrentAppointment
import com.glamvibe.glamvibeclient.domain.model.WorkingDay
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class AppointmentUiState(
    val appointment: Appointment? = null,
    val masterSchedule: List<WorkingDay> = emptyList(),
    val masterCurrentAppointments: List<CurrentAppointment> = emptyList(),
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val availableSlots: List<LocalTime> = emptyList(),
    val status: Status = Status.Idle,
    val isRescheduled: Boolean = false
)
