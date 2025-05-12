package com.glamvibe.glamvibeclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentCatalogServicesBinding
import com.glamvibe.glamvibeclient.domain.model.Service
import com.glamvibe.glamvibeclient.presentation.adapter.services.ServicesAdapter
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.favourites.FavouritesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.services.ServicesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ServicesCatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogServicesBinding
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val servicesViewModel: ServicesViewModel by viewModel<ServicesViewModel>()
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private lateinit var categoriesAdapter: ArrayAdapter<String>
    private var currentCategories: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogServicesBinding.inflate(inflater)

        toolbarViewModel.setTitle(getString(R.string.catalog_services_title))

        val clientId = clientViewModel.state.value.client?.id

        val favouritesViewModel by activityViewModel<FavouritesViewModel> { parametersOf(clientId) }

        categoriesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_dropdown_item,
            mutableListOf<String>()
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }

        binding.spinner.adapter = categoriesAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position) as? String
                servicesViewModel.filterServicesByCategory(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                servicesViewModel.filterServicesByCategory(null)
            }
        }

        clientViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                it.client?.id?.let { clientId ->
                    servicesViewModel.getServices(clientId)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        val servicesAdapter = ServicesAdapter(
            object : ServicesAdapter.ServicesListener {
                override fun onFavouriteClicked(service: Service) {
                    clientViewModel.state.value.client?.let {
                        servicesViewModel.changeFavourites(
                            it.id,
                            service
                        )
                    }
                }

                override fun onServiceImageClicked(service: Service) {
                    findNavController().navigate(
                        R.id.action_servicesCatalogFragment_to_serviceInformationFragment,
                        bundleOf(
                            ServiceInformationFragment.ARG_ID to service.id,

                            )
                    )
                }
            }
        )

        binding.listOfServices.isNestedScrollingEnabled = false
        binding.listOfServices.layoutManager = LinearLayoutManager(requireContext())
        binding.listOfServices.adapter = servicesAdapter

        servicesViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                servicesAdapter.submitList(state.filteredServices)

                val newCategories = listOf("Все категории") + state.categories
                currentCategories = newCategories

                categoriesAdapter.clear()
                categoriesAdapter.addAll(newCategories)
                categoriesAdapter.notifyDataSetChanged()

                val position = state.lastSelectedCategory?.let { cat ->
                    newCategories.indexOf(cat)
                } ?: 0

                if (position >= 0) {
                    binding.spinner.setSelection(position)
                }

                favouritesViewModel.getFavourites()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}