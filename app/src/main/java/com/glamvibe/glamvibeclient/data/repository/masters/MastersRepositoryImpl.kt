package com.glamvibe.glamvibeclient.data.repository.masters

import com.glamvibe.glamvibeclient.data.api.MastersApi
import com.glamvibe.glamvibeclient.data.model.response.toMaster
import com.glamvibe.glamvibeclient.domain.model.Master
import com.glamvibe.glamvibeclient.domain.repository.masters.MastersRepository

class MastersRepositoryImpl(private val api: MastersApi) : MastersRepository {
    override suspend fun getMasters(): List<Master> = api.getMasters()
    override suspend fun getMaster(masterId: Int): Master = api.getMaster(masterId)
    override suspend fun getMasterWithCurrentAppointments(masterId: Int): Master =
        api.getMasterWithCurrentAppointments(masterId).toMaster()
    override suspend fun getMastersWithCurrentAppointments(): List<Master> =
        api.getMastersWithCurrentAppointments().map { it.toMaster() }
}