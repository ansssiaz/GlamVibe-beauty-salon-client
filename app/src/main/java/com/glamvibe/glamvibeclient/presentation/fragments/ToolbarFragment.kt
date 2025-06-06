package com.glamvibe.glamvibeclient.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentToolbarBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ToolbarFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.beginTransaction()
            .setPrimaryNavigationFragment(this)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)

        val navigationArrow = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_back_24)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        binding.toolbar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val topLevelDestinations =
                setOf(
                    R.id.bottomMenuFragment,
                    R.id.authorizationFragment
                )

            if (topLevelDestinations.contains(destination.id)) {
                binding.toolbar.navigationIcon = null
            } else {
                binding.toolbar.navigationIcon = navigationArrow
            }
        }

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        toolbarViewModel.title.onEach { title ->
            binding.toolbar.title = title
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.toolbar.title = getString(R.string.app_name)

        return binding.root
    }
}