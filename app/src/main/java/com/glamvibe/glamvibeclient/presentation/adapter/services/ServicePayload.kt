package com.glamvibe.glamvibeclient.presentation.adapter.services

data class ServicePayload(
    val favourite: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = (favourite != null)
}