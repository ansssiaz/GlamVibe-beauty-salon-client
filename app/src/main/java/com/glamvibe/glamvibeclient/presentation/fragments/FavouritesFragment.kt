package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentFavouritesBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel

class FavouritesFragment : Fragment() {
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater)
        toolbarViewModel.setTitle(getString(R.string.favourites_title))
        return binding.root
    }
}