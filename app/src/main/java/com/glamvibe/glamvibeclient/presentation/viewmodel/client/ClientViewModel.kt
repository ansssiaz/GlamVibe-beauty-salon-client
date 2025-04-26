package com.glamvibe.glamvibeclient.presentation.viewmodel.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.data.model.response.toClient
import com.glamvibe.glamvibeclient.domain.model.Client
import com.glamvibe.glamvibeclient.domain.model.toUpdatedClient
import com.glamvibe.glamvibeclient.domain.repository.client.ClientLocalRepository
import com.glamvibe.glamvibeclient.domain.repository.client.ClientNetworkRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ClientViewModel(
    private val networkRepository: ClientNetworkRepository,
    private val localRepository: ClientLocalRepository
) : ViewModel() {
    private var _state = MutableStateFlow(ClientUiState())
    val state = _state.asStateFlow()

    fun checkTokenPair() {
        val tokenPair = localRepository.checkTokenPair()
        val client = if (tokenPair.accessToken.isNotEmpty() && tokenPair.refreshToken.isNotEmpty())
            Client(
                accessToken = tokenPair.accessToken,
                refreshToken = tokenPair.refreshToken
            ) else null
        localRepository.saveTokenPair(tokenPair.accessToken, tokenPair.refreshToken)
        _state.update { it.copy(client = client, status = Status.Idle) }
    }

    fun logIn(login: String, password: String) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val tokenPair = networkRepository.logIn(login, password)
                val client = Client(
                    accessToken = tokenPair.accessToken,
                    refreshToken = tokenPair.refreshToken
                )
                localRepository.saveTokenPair(tokenPair.accessToken, tokenPair.refreshToken)
                localRepository.saveTokenExpirationTime(1800)
                _state.update { it.copy(client = client, status = Status.Idle) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun logOut(id: Int) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                networkRepository.logOut(id)
                localRepository.deleteTokenPair()
                _state.update { it.copy(client = null, status = Status.Idle) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun getProfileInformation() {
        val refreshToken = localRepository.checkTokenPair().refreshToken

        if (refreshToken.isNotEmpty()) {
            _state.update { it.copy(status = Status.Loading) }

            viewModelScope.launch {
                try {
                    val client = networkRepository.getProfileInformation(refreshToken).toClient()
                    _state.update { it.copy(client = client, status = Status.Idle) }
                } catch (e: Exception) {
                    _state.update {
                        it.copy(status = Status.Error(e))
                    }
                }
            }
        }
    }

    fun updateProfileInformation(
        id: Int,
        lastname: String,
        name: String,
        patronymic: String?,
        birthDate: String,
        email: String,
        phone: String,
        login: String,
        password: String
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
                val updatedClient = networkRepository.updateProfileInformation(
                    id, client.toUpdatedClient()
                ).toClient()

                _state.update { it.copy(client = updatedClient, status = Status.Idle) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}