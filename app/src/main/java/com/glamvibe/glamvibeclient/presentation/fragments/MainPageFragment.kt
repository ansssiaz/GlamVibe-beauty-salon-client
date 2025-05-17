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
import com.glamvibe.glamvibeclient.databinding.FragmentMainPageBinding
import com.glamvibe.glamvibeclient.domain.model.Promotion
import com.glamvibe.glamvibeclient.presentation.adapter.promotions.PromotionsAdapter
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.promotions.PromotionsViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPageFragment : Fragment() {
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val promotionsViewModel: PromotionsViewModel by viewModel<PromotionsViewModel>()
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

        val promotionsAdapter = PromotionsAdapter(
            object : PromotionsAdapter.PromotionsListener {
                override fun onPromotionImageClicked(promotion: Promotion) {
                    requireParentFragment().requireParentFragment().findNavController().navigate(
                        R.id.action_bottomMenuFragment_to_promotionFragment,
                        bundleOf(PromotionFragment.ARG_ID to promotion.id)
                    )
                }
            }
        )

        binding.listOfPromotions.setPadding(8, 0, 8, 0)
        binding.listOfPromotions.clipToPadding = false
        binding.listOfPromotions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.listOfPromotions.adapter = promotionsAdapter

        clientViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.client == null && isAdded) {
                    requireParentFragment().requireParentFragment().findNavController()
                        .navigate(R.id.action_bottomMenuFragment_to_authorizationFragment)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        promotionsViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                promotionsAdapter.submitList(it.promotions)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}