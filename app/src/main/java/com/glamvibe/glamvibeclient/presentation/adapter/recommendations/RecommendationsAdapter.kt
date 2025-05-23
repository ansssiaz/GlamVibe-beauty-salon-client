package com.glamvibe.glamvibeclient.presentation.adapter.recommendations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.databinding.CardRecommendationBinding
import com.glamvibe.glamvibeclient.domain.model.Service

class RecommendationsAdapter(
    private val listener: RecommendationsListener
) : ListAdapter<Service, RecommendationsViewHolder>(RecommendationsDiffCallback()) {

    interface RecommendationsListener {
        fun onServiceImageClicked(service: Service)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardRecommendationBinding.inflate(layoutInflater, parent, false)
        val viewHolder = RecommendationsViewHolder(binding)

        binding.serviceImage.setOnClickListener {
            listener.onServiceImageClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RecommendationsViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                if (it is RecommendationsPayload) {
                    holder.bind(it)
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }
}