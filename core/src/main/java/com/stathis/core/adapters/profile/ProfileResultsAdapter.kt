package com.stathis.core.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.R
import com.stathis.core.adapters.general.SeriesCallback
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderEmptyFollowersBinding
import com.stathis.core.databinding.HolderEmptyFollowingBinding
import com.stathis.core.databinding.HolderEmptyViewBinding
import com.stathis.core.databinding.HolderEmptyWatchlistBinding
import com.stathis.core.databinding.HolderSmallUserItemBinding
import com.stathis.core.databinding.HolderTvSeriesItemBinding
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.profile.User
import com.stathis.domain.model.profile.uimodel.EmptyFollowers
import com.stathis.domain.model.profile.uimodel.EmptyFollowing
import com.stathis.domain.model.profile.uimodel.EmptyWatchlist

class ProfileResultsAdapter(
    private val callback: ProfileResultsCallback
) : ListAdapter<UiModel, ProfileResultsViewHolder>(BaseDiffUtil<UiModel>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileResultsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_small_user_item -> {
                HolderSmallUserItemBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_tv_series_item -> {
                HolderTvSeriesItemBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_empty_watchlist -> {
                HolderEmptyWatchlistBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_empty_following -> {
                HolderEmptyFollowingBinding.inflate(inflater, parent, false)
            }

            R.layout.holder_empty_followers -> {
                HolderEmptyFollowersBinding.inflate(inflater, parent, false)
            }

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }
        return ProfileResultsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ProfileResultsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is User -> R.layout.holder_small_user_item
        is TvSeries -> R.layout.holder_tv_series_item
        is EmptyWatchlist -> R.layout.holder_empty_watchlist
        is EmptyFollowing -> R.layout.holder_empty_following
        is EmptyFollowers -> R.layout.holder_empty_followers
        else -> R.layout.holder_empty_view
    }
}

class ProfileResultsViewHolder(
    private val binding: ViewDataBinding,
    private val callback: ProfileResultsCallback
) : BaseViewHolder(binding), SeriesCallback {

    override fun present(data: UiModel) {
        when (data) {
            is User -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, callback)
            }

            is TvSeries -> {
                binding.setVariable(BR.model, data)
                binding.setVariable(BR.callback, this)
            }
        }
    }

    override fun onSeriesClick(model: TvSeries) {
        callback.onSeriesClick(model)
    }
}

interface ProfileResultsCallback {
    fun onUserClick(user: User)
    fun onSeriesClick(series: TvSeries)
}