package com.glamvibe.glamvibeclient.data.repository.favourites

import com.glamvibe.glamvibeclient.data.api.FavouritesApi
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.domain.repository.favourites.FavouritesRepository

class FavouritesRepositoryImpl(private val api: FavouritesApi) : FavouritesRepository {
    override suspend fun getFavourites(clientId: Int): List<Service> = api.getFavourites(clientId)

    override suspend fun addToFavourites(clientId: Int, serviceId: Int) =
        api.addToFavourites(clientId, serviceId)

    override suspend fun deleteFromFavourites(clientId: Int, serviceId: Int) =
        api.deleteFromFavourites(clientId, serviceId)
}