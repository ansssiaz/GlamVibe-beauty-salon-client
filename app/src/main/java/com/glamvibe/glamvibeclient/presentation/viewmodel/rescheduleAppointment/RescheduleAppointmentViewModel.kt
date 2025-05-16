package com.glamvibe.glamvibeclient.presentation.viewmodel.rescheduleAppointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.data.model.request.NewAppointmentDateTime
import com.glamvibe.glamvibeclient.domain.model.toWeekDay
import com.glamvibe.glamvibeclient.domain.repository.appointments.AppointmentsRepository
import com.glamvibe.glamvibeclient.domain.repository.masters.MastersRepository
import com.glamvibe.glamvibeclient.utils.Status
import com.glamvibe.glamvibeclient.utils.plusMinutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class RescheduleAppointmentViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    private val mastersRepository: MastersRepository,
    private val appointmentId: Int,
    private val clientId: Int
) : ViewModel() {
    private var _state = MutableStateFlow(RescheduleAppointmentUiState())
    val state = _state.asStateFlow()

    init {
        loadAppointmentInformation()
    }

    private fun loadAppointmentInformation() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val appointment = appointmentsRepository.getAppointment(appointmentId, clientId)

                val masterWithAppointments =
                    mastersRepository.getMasterWithCurrentAppointments(appointment.master.id)

                _state.update { state ->
                    state.copy(
                        appointment = appointment,
                        masterSchedule = masterWithAppointments.schedule,
                        masterCurrentAppointments = masterWithAppointments.appointments,
                        status = Status.Idle
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        _state.update {
            it.copy(selectedDate = date)
        }

        val workingDay =
            state.value.masterSchedule.find { it.weekDay == date.dayOfWeek.toWeekDay() }

        if (workingDay == null) {
            _state.update {
                it.copy(
                    availableSlots = emptyList(),
                )
            }
            return
        }

        val bookedTimes = state.value.masterCurrentAppointments
            .filter { it.date == date }
            .map { it.time }
        val slots = mutableListOf<LocalTime>()

        var slot = workingDay.startTime
        while (slot < workingDay.endTime) {
            if (!bookedTimes.contains(slot)) {
                slots.add(slot)
            }
            slot = slot.plusMinutes(60)
        }

        _state.update {
            it.copy(availableSlots = slots)
        }
    }

    fun onTimeSelected(time: LocalTime) {
        _state.update {
            it.copy(selectedTime = time)
        }
    }

    fun reschedule(date: LocalDate, time: LocalTime, weekDay: DayOfWeek) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val newAppointmentDateTime = NewAppointmentDateTime(
                    date = date,
                    time = time,
                    weekDay = weekDay.toWeekDay()
                )

                appointmentsRepository.rescheduleAppointment(
                    appointmentId,
                    clientId,
                    newAppointmentDateTime
                )

                _state.update {
                    it.copy(
                        status = Status.Idle,
                        isRescheduled = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }
}