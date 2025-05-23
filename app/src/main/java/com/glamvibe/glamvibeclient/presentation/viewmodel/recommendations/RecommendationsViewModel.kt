package com.glamvibe.glamvibeclient.presentation.viewmodel.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val repository: ClientNetworkRepository,
) : ViewModel() {
    private var _state = MutableStateFlow(RecommendationsUiState())
    val state = _state.asStateFlow()

    fun getRecommendations(clientId: Int) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val recommendations = repository.getRecommendations(clientId)

                _state.update {
                    it.copy(
                        recommendations = recommendations,
                        status = Status.Idle
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}