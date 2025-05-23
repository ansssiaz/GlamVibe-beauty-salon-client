package com.glamvibe.glamvibeclient.presentation.viewmodel.recommendations

import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.utils.Status

data class RecommendationsUiState(
    val recommendations: List<Service> = emptyList(),
    val status: Status = Status.Idle
)
