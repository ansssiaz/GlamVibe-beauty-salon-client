package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.data.repository.client.ClientLocalRepositoryImpl
import com.glamvibe.glamvibeclient.data.repository.client.ClientNetworkRepositoryImpl
import com.glamvibe.glamvibeclient.data.repository.favourites.FavouritesRepositoryImpl
import com.glamvibe.glamvibeclient.data.repository.masters.MastersRepositoryImpl
import com.glamvibe.glamvibeclient.data.repository.services.ServicesRepositoryImpl
import com.glamvibe.glamvibeclient.domain.repository.client.ClientLocalRepository
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository
import com.glamvibe.glamvibeclient.domain.repository.favourites.FavouritesRepository
import com.glamvibe.glamvibeclient.domain.repository.masters.MastersRepository
import com.glamvibe.glamvibeclient.domain.repository.services.ServicesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideClientNetworkRepository(get()) }
    single { provideClientLocalRepository(get()) }
    single { provideServicesRepository(get()) }
    single { provideFavouritesRepository(get()) }
    single { provideMastersRepository(get()) }

    single<ClientNetworkRepository> {
        return@single ClientNetworkRepositoryImpl(get())
    }

    single<ClientLocalRepository> {
        return@single ClientLocalRepositoryImpl(get())
    }

    single<ServicesRepository> {
        return@single ServicesRepositoryImpl(get())
    }

    single<FavouritesRepository> {
        return@single FavouritesRepositoryImpl(get())
    }

    single<MastersRepository> {
        return@single MastersRepositoryImpl(get())
    }
}

private fun provideClientNetworkRepository(repository: ClientNetworkRepositoryImpl): ClientNetworkRepository =
    repository

private fun provideClientLocalRepository(repository: ClientLocalRepositoryImpl): ClientLocalRepository =
    repository

private fun provideServicesRepository(repository: ServicesRepositoryImpl): ServicesRepository =
    repository

private fun provideFavouritesRepository(repository: FavouritesRepositoryImpl): FavouritesRepository =
    repository

private fun provideMastersRepository(repository: MastersRepositoryImpl): MastersRepository =
    repository