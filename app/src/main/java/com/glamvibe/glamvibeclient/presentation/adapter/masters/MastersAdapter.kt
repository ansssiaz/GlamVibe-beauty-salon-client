package com.glamvibe.glamvibeclient.presentation.adapter.masters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.databinding.CardMasterBinding
import com.glamvibe.glamvibeclient.domain.model.Master

class MastersAdapter(
    private val listener: MastersListener,
) : ListAdapter<Master, MastersViewHolder>(MastersDiffCallback()) {

    interface MastersListener {
        fun onMakeAppointmentButtonClicked(master: Master)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MastersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardMasterBinding.inflate(layoutInflater, parent, false)
        val viewHolder = MastersViewHolder(binding)

        binding.makeAppointmentButton.setOnClickListener {
            listener.onMakeAppointmentButtonClicked(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MastersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}