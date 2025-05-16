package com.glamvibe.glamvibeclient.presentation.adapter.appointments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.databinding.CardLastAppointmentBinding
import com.glamvibe.glamvibeclient.domain.model.Appointment

class LastAppointmentsAdapter :
    ListAdapter<Appointment, LastAppointmentsViewHolder>(AppointmentsDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LastAppointmentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardLastAppointmentBinding.inflate(layoutInflater, parent, false)
        val viewHolder = LastAppointmentsViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: LastAppointmentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}