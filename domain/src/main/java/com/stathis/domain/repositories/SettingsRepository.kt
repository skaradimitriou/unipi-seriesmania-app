package com.stathis.domain.repositories

import com.stathis.domain.model.analytics.AnalyticsResponse
import com.stathis.domain.model.faq.Faq
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getApplicationAnalytics(): Flow<AnalyticsResponse>

    suspend fun getFaqs(): Flow<List<Faq>>
}