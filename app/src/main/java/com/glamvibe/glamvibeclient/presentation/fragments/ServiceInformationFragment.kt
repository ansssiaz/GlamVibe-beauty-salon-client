package com.glamvibe.glamvibeclient.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentServiceInformationBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.client.ClientViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.favourites.FavouritesViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.service.ServiceViewModel
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ServiceInformationFragment : Fragment() {
    companion object {
        const val ARG_ID = "ARG_ID"
    }

    private lateinit var binding: FragmentServiceInformationBinding
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()
    private val clientViewModel: ClientViewModel by activityViewModel<ClientViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceInformationBinding.inflate(inflater)

        toolbarViewModel.setTitle(getString(R.string.service_information_title))

        val serviceId = arguments?.getInt(ARG_ID)

        val clientId = clientViewModel.state.value.client?.id

        val serviceViewModel by viewModel<ServiceViewModel> { parametersOf(serviceId, clientId) }

        val favouritesViewModel by activityViewModel<FavouritesViewModel> { parametersOf(clientId) }

        binding.favourite.setOnClickListener {
            clientViewModel.state.value.client?.let {
                if (serviceId != null) {
                    serviceViewModel.changeFavourites()
                }
            }
        }

        serviceViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state.service != null) {
                    val priceWithPromotion = state.service.priceWithPromotion
                    val discountPercentage = state.service.discountPercentage
                    val price = state.service.price

                    if (priceWithPromotion != null && discountPercentage != null) {
                        binding.price.text = priceWithPromotion.toString()
                        binding.price.setTextColor(getColor(requireContext(), R.color.mauve))

                        binding.ruble.setImageResource(R.drawable.baseline_currency_ruble_24_mauve)

                        binding.oldPrice.text = price.toString()
                        binding.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                        binding.discountPercentage.text = "-$discountPercentage%"
                    } else {
                        binding.price.text = price.toString()
                        binding.oldPriceRuble.isVisible = false
                        binding.promotion.isVisible = false
                    }

                    val imageUrl = state.service.imageUrl

                    val radius =
                        requireContext().resources.getDimensionPixelSize(R.dimen.corner_radius)

                    if (imageUrl.isEmpty()) {
                        Glide.with(binding.root)
                            .load(R.drawable.empty_image)
                            .transform(RoundedCorners(radius))
                            .into(binding.serviceImage)
                    } else {
                        Glide.with(binding.root)
                            .load(imageUrl)
                            .apply(
                                RequestOptions()
                                    .override(250, 250)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .downsample(DownsampleStrategy.AT_LEAST)
                                    .encodeQuality(90)
                            )
                            .error(R.drawable.empty_image)
                            .transform(
                                MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(radius)
                                )
                            )
                            .into(binding.serviceImage)
                    }

                    binding.serviceName.text = state.service.name

                    binding.duration.text = state.service.duration.toString()

                    binding.serviceDescription.text = state.service.description

                    binding.favourite.setIconResource(
                        if (state.service.isFavourite) {
                            R.drawable.baseline_favorite_24
                        } else {
                            R.drawable.baseline_favorite_border_24
                        }
                    )

                    favouritesViewModel.getFavourites()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}