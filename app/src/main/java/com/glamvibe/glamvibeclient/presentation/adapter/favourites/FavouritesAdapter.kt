package com.glamvibe.glamvibeclient.presentation.adapter.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.databinding.CardFavouritesServiceBinding
import com.glamvibe.glamvibeclient.domain.model.Service

class FavouritesAdapter(
    private val listener: FavouritesListener,
) : ListAdapter<Service, FavouritesViewHolder>(FavouritesDiffCallback()) {

    interface FavouritesListener {
        fun onMakeAppointmentButtonClicked(service: Service)
        fun onDeleteClicked(service: Service)
        fun onServiceImageClicked(service: Service)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardFavouritesServiceBinding.inflate(layoutInflater, parent, false)
        val viewHolder = FavouritesViewHolder(binding)

        binding.serviceImage.setOnClickListener {
            listener.onServiceImageClicked(getItem(viewHolder.adapterPosition))
        }

        binding.deleteService.setOnClickListener {
            listener.onDeleteClicked(getItem(viewHolder.adapterPosition))
        }

        binding.makeAppointmentButton.setOnClickListener {
            listener.onMakeAppointmentButtonClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}