package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentFavouritesBinding
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.presentation.adapter.favourites.FavouritesAdapter
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.favourites.FavouritesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.parameter.parametersOf

class FavouritesFragment : Fragment() {
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater)
        toolbarViewModel.setTitle(getString(R.string.favourites_title))

        val clientId = clientViewModel.state.value.client?.id
        val favouritesViewModel by activityViewModel<FavouritesViewModel> { parametersOf(clientId) }

        val favouritesAdapter = FavouritesAdapter(
            object : FavouritesAdapter.FavouritesListener {
                override fun onMakeAppointmentButtonClicked(service: Service) {
                    TODO("Not yet implemented")
                }

                override fun onDeleteClicked(service: Service) {
                    clientViewModel.state.value.client?.let {
                        favouritesViewModel.deleteFromFavourites(service.id)
                    }
                }

                override fun onServiceImageClicked(service: Service) {
                    requireParentFragment().requireParentFragment().findNavController().navigate(
                        R.id.action_bottomMenuFragment_to_serviceInformationFragment,
                        bundleOf(
                            ServiceInformationFragment.ARG_ID to service.id,
                        )
                    )
                }
            }
        )

        binding.favouritesList.layoutManager = LinearLayoutManager(requireContext())
        binding.favouritesList.adapter = favouritesAdapter

        favouritesViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                favouritesAdapter.submitList(state.favourites)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}