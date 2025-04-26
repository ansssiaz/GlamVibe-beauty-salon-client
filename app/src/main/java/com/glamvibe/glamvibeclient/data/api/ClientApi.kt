package com.glamvibe.glamvibeclient.data.api

import com.glamvibe.glamvibeclient.data.model.response.ClientResponse
import com.glamvibe.glamvibeclient.data.model.response.TokenPair
import com.glamvibe.glamvibeclient.data.model.request.LogInClient
import com.glamvibe.glamvibeclient.data.model.request.NewClient
import com.glamvibe.glamvibeclient.data.model.request.RefreshToken
import com.glamvibe.glamvibeclient.data.model.request.UpdatedClient
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientApi {
    @POST("auth/registration")
    suspend fun register(@Body newClient: NewClient): ClientResponse

    @POST("auth/login")
    suspend fun logIn(@Body logInClient: LogInClient): TokenPair

    @POST("auth/token-refresh")
    suspend fun refreshTokenPair(@Body refreshToken: RefreshToken): TokenPair

    @POST("auth/logout/{id}")
    suspend fun logOut(@Path("id") id: Int)

    @POST("me")
    suspend fun getProfileInformation(@Body refreshToken: RefreshToken): ClientResponse

    @PUT("me/{id}")
    suspend fun updateProfileInformation(@Path("id") id: Int, @Body newClient: UpdatedClient): ClientResponse
}