package com.glamvibe.glamvibeclient.data.api

import com.glamvibe.glamvibeclient.domain.model.Service
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouritesApi {
    @GET("favourites/{clientId}")
    suspend fun getFavourites(@Path("clientId") clientId: Int): List<Service>

    @POST("favourites/{clientId}/services/{serviceId}")
    suspend fun addToFavourites(@Path("clientId") clientId: Int, @Path("serviceId") serviceId: Int)

    @DELETE("favourites/{clientId}/services/{serviceId}")
    suspend fun deleteFromFavourites(@Path("clientId") clientId: Int, @Path("serviceId") serviceId: Int)
}