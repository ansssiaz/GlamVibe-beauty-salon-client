package com.glamvibe.glamvibeclient.domain.repository.masters

import com.glamvibe.glamvibeclient.domain.model.Master

interface MastersRepository {
    suspend fun getMasters(): List<Master>
    suspend fun getMaster(masterId: Int): Master
    suspend fun getMasterWithCurrentAppointments(masterId: Int): Master
    suspend fun getMastersWithCurrentAppointments(): List<Master>
}