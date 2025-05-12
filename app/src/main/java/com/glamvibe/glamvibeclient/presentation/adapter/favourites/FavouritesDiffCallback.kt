package com.glamvibe.glamvibeclient.presentation.adapter.favourites

import androidx.recyclerview.widget.DiffUtil
import com.glamvibe.glamvibeclient.domain.model.Service

class FavouritesDiffCallback: DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean =
        oldItem == newItem
}