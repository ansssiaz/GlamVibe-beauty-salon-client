package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentCatalogBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel

class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater)

        toolbarViewModel.setTitle(getString(R.string.catalog_title))

        binding.mastersItem.setOnClickListener {
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomMenuFragment_to_mastersCatalogFragment
                )
        }

        binding.servicesItem.setOnClickListener {
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomMenuFragment_to_servicesCatalogFragment
                )
        }

        return binding.root
    }
}