package com.glamvibe.glamvibeclient.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentAppointmentsBinding
import com.glamvibe.glamvibeclient.domain.model.Appointment
import com.glamvibe.glamvibeclient.presentation.adapter.appointments.CurrentAppointmentsAdapter
import com.glamvibe.glamvibeclient.presentation.adapter.appointments.LastAppointmentsAdapter
import com.glamvibe.glamvibeclient.presentation.viewmodel.appointments.AppointmentsViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AppointmentsFragment : Fragment() {
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private lateinit var binding: FragmentAppointmentsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentsBinding.inflate(inflater)

        toolbarViewModel.setTitle(getString(R.string.appointments_title))

        val clientId = clientViewModel.state.value.client?.id

        val appointmentsViewModel: AppointmentsViewModel by viewModel<AppointmentsViewModel> {
            parametersOf(
                clientId
            )
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            RescheduleAppointmentFragment.APPOINTMENT_RESCHEDULED_RESULT,
            viewLifecycleOwner
        ) { _, _ ->
            appointmentsViewModel.getAppointments()
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            NewAppointmentFragment.APPOINTMENT_CREATED_RESULT,
            viewLifecycleOwner
        ) { _, _ ->
            appointmentsViewModel.getAppointments()
        }

        val currentAppointmentsAdapter = CurrentAppointmentsAdapter(
            object : CurrentAppointmentsAdapter.CurrentAppointmentsListener {
                override fun onRescheduleClicked(appointment: Appointment) {
                    findNavController().navigate(
                        R.id.action_appointmentsFragment_to_rescheduleAppointmentFragment,
                        bundleOf(RescheduleAppointmentFragment.ARG_ID to appointment.id)
                    )
                }

                override fun onCancelClicked(appointment: Appointment) {
                    val dialogView =
                        layoutInflater.inflate(R.layout.dialog_cancel_appointment, null)
                    val buttonNo = dialogView.findViewById<MaterialButton>(R.id.exit_button)
                    val buttonYes = dialogView.findViewById<MaterialButton>(R.id.ok_button)

                    val dialog = AlertDialog.Builder(requireContext())
                        .setView(dialogView)
                        .setCancelable(true)
                        .create()

                    dialog.setOnShowListener {
                        buttonNo.setOnClickListener {
                            dialog.dismiss()
                        }

                        buttonYes.setOnClickListener {
                            appointmentsViewModel.cancel(appointment.id)
                            dialog.dismiss()
                        }
                    }
                    dialog.show()
                }
            }
        )

        binding.currentAppointmentsList.layoutManager = LinearLayoutManager(requireContext())
        binding.currentAppointmentsList.adapter = currentAppointmentsAdapter

        val lastAppointmentsAdapter = LastAppointmentsAdapter()
        binding.lastAppointmentsList.layoutManager = LinearLayoutManager(requireContext())
        binding.lastAppointmentsList.adapter = lastAppointmentsAdapter

        binding.currentAppointmentsList.isNestedScrollingEnabled = false
        binding.lastAppointmentsList.isNestedScrollingEnabled = false

        appointmentsViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                binding.noCurrentAppointmentsText.isVisible = state.currentAppointments.isEmpty()
                binding.noLastAppointmentsText.isVisible = state.lastAppointments.isEmpty()

                currentAppointmentsAdapter.submitList(state.currentAppointments)
                lastAppointmentsAdapter.submitList(state.lastAppointments)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}