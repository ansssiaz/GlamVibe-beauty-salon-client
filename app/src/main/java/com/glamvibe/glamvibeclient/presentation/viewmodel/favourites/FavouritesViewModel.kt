package com.glamvibe.glamvibeclient.presentation.viewmodel.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.domain.repository.favourites.FavouritesRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val favouritesRepository: FavouritesRepository,
    private val clientId: Int
) : ViewModel() {
    private var _state = MutableStateFlow(FavouritesUiState())
    val state = _state.asStateFlow()

    init {
        getFavourites()
    }

    fun getFavourites() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val favourites = favouritesRepository.getFavourites(clientId)

                _state.update {
                    it.copy(
                        favourites = favourites,
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

    fun deleteFromFavourites(serviceId: Int) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                favouritesRepository.deleteFromFavourites(clientId, serviceId)
                val updatedFavourites = favouritesRepository.getFavourites(clientId)

                _state.update {
                    it.copy(
                        favourites = updatedFavourites,
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