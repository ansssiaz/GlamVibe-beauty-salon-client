package com.glamvibe.glamvibeclient.presentation.adapter.appointments

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.CardCurrentAppointmentBinding
import com.glamvibe.glamvibeclient.domain.model.Appointment

class CurrentAppointmentsAdapter(
    private val listener: CurrentAppointmentsListener,
) : ListAdapter<Appointment, CurrentAppointmentsViewHolder>(AppointmentsDiffCallback()) {

    interface CurrentAppointmentsListener {
        fun onRescheduleClicked(appointment: Appointment)
        fun onCancelClicked(appointment: Appointment)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentAppointmentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardCurrentAppointmentBinding.inflate(layoutInflater, parent, false)
        val viewHolder = CurrentAppointmentsViewHolder(binding)

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it, Gravity.END, 0, R.style.PopupMenu).apply {
                inflate(R.menu.appointment_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.reschedule -> {
                            listener.onRescheduleClicked(getItem(viewHolder.adapterPosition))
                            true
                        }

                        R.id.cancel -> {
                            listener.onCancelClicked(getItem(viewHolder.adapterPosition))
                            true
                        }

                        else -> false
                    }
                }
                show()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CurrentAppointmentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}