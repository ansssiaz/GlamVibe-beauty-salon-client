package com.glamvibe.glamvibeclient.data.api

import com.glamvibe.glamvibeclient.data.model.request.NewAppointmentDateTime
import com.glamvibe.glamvibeclient.domain.model.Appointment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentsApi {
    @GET("appointments/{clientId}")
    suspend fun getAppointments(@Path("clientId") clientId: Int): List<Appointment>

    @GET("appointments/{appointmentId}/clients/{clientId}")
    suspend fun getAppointment(
        @Path("appointmentId") appointmentId: Int,
        @Path("clientId") clientId: Int
    ): Appointment

    @POST("appointments/{clientId}")
    suspend fun makeAppointment(@Path("clientId") clientId: Int): Appointment

    @POST("appointments/cancel/{appointmentId}/clients/{clientId}")
    suspend fun cancelAppointment(
        @Path("appointmentId") appointmentId: Int,
        @Path("clientId") clientId: Int
    ): Appointment

    @POST("appointments/reschedule/{appointmentId}/clients/{clientId}")
    suspend fun rescheduleAppointment(
        @Path("appointmentId") appointmentId: Int,
        @Path("clientId") clientId: Int,
        @Body newAppointmentDateTime: NewAppointmentDateTime
    ): Appointment
}