package com.glamvibe.glamvibeclient.domain.repository.client

import com.glamvibe.glamvibeclient.data.model.response.ClientResponse
import com.glamvibe.glamvibeclient.data.model.response.TokenPair
import com.glamvibe.glamvibeclient.data.model.request.NewClient
import com.glamvibe.glamvibeclient.data.model.request.UpdatedClient

interface ClientNetworkRepository {
    suspend fun register(client: NewClient): ClientResponse
    suspend fun logIn(login: String, password: String): TokenPair
    suspend fun refreshTokenPair(refreshToken: String): TokenPair
    suspend fun logOut(id: Int)
    suspend fun getProfileInformation(refreshToken: String): ClientResponse
    suspend fun updateProfileInformation(id: Int, newClient: UpdatedClient): ClientResponse
}