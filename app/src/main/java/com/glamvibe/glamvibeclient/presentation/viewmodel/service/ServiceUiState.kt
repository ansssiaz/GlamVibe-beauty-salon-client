package com.glamvibe.glamvibeclient.presentation.viewmodel.service

import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.utils.Status

data class ServiceUiState(
    val service: Service? = null,
    val status: Status = Status.Idle,
)