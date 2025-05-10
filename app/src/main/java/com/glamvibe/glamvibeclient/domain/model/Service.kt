package com.glamvibe.glamvibeclient.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val duration: Int,
    val price: Int,
    val priceWithPromotion: Int? = null,
    val discountPercentage: Int? = null,
    val isFavourite: Boolean = false,
)