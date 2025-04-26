package com.glamvibe.glamvibeclient.domain.repository.client

import com.glamvibe.glamvibeclient.data.model.response.TokenPair

interface ClientLocalRepository {
    fun checkTokenPair(): TokenPair
    fun saveTokenPair(accessToken: String, refreshToken: String)
    fun deleteTokenPair()
    fun isAccessTokenExpired(): Boolean
    fun getTokenExpirationTime(): Long
    fun saveTokenExpirationTime(expiresIn: Long)
}