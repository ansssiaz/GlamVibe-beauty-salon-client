package com.glamvibe.glamvibeclient.presentation.viewmodel.newClient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.data.model.response.toClient
import com.glamvibe.glamvibeclient.domain.model.Client
import com.glamvibe.glamvibeclient.domain.model.toNewClient
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class NewClientViewModel(
    private val networkRepository: ClientNetworkRepository
) : ViewModel() {
    private var _state = MutableStateFlow(NewClientUiState())
    val state = _state.asStateFlow()

    fun register(
        lastname: String,
        name: String,
        patronymic: String?,
        birthDate: String,
        email: String,
        phone: String,
        login: String,
        password: String,
        formData: String?
    ) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val client = Client(
                    lastname = lastname,
                    name = name,
                    patronymic = patronymic,
                    birthDate = LocalDate.parse(birthDate),
                    email = email,
                    phone = phone,
                    login = login,
                    password = password,
                )

                val newClient = client.toNewClient(formData)
                val registeredClient = networkRepository.register(newClient).toClient()

                _state.update { it.copy(client = registeredClient, status = Status.Idle) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}