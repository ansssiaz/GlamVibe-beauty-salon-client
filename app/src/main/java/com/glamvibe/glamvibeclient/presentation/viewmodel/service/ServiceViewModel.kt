package com.glamvibe.glamvibeclient.presentation.viewmodel.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.domain.repository.favourites.FavouritesRepository
import com.glamvibe.glamvibeclient.domain.repository.services.ServicesRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val favouritesRepository: FavouritesRepository,
    private val servicesRepository: ServicesRepository,
    private val serviceId: Int,
    private val clientId: Int
) : ViewModel() {
    private var _state = MutableStateFlow(ServiceUiState())
    val state = _state.asStateFlow()

    init {
        loadServiceInformation()
    }

    private fun loadServiceInformation() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val service = servicesRepository.getService(serviceId, clientId)
                _state.update { state ->
                    state.copy(
                        service = service,
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

    fun changeFavourites() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val service = state.value.service
                if (service != null) {
                    if (service.isFavourite) {
                        favouritesRepository.deleteFromFavourites(clientId, serviceId)
                    } else {
                        favouritesRepository.addToFavourites(clientId, serviceId)
                    }
                }

                if (service != null) {
                    _state.update { state ->
                        state.copy(
                            service = service.copy(
                                isFavourite = !service.isFavourite
                            ),
                            status = Status.Idle
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}