package com.glamvibe.glamvibeclient.presentation.viewmodel.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.domain.model.AppointmentStatus
import com.glamvibe.glamvibeclient.domain.model.toNewAppointment
import com.glamvibe.glamvibeclient.domain.repository.appointments.AppointmentsRepository
import com.glamvibe.glamvibeclient.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppointmentsViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    private val clientId: Int
) : ViewModel() {
    private var _state = MutableStateFlow(AppointmentsUiState())
    val state = _state.asStateFlow()

    init {
        getAppointments()
    }

    private fun getAppointments() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val appointments = appointmentsRepository.getAppointments(clientId)

                val currentAppointments =
                    appointments.filter { it.status == AppointmentStatus.IN_PROCESSING || it.status == AppointmentStatus.WAITING }

                val lastAppointments =
                    appointments.filter {
                        it.status == AppointmentStatus.DONE || it.status == AppointmentStatus.CANCELLATION_BY_THE_CLIENT
                                || it.status == AppointmentStatus.CANCELLATION_BY_THE_ADMINISTRATOR
                    }

                _state.update {
                    it.copy(
                        currentAppointments = currentAppointments,
                        lastAppointments = lastAppointments,
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

    fun reschedule(appointmentId: Int, appointment: Appointment) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val newAppointmentDateTime = appointment.toNewAppointment()
                appointmentsRepository.rescheduleAppointment(
                    appointmentId,
                    clientId,
                    newAppointmentDateTime
                )

                val appointments = appointmentsRepository.getAppointments(clientId)

                val currentAppointments =
                    appointments.filter { it.status == AppointmentStatus.IN_PROCESSING || it.status == AppointmentStatus.WAITING }

                val lastAppointments =
                    appointments.filter {
                        it.status == AppointmentStatus.DONE || it.status == AppointmentStatus.CANCELLATION_BY_THE_CLIENT
                                || it.status == AppointmentStatus.CANCELLATION_BY_THE_ADMINISTRATOR
                    }

                _state.update {
                    it.copy(
                        currentAppointments = currentAppointments,
                        lastAppointments = lastAppointments,
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

    fun cancel(
        appointmentId: Int
    ) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                appointmentsRepository.cancelAppointment(
                    appointmentId,
                    clientId,
                )

                val appointments = appointmentsRepository.getAppointments(clientId)

                val currentAppointments =
                    appointments.filter { it.status == AppointmentStatus.IN_PROCESSING || it.status == AppointmentStatus.WAITING }

                val lastAppointments =
                    appointments.filter {
                        it.status == AppointmentStatus.DONE || it.status == AppointmentStatus.CANCELLATION_BY_THE_CLIENT
                                || it.status == AppointmentStatus.CANCELLATION_BY_THE_ADMINISTRATOR
                    }

                _state.update {
                    it.copy(
                        currentAppointments = currentAppointments,
                        lastAppointments = lastAppointments,
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
}