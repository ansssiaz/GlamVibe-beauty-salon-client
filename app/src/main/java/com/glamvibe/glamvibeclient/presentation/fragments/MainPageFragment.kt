package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentMainPageBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainPageFragment : Fragment() {
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private lateinit var binding: FragmentMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater)
        toolbarViewModel.setTitle(getString(R.string.main_page_title))

        clientViewModel.checkTokenPair()

        clientViewModel.getProfileInformation()

        clientViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.client == null && isAdded) {
                    requireParentFragment().requireParentFragment().findNavController()
                        .navigate(R.id.action_bottomMenuFragment_to_authorizationFragment)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}