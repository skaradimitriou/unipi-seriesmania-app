package com.stathis.core.adapters.analytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.core.BR
import com.stathis.core.R
import com.stathis.core.adapters.analytics.uimodel.AnalyticsGeneralItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsPreferencesItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsSeriesItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsThreadItem
import com.stathis.core.adapters.analytics.uimodel.AnalyticsUsersItem
import com.stathis.core.adapters.general.SeriesCallback
import com.stathis.core.adapters.general.SeriesDetailsAdapter
import com.stathis.core.adapters.users.UsersAdapter
import com.stathis.core.adapters.users.UsersCalback
import com.stathis.core.base.BaseDiffUtil
import com.stathis.core.base.BaseViewHolder
import com.stathis.core.databinding.HolderAnalyticsDetailBinding
import com.stathis.core.databinding.HolderAnalyticsPreferencesBinding
import com.stathis.core.databinding.HolderAnalyticsSeriesBinding
import com.stathis.core.databinding.HolderAnalyticsThreadBinding
import com.stathis.core.databinding.HolderAnalyticsUsersBinding
import com.stathis.core.databinding.HolderEmptyViewBinding
import com.stathis.core.util.decorations.HorizontalItemDecoration
import com.stathis.core.util.decorations.VerticalItemDecoration
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.UiModel
import com.stathis.domain.model.forum.ForumThread
import com.stathis.domain.model.profile.User

class AnalyticsAdapter(
    private val callback: AnalyticsCallback
) : ListAdapter<UiModel, AnalyticsViewHolder>(BaseDiffUtil<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_analytics_detail -> HolderAnalyticsDetailBinding.inflate(
                inflater, parent, false
            )

            R.layout.holder_analytics_series -> HolderAnalyticsSeriesBinding.inflate(
                inflater, parent, false
            )

            R.layout.holder_analytics_users -> HolderAnalyticsUsersBinding.inflate(
                inflater, parent, false
            )

            R.layout.holder_analytics_thread -> HolderAnalyticsThreadBinding.inflate(
                inflater, parent, false
            )

            R.layout.holder_analytics_preferences -> HolderAnalyticsPreferencesBinding.inflate(
                inflater, parent, false
            )

            else -> HolderEmptyViewBinding.inflate(inflater, parent, false)
        }

        return AnalyticsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: AnalyticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is AnalyticsGeneralItem -> R.layout.holder_analytics_detail
        is AnalyticsSeriesItem -> R.layout.holder_analytics_series
        is AnalyticsUsersItem -> R.layout.holder_analytics_users
        is AnalyticsThreadItem -> R.layout.holder_analytics_thread
        is AnalyticsPreferencesItem -> R.layout.holder_analytics_preferences
        else -> R.layout.holder_empty_view
    }
}

class AnalyticsViewHolder(
    private val binding: ViewDataBinding,
    private val callback: AnalyticsCallback
) : BaseViewHolder(binding), SeriesCallback, UsersCalback {

    override fun present(data: UiModel) {
        when (data) {
            is AnalyticsGeneralItem -> {
                binding.setVariable(BR.model, data)
            }

            is AnalyticsSeriesItem -> {
                val adapter = SeriesDetailsAdapter()
                val decor = HorizontalItemDecoration(20)
                adapter.submitList(data.series)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }

            is AnalyticsUsersItem -> {
                val adapter = UsersAdapter(this)
                val decor = VerticalItemDecoration(20)
                adapter.submitList(data.users)
                binding.setVariable(BR.adapter, adapter)
                binding.setVariable(BR.decoration, decor)
            }

            is AnalyticsThreadItem -> {
                binding.setVariable(BR.model, data.thread)
                binding.setVariable(BR.callback, callback)
            }

            is AnalyticsPreferencesItem -> {
                binding.setVariable(BR.model, data.string)
            }
        }
    }

    override fun onSeriesClick(model: TvSeries) {
        callback.onSeriesClick(model)
    }

    override fun onUserClick(user: User) {
        callback.onUserClick(user)
    }
}

interface AnalyticsCallback {
    fun onUserClick(user: User)
    fun onSeriesClick(series: TvSeries)
    fun onThreadClick(thread: ForumThread)
}