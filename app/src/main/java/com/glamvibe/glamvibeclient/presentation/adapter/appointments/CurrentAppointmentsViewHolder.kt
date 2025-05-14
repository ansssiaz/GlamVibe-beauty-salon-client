package com.glamvibe.glamvibeclient.presentation.adapter.appointments

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.CardCurrentAppointmentBinding
import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.domain.model.AppointmentStatus
import com.glamvibe.glamvibeclient.domain.model.getStringByStatus
import com.glamvibe.glamvibeclient.domain.model.getStringByWeekDay
import java.time.format.TextStyle
import java.util.Locale

class CurrentAppointmentsViewHolder(private val binding: CardCurrentAppointmentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(appointment: Appointment) {
        when (appointment.status) {
            AppointmentStatus.IN_PROCESSING, AppointmentStatus.WAITING -> {
                binding.statusIcon.setImageResource(R.drawable.baseline_change_circle_24)
            }

            AppointmentStatus.DONE -> {
                binding.statusIcon.setImageResource(R.drawable.baseline_check_circle_24)
            }

            else -> {
                binding.statusIcon.setImageResource(R.drawable.baseline_remove_circle_24)
            }
        }

        binding.status.text = appointment.status.getStringByStatus()

        binding.month.text =
            appointment.date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        binding.day.text = appointment.date.dayOfMonth.toString()

        if (appointment.master.patronymic != null) {
            binding.masterData.text =
                "${appointment.master.lastname} ${appointment.master.name.first()}.${appointment.master.patronymic.first()}."
        } else {
            binding.masterData.text =
                "${appointment.master.lastname} ${appointment.master.name.first()}."
        }

        binding.service.text = appointment.service

        binding.weekDay.text = appointment.weekDay.getStringByWeekDay()
            .uppercase(Locale.getDefault())

        binding.time.text = appointment.time.toString()

        binding.comment.text = appointment.comment
    }
}