package com.glamvibe.glamvibeclient.presentation.viewmodel.favourites

import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.utils.Status

data class FavouritesUiState(
    val favourites: List<Service> = emptyList(),
    val status: Status = Status.Idle
)
