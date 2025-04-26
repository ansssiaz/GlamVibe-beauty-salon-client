package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.data.repository.client.ClientLocalRepositoryImpl
import com.glamvibe.glamvibeclient.data.repository.client.ClientNetworkRepositoryImpl
import com.glamvibe.glamvibeclient.domain.repository.client.ClientLocalRepository
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideClientNetworkRepository(get()) }
    single { provideClientLocalRepository(get()) }

    single<ClientNetworkRepository> {
        return@single ClientNetworkRepositoryImpl(get())
    }

    single<ClientLocalRepository> {
        return@single ClientLocalRepositoryImpl(get())
    }
}

private fun provideClientNetworkRepository(repository: ClientNetworkRepositoryImpl): ClientNetworkRepository =
    repository

private fun provideClientLocalRepository(repository: ClientLocalRepositoryImpl): ClientLocalRepository =
    repository