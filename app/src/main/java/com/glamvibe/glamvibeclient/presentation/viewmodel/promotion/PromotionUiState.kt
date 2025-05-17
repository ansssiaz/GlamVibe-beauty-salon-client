package com.glamvibe.glamvibeclient.presentation.viewmodel.promotion

import com.glamvibe.glamvibeclient.domain.model.Promotion
import com.glamvibe.glamvibeclient.utils.Status

data class PromotionUiState(
    val promotion: Promotion? = null,
    val status: Status = Status.Idle
)
