package com.glamvibe.glamvibeclient.data.repository.services

import com.glamvibe.glamvibeclient.data.api.ServicesApi
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.domain.repository.services.ServicesRepository

class ServicesRepositoryImpl(private val api: ServicesApi) : ServicesRepository {
    override suspend fun getServices(clientId: Int): List<Service> = api.getServices(clientId)
    override suspend fun getService(serviceId: Int, clientId: Int): Service =
        api.getService(serviceId, clientId)
}