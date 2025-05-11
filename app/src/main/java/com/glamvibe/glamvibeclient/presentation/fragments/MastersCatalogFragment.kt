package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentCatalogMastersBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel

class MastersCatalogFragment : Fragment() {
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private lateinit var binding: FragmentCatalogMastersBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogMastersBinding.inflate(inflater)
        toolbarViewModel.setTitle(getString(R.string.catalog_masters_title))
        return binding.root
    }
}