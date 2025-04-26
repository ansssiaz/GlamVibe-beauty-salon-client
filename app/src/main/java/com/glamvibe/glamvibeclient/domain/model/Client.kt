package com.glamvibe.glamvibeclient.domain.model

import com.glamvibe.glamvibeclient.data.model.request.NewClient
import com.glamvibe.glamvibeclient.data.model.request.Role
import com.glamvibe.glamvibeclient.data.model.request.UpdatedClient
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Contextual


data class Client(
    val id: Int = 0,
    val lastname: String = "",
    val name: String = "",
    val patronymic: String? = null,
    @Contextual val birthDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val email: String = "",
    val phone: String = "",
    val login: String = "",
    val password: String = "",
    val accessToken: String = "",
    val refreshToken: String? = null,
)

fun Client.toNewClient(formData: String?) = NewClient(
    lastname = lastname,
    name = name,
    patronymic = patronymic,
    birthDate = birthDate,
    email = email,
    phone = phone,
    login = login,
    password = password,
    role = Role.CLIENT,
    formData = formData,
)

fun Client.toUpdatedClient() = UpdatedClient(
    lastname = lastname,
    name = name,
    patronymic = patronymic,
    birthDate = birthDate,
    email = email,
    phone = phone,
    login = login,
    password = password
)