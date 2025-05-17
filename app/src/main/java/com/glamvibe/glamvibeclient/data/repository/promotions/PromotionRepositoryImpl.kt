package com.glamvibe.glamvibeclient.data.repository.promotions

import com.glamvibe.glamvibeclient.data.api.PromotionsApi
import com.glamvibe.glamvibeclient.domain.model.Promotion
import com.glamvibe.glamvibeclient.domain.repository.promotions.PromotionsRepository

class PromotionRepositoryImpl(private val api: PromotionsApi) : PromotionsRepository {
    override suspend fun getCurrentPromotions(): List<Promotion> = api.getCurrentPromotions()
    override suspend fun getPromotion(promotionId: Int): Promotion = api.getPromotion(promotionId)
}