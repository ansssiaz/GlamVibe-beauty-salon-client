package com.glamvibe.glamvibeclient.data.api

import com.glamvibe.glamvibeclient.domain.model.Service
import retrofit2.http.GET
import retrofit2.http.Path

interface ServicesApi {
    @GET("services/clients/{id}")
    suspend fun getServices(@Path("id") clientId: Int): List<Service>

    @GET("services/{serviceId}/clients/{clientId}")
    suspend fun getService(
        @Path("serviceId") serviceId: Int,
        @Path("clientId") clientId: Int
    ): Service
}