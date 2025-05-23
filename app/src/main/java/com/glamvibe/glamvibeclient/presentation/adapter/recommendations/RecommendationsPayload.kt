package com.glamvibe.glamvibeclient.presentation.adapter.recommendations

data class RecommendationsPayload(
    val favourite: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = (favourite != null)
}