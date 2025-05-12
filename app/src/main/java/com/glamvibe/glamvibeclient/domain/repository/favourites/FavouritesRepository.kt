package com.glamvibe.glamvibeclient.domain.repository.favourites

import com.glamvibe.glamvibeclient.domain.model.Service

interface FavouritesRepository {
    suspend fun getFavourites(clientId: Int): List<Service>
    suspend fun addToFavourites(clientId: Int, serviceId: Int)
    suspend fun deleteFromFavourites(clientId: Int, serviceId: Int)
}