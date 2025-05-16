package com.glamvibe.glamvibeclient.data.repository.appointments

import com.glamvibe.glamvibeclient.data.api.AppointmentsApi
import com.glamvibe.glamvibeclient.data.model.request.NewAppointment
import com.glamvibe.glamvibeclient.data.model.request.NewAppointmentDateTime
import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.domain.repository.appointments.AppointmentsRepository

class AppointmentsRepositoryImpl(private val api: AppointmentsApi) : AppointmentsRepository {
    override suspend fun getAppointments(clientId: Int): List<Appointment> =
        api.getAppointments(clientId)

    override suspend fun getAppointment(appointmentId: Int, clientId: Int): Appointment =
        api.getAppointment(appointmentId, clientId)

    override suspend fun makeAppointment(
        clientId: Int,
        newAppointment: NewAppointment
    ): Appointment = api.makeAppointment(clientId, newAppointment)

    override suspend fun rescheduleAppointment(
        appointmentId: Int,
        clientId: Int,
        newAppointmentDateTime: NewAppointmentDateTime
    ): Appointment =
        api.rescheduleAppointment(appointmentId, clientId, newAppointmentDateTime)

    override suspend fun cancelAppointment(
        appointmentId: Int,
        clientId: Int,
    ): Appointment =
        api.cancelAppointment(appointmentId, clientId)
}