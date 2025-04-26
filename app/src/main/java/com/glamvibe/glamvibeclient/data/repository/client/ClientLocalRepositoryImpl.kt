package com.glamvibe.glamvibeclient.data.repository.client

import android.content.Context
import androidx.core.content.edit
import com.glamvibe.glamvibeclient.data.model.response.TokenPair
import com.glamvibe.glamvibeclient.domain.repository.client.ClientLocalRepository

class ClientLocalRepositoryImpl(context: Context) : ClientLocalRepository {
    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN_EXPIRATION_TIME = "ACCESS_TOKEN_EXPIRATION_TIME"
    }

    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    override fun checkTokenPair(): TokenPair {
        val accessToken = preferences.getString(ACCESS_TOKEN, "") ?: ""
        val refreshToken = preferences.getString(REFRESH_TOKEN, "") ?: ""
        return TokenPair(accessToken, refreshToken)
    }

    override fun saveTokenPair(accessToken: String, refreshToken: String) {
        preferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
        }
    }

    override fun deleteTokenPair() {
        preferences.edit {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(ACCESS_TOKEN_EXPIRATION_TIME)
        }
    }

    override fun isAccessTokenExpired(): Boolean {
        val expirationTime = preferences.getLong(ACCESS_TOKEN_EXPIRATION_TIME, 0)
        return System.currentTimeMillis() > expirationTime
    }

    override fun getTokenExpirationTime(): Long {
        return preferences.getLong(ACCESS_TOKEN_EXPIRATION_TIME, 0)
    }

    override fun saveTokenExpirationTime(expiresIn: Long) {
        val expirationTime = System.currentTimeMillis() + expiresIn * 1000
        preferences.edit {
            putLong(ACCESS_TOKEN_EXPIRATION_TIME, expirationTime)
        }
    }
}