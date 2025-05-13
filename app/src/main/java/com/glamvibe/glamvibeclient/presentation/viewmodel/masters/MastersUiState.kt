package com.glamvibe.glamvibeclient.presentation.viewmodel.masters

import com.glamvibe.glamvibeclient.domain.model.Master
import com.glamvibe.glamvibeclient.utils.Status

data class MastersUiState(
    val masters: List<Master> = emptyList(),
    val filteredMasters: List<Master> = emptyList(),
    val categories: List<String> = emptyList(),
    val lastSelectedCategory: String? = null,
    val status: Status = Status.Idle
)
