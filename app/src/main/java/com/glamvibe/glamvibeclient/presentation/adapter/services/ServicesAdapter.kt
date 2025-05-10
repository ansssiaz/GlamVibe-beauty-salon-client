package com.glamvibe.glamvibeclient.presentation.adapter.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.databinding.CardServiceBinding
import com.glamvibe.glamvibeclient.domain.model.Service

class ServicesAdapter(
    private val listener: ServicesListener,
) : ListAdapter<Service, ServiceViewHolder>(ServicesDiffCallback()) {

    interface ServicesListener {
        fun onFavouriteClicked(service: Service)
        fun onServiceImageClicked(service: Service)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardServiceBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ServiceViewHolder(binding)

        binding.serviceImage.setOnClickListener {
            listener.onServiceImageClicked(getItem(viewHolder.adapterPosition))
        }

        binding.favourite.setOnClickListener {
            listener.onFavouriteClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                if (it is ServicePayload) {
                    holder.bind(it)
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }
}