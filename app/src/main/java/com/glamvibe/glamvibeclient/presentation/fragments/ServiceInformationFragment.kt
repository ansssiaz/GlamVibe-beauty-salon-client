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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.glamvibe.glamvibeclient.R
import com.glamvibe.glamvibeclient.databinding.FragmentServiceInformationBinding
import com.glamvibe.glamvibeclient.presentation.viewmodel.toolbar.ToolbarViewModel

class ServiceInformationFragment : Fragment() {
    companion object {
        const val ARG_NAME = "ARG_NAME"
        const val ARG_CATEGORY = "ARG_CATEGORY"
        const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
        const val ARG_IMAGE_URL = "ARG_IMAGE_URL"
        const val ARG_DURATION = "ARG_DURATION"
        const val ARG_PRICE = "ARG_PRICE"
        const val ARG_PRICE_WITH_PROMOTION = "ARG_PRICE_WITH_PROMOTION"
        const val ARG_DISCOUNT_PERCENTAGE = "ARG_DISCOUNT_PERCENTAGE"
        const val ARG_IS_FAVOURITE = "ARG_IS_FAVOURITE"
    }

    private lateinit var binding: FragmentServiceInformationBinding
    private val toolbarViewModel: ToolbarViewModel by activityViewModels<ToolbarViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceInformationBinding.inflate(inflater)

        toolbarViewModel.setTitle(getString(R.string.service_information_title))

        val priceWithPromotion = arguments?.getInt(ARG_PRICE_WITH_PROMOTION)
        val discountPercentage = arguments?.getInt(ARG_DISCOUNT_PERCENTAGE)
        val price = arguments?.getInt(ARG_PRICE)

        if (priceWithPromotion != 0 && discountPercentage != 0) {
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

        val isFavourite = arguments?.getBoolean(ARG_IS_FAVOURITE)
        binding.favourite.setIconResource(
            if (isFavourite == true) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )

        val imageUrl = arguments?.getString(ARG_IMAGE_URL)

        val radius = requireContext().resources.getDimensionPixelSize(R.dimen.corner_radius)

        if (imageUrl != null) {
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
        }

        binding.serviceName.text = arguments?.getString(ARG_NAME)

        binding.duration.text = arguments?.getInt(ARG_DURATION).toString()

        binding.serviceDescription.text = arguments?.getString(ARG_DESCRIPTION)

        return binding.root
    }
}