package com.glamvibe.glamvibeclient.presentation.adapter.appointments

import androidx.recyclerview.widget.DiffUtil
import com.glamvibe.glamvibeclient.domain.model.Appointment

class AppointmentsDiffCallback : DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean =
        oldItem == newItem
}