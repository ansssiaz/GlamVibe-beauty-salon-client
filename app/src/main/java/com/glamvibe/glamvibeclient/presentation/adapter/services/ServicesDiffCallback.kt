package com.glamvibe.glamvibeclient.presentation.adapter.services

import androidx.recyclerview.widget.DiffUtil
import com.glamvibe.glamvibeclient.domain.model.Service

class ServicesDiffCallback : DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Service, newItem: Service): Any? =
        ServicePayload(
            favourite = newItem.isFavourite.takeIf {
                it != oldItem.isFavourite
            }
        )
            .takeIf {
                it.isNotEmpty()
            }
}