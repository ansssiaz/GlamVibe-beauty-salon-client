package com.glamvibe.glamvibeclient.di

import com.glamvibe.glamvibeclient.BuildConfig
import com.glamvibe.glamvibeclient.data.api.AppointmentsApi
import com.glamvibe.glamvibeclient.data.api.AuthInterceptor
import com.glamvibe.glamvibeclient.data.api.ClientApi
import com.glamvibe.glamvibeclient.data.api.FavouritesApi
import com.glamvibe.glamvibeclient.data.api.MastersApi
import com.glamvibe.glamvibeclient.data.api.ServicesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { AuthInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideClientApi(get()) }
    single { provideServicesApi(get()) }
    single { provideFavouritesApi(get()) }
    single { provideMastersApi(get()) }
    single { provideAppointmentsApi(get()) }
}

private val contentType = "application/json".toMediaType()
private val json = Json { ignoreUnknownKeys = true }

private fun provideOkHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    })
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://192.168.0.12:8080/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

private fun provideClientApi(retrofit: Retrofit): ClientApi = retrofit.create(ClientApi::class.java)

private fun provideServicesApi(retrofit: Retrofit): ServicesApi =
    retrofit.create(ServicesApi::class.java)

private fun provideFavouritesApi(retrofit: Retrofit): FavouritesApi =
    retrofit.create(FavouritesApi::class.java)

private fun provideMastersApi(retrofit: Retrofit): MastersApi =
    retrofit.create(MastersApi::class.java)

private fun provideAppointmentsApi(retrofit: Retrofit): AppointmentsApi =
    retrofit.create(AppointmentsApi::class.java)