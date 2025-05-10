package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater)


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