package com.glamvibe.glamvibeclient.data.repository.masters

import com.glamvibe.glamvibeclient.data.api.MastersApi
import com.glamvibe.glamvibeclient.domain.model.Master
import com.glamvibe.glamvibeclient.domain.repository.masters.MastersRepository

class MastersRepositoryImpl(private val api: MastersApi) : MastersRepository {
    override suspend fun getMasters(): List<Master> = api.getMasters()
    override suspend fun getMaster(masterId: Int): Master = api.getMaster(masterId)
}