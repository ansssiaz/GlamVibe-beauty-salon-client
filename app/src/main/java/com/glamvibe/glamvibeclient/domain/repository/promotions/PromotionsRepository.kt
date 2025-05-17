package com.glamvibe.glamvibeclient.domain.repository.promotions

import com.glamvibe.glamvibeclient.domain.model.Promotion

interface PromotionsRepository {
    suspend fun getCurrentPromotions(): List<Promotion>
    suspend fun getPromotion(promotionId: Int): Promotion
}