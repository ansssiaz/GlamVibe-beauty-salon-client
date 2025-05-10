package com.glamvibe.glamvibeclient.domain.repository.services

import com.glamvibe.glamvibeclient.domain.model.Service

interface ServicesRepository {
    suspend fun getServices(clientId: Int): List<Service>
    //suspend fun addToFavourites(clientId: Int, serviceId: Int)
}