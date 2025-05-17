package com.glamvibe.glamvibeclient.presentation.viewmodel.promotions

import com.glamvibe.glamvibeclient.domain.model.Promotion
import com.glamvibe.glamvibeclient.utils.Status

data class PromotionsUiState(
    val promotions: List<Promotion> = emptyList(),
    val status: Status = Status.Idle
)
