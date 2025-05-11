package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentNewAppointmentBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel

class NewAppointmentFragment: Fragment() {
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private lateinit var binding: FragmentNewAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAppointmentBinding.inflate(inflater)
        toolbarViewModel.setTitle(getString(R.string.new_appointment_title))
        return binding.root
    }
}