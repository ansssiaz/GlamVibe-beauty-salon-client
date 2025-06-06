package com.glamvibe.glamvibeclient.presentation.viewmodel.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.domain.repository.favourites.FavouritesRepository
import com.glamvibe.glamvibeclient.domain.repository.services.ServicesRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val servicesRepository: ServicesRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private var _state = MutableStateFlow(ServicesUiState())
    val state = _state.asStateFlow()

    fun getServices(clientId: Int) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val services = servicesRepository.getServices(clientId)
                val categories = services.map { it.category }.distinct()

                val filtered = _state.value.lastSelectedCategory?.let { category ->
                    if (category == "Все категории") services
                    else services.filter { it.category == category }
                } ?: services

                _state.update {
                    it.copy(
                        services = services,
                        categories = categories,
                        filteredServices = filtered,
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

    fun filterServicesByCategory(category: String?) {
        _state.update { state ->
            val filtered = when {
                category == null || category == "Все категории" -> state.services ?: emptyList()
                state.services == null -> emptyList()
                else -> state.services.filter { it.category == category }
            }

            state.copy(
                filteredServices = filtered,
                lastSelectedCategory = category
            )
        }
    }

    fun changeFavourites(clientId: Int, service: Service) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                if (service.isFavourite) {
                    favouritesRepository.deleteFromFavourites(clientId, service.id)
                } else {
                    favouritesRepository.addToFavourites(clientId, service.id)
                }

                _state.update { state ->
                    state.copy(
                        services = state.services?.map {
                            if (it.id == service.id) {
                                it.copy(isFavourite = !it.isFavourite)
                            } else {
                                it
                            }
                        },
                        filteredServices = state.filteredServices.map {
                            if (it.id == service.id) {
                                it.copy(isFavourite = !it.isFavourite)
                            } else {
                                it
                            }
                        },
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