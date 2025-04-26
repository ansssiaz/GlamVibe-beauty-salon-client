package com.glamvibe.glamvibeclient.presentation.viewmodel.client

import com.glamvibe.glamvibeclient.domain.model.Client
import com.glamvibe.glamvibeclient.utils.Status

data class ClientUiState(
    val client: Client? = null,
    val status: Status = Status.Idle,
){
    val isError: Boolean
        get() = status is Status.Error && client == null
}
