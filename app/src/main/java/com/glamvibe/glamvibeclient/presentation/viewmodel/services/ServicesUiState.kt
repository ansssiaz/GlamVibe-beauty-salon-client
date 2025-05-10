package com.glamvibe.glamvibeclient.presentation.viewmodel.services

import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.utils.Status

data class ServicesUiState(
    val services: List<Service>? = null,
    val filteredServices: List<Service> = emptyList(),
    val categories: List<String> = emptyList(),
    val lastSelectedCategory: String? = null,
    val status: Status = Status.Idle,
)