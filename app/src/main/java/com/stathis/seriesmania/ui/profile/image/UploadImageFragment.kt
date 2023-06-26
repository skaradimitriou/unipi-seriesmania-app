package com.stathis.seriesmania.ui.profile.image

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.core.binding.setImage
import com.stathis.core.components.GenericBottomSheet
import com.stathis.core.components.GenericBottomSheet.Companion.GENERIC_BS_TAG
import com.stathis.core.ext.onSuccessCameraResult
import com.stathis.core.ext.onSuccessResult
import com.stathis.core.ext.setScreenTitle
import com.stathis.core.ext.toBitmap
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentUploadImageBinding
import com.stathis.seriesmania.ui.profile.ProfileActivityViewModel
import com.stathis.seriesmania.ui.profile.navigator.ProfileAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadImageFragment :
    BaseFragment<FragmentUploadImageBinding>(R.layout.fragment_upload_image) {

    private val viewModel: UploadImageViewModel by viewModels()
    private val activityViewModel: ProfileActivityViewModel by activityViewModels()

    private val cameraIntent = onSuccessCameraResult { bitmap ->
        bitmap?.let {
            viewModel.saveUserPhoto(it)
        }
    }

    private val galleryIntent = onSuccessResult { result ->
        result.data?.data?.let {
            viewModel.saveUserPhoto(it.toBitmap(requireContext()))
        }
    }

    override fun init() {
        setScreenTitle(getString(com.stathis.core.R.string.upload_image_title))
        viewModel.getUserImage()

        binding.addImgBtn.setOnClickListener {
            showUploadOptions()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.saveUserImage()
        }
    }

    private fun showUploadOptions() {
        GenericBottomSheet.Builder()
            .setTitle(getString(com.stathis.core.R.string.bs_title))
            .setFirstOption(getString(com.stathis.core.R.string.bs_first_option))
            .setSecondOption(getString(com.stathis.core.R.string.bs_second_option))
            .setListener(object : GenericBottomSheet.GenericBottomSheetListener {
                override fun onFirstOptionClick() {
                    capturePhoto()
                }

                override fun onSecondOptionClick() {
                    uploadFromGallery()
                }
            })
            .build()
            .show(requireActivity().supportFragmentManager, GENERIC_BS_TAG)
    }

    override fun startOps() {
        viewModel.ctaState.observe(viewLifecycleOwner) { isEnabled ->
            binding.saveBtn.isEnabled = isEnabled
        }

        viewModel.userImage.observe(viewLifecycleOwner) { url ->
            binding.userImgView.setImage(url)
        }

        viewModel.bitmap.observe(viewLifecycleOwner) { bitmap ->
            binding.userImgView.setImageBitmap(bitmap)
        }

        viewModel.bitmapSaved.observe(viewLifecycleOwner) { result ->
            when (result) {
                is com.stathis.domain.model.Result.Loading -> {
                    binding.isLoading = true
                }
                is com.stathis.domain.model.Result.Success -> {
                    binding.isLoading = false
                    activityViewModel.navigateToScreen(ProfileAction.PHOTO_UPLOADED)
                }
                is com.stathis.domain.model.Result.Failure -> {
                    binding.isLoading = false
                }
            }
        }
    }

    override fun stopOps() {
        activityViewModel.resetNavigation()
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.launch(intent)
    }

    private fun uploadFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        galleryIntent.launch(intent)
    }
}