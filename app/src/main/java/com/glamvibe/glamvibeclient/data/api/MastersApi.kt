package com.glamvibe.glamvibeclient.data.api

import com.glamvibe.glamvibeclient.data.model.response.MasterWithAppointments
import com.glamvibe.glamvibeclient.domain.model.Master
import retrofit2.http.GET
import retrofit2.http.Path

interface MastersApi {
    @GET("masters")
    suspend fun getMasters(): List<Master>

    @GET("masters/{id}")
    suspend fun getMaster(@Path("id") masterId: Int): Master

    @GET("masters/{masterId}/appointments")
    suspend fun getMasterWithCurrentAppointments(@Path("masterId") masterId: Int): MasterWithAppointments

    @GET("masters/appointments")
    suspend fun getMastersWithCurrentAppointments(): List<MasterWithAppointments>
}