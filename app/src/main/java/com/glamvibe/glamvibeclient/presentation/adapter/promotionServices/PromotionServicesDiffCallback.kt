package com.glamvibe.glamvibeclient.presentation.adapter.promotionServices

import androidx.recyclerview.widget.DiffUtil
import com.glamvibe.glamvibeclient.domain.model.Service

class PromotionServicesDiffCallback : DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem == newItem
}