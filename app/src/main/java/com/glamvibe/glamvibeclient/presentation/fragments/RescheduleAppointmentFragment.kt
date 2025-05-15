package com.glamvibe.glamvibeclient.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentRescheduleAppointmentBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.appointment.AppointmentViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.Locale

class RescheduleAppointmentFragment : Fragment() {
    companion object {
        const val ARG_ID = "ARG_ID"
    }

    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private lateinit var binding: FragmentRescheduleAppointmentBinding
    private var selectedChip: Chip? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRescheduleAppointmentBinding.inflate(inflater)

        val appointmentId = arguments?.getInt(ARG_ID)

        val clientId = clientViewModel.state.value.client?.id

        val appointmentViewModel by viewModel<AppointmentViewModel> {
            parametersOf(
                appointmentId,
                clientId
            )
        }

        toolbarViewModel.setTitle(getString(R.string.reschedule_appointment_fragment))

        binding.rescheduleButton.isEnabled = false
        binding.rescheduleButton.alpha = 0.5f

        val initialDate = appointmentViewModel.state.value.appointment?.date

        if (initialDate != null) {
            binding.appointmentDatePicker.init(
                initialDate.year,
                initialDate.month.value - 1,
                initialDate.dayOfMonth,
                null
            )
        }

        binding.appointmentDatePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = LocalDate(year, monthOfYear + 1, dayOfMonth)
            appointmentViewModel.onDateSelected(selectedDate)
        }

        binding.rescheduleButton.setOnClickListener {
            val year = binding.appointmentDatePicker.year
            val month = binding.appointmentDatePicker.month + 1
            val day = binding.appointmentDatePicker.dayOfMonth
            val newDate = LocalDate(year, month, day)

            val weekDay = newDate.dayOfWeek

            val checkedChipId = binding.timeChipGroup.checkedChipId

            val chip = binding.timeChipGroup.findViewById<Chip>(checkedChipId)
            val timeText = chip.text.toString()
            val newTime = LocalTime.parse(timeText)
            appointmentViewModel.reschedule(newDate, newTime, weekDay)
        }

        appointmentViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state.appointment != null) {
                    binding.service.text = "Услуга: ${
                        state.appointment.service.replaceFirstChar {
                            if (!it.isLowerCase()) it.lowercase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                    }"

                    if (state.appointment.master.patronymic != null) {
                        binding.master.text =
                            "Мастер: ${state.appointment.master.lastname} ${state.appointment.master.name.first()}.${state.appointment.master.patronymic.first()}."
                    } else {
                        binding.master.text =
                            "Мастер: ${state.appointment.master.lastname} ${state.appointment.master.name.first()}."
                    }

                    binding.timeChipGroup.removeAllViews()
                    selectedChip = null

                    //binding.noAvailableTimeTitle.isVisible = state.availableSlots.isEmpty()

                    state.availableSlots.forEach { time ->
                        val chip = Chip(requireContext())
                        chip.id = View.generateViewId()

                        val timeString = "%02d:%02d".format(time.hour, time.minute)

                        chip.chipBackgroundColor = ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.chip_bg_selector
                        )
                        chip.chipStrokeColor =
                            ContextCompat.getColorStateList(requireContext(), R.color.brown)
                        chip.chipStrokeWidth = 1f
                        chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))

                        chip.text = timeString
                        chip.isClickable = true
                        chip.isCheckable = true

                        binding.timeChipGroup.addView(chip)
                    }

                    binding.timeChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                        binding.rescheduleButton.isEnabled = checkedIds.isNotEmpty()
                        if (checkedIds.isNotEmpty()) {
                            binding.rescheduleButton.alpha = 1f
                        }
                    }

                    if (state.isRescheduled) {
                        findNavController().navigateUp()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}