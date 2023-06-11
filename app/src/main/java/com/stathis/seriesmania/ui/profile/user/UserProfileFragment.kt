package com.stathis.seriesmania.ui.profile.user

import androidx.fragment.app.viewModels
import com.stathis.core.base.BaseFragment
import com.stathis.seriesmania.R
import com.stathis.seriesmania.databinding.FragmentUserProfileBinding

class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(R.layout.fragment_user_profile) {

    private val viewModel: UserProfileViewModel by viewModels()

    override fun init() {
        //
    }

    override fun startOps() {
        //
    }

    override fun stopOps() {
        //
    }
}