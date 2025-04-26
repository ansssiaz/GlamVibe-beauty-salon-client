package com.glamvibe.glamvibeclient.data.repository.client

import com.glamvibe.glamvibeclient.data.api.ClientApi
import com.glamvibe.glamvibeclient.data.model.response.ClientResponse
import com.glamvibe.glamvibeclient.data.model.request.RefreshToken
import com.glamvibe.glamvibeclient.data.model.response.TokenPair
import com.glamvibe.glamvibeclient.data.model.request.LogInClient
import com.glamvibe.glamvibeclient.data.model.request.NewClient
import com.glamvibe.glamvibeclient.data.model.request.UpdatedClient
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository

class ClientNetworkRepositoryImpl(private val api: ClientApi) : ClientNetworkRepository {
    override suspend fun register(client: NewClient): ClientResponse = api.register(client)

    override suspend fun logIn(login: String, password: String): TokenPair {
        val logInClient = LogInClient(login, password)
        return api.logIn(logInClient)
    }

    override suspend fun refreshTokenPair(refreshToken: String): TokenPair {
        val token = RefreshToken(refreshToken)
        return api.refreshTokenPair(token)
    }

    override suspend fun logOut(id: Int) = api.logOut(id)

    override suspend fun getProfileInformation(refreshToken: String): ClientResponse {
        val token = RefreshToken(refreshToken)
        return api.getProfileInformation(token)
    }

    override suspend fun updateProfileInformation(id: Int, newClient: UpdatedClient): ClientResponse =
        api.updateProfileInformation(id, newClient)
}