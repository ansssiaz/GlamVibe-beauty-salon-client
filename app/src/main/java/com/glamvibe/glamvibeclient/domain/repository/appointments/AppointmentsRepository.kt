package com.glamvibe.glamvibeclient.domain.repository.appointments

import com.glamvibe.glamvibeclient.data.model.request.NewAppointmentDateTime
import com.glamvibe.glamvibeclient.domain.model.Appointment

interface AppointmentsRepository {
    suspend fun getAppointments(clientId: Int): List<Appointment>
    suspend fun getAppointment(appointmentId: Int, clientId: Int): Appointment
    suspend fun makeAppointment(clientId: Int): Appointment
    suspend fun rescheduleAppointment(
        appointmentId: Int,
        clientId: Int,
        newAppointmentDateTime: NewAppointmentDateTime
    ): Appointment

    suspend fun cancelAppointment(
        appointmentId: Int,
        clientId: Int
    ): Appointment
}