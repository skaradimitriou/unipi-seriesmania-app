package com.stathis.core.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.R
import com.stathis.core.adapters.general.AiringTodaySeriesAdapter
import com.stathis.core.adapters.general.SeriesCallback
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.*
import com.stathis.core.util.decorations.HorizontalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import com.stathis.domain.model.profile.uimodel.EmptyUserPreferences
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist
import com.stathis.domain.model.profile.uimodel.LogoutOption
import com.stathis.domain.model.profile.uimodel.UserStatistics

class UserProfileAdapter(
    private val callback: UserProfileCallback
) : ListAdapter<UiModel, UserProfileViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_user_item -> {
                HolderUserItemBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_user_statistics_item -> {
                HolderUserStatisticsItemBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_empty_prefs -> {
                HolderEmptyPrefsBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_empty_watchlist -> {
                HolderEmptyWatchlistBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_series_wrapper_item -> {
                HolderSeriesWrapperItemBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_logout_option -> {
                HolderLogoutOptionBinding.inflate(inflater, parent, false)
            }
            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return UserProfileViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is User -> R.layout.holder_user_item
        is UserStatistics -> R.layout.holder_user_statistics_item
        is EmptyUserPreferences -> R.layout.holder_empty_prefs
        is EmptyWatchlist -> R.layout.holder_empty_watchlist
        is TvSeriesWrapper -> R.layout.holder_series_wrapper_item
        is LogoutOption -> R.layout.holder_logout_option
        else -> R.layout.holder_empty_view
    }
}

class UserProfileViewHolder(
    private val binding: ViewDataBinding,
    private val callback: UserProfileCallback
) : BaseViewHolder(binding), SeriesCallback {

    override fun present(data: UiModel) {
        when (data) {
            is User -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
            is UserStatistics -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }

            is EmptyUserPreferences -> {
                binding.setVariable(BR.callback, callback)
            }

            is TvSeriesWrapper -> {
                val adapter = AiringTodaySeriesAdapter(this)
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.series)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }

            is LogoutOption -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }
            else -> Unit
        }
    }

    override fun onSeriesClick(model: TvSeries) {
        callback.onSeriesClick(model)
    }
}

interface UserProfileCallback {
    fun onImageClick()
    fun onEditInfoClick()
    fun onFollowClick(model: User)
    fun onMyFollowingUsersClick()
    fun onMyFollowsClick()
    fun onWatchlistClick()
    fun onPreferencesClick()
    fun onSeriesClick(model: TvSeries)
    fun onLogoutClick()
}