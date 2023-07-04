package com.stathis.domain.usecases.settings

import com.stathis.domain.repositories.SettingsRepository
import com.stathis.domain.model.analytics.AnalyticsResponse
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAppAnalyticsUseCase @Inject constructor(
    private val repo: SettingsRepository
) : BaseUseCase<Flow<AnalyticsResponse>> {

    override suspend fun invoke(vararg args: Any?) = repo.getApplicationAnalytics()
}