package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.ext.toListOf
import com.stathis.data.mappers.AnalyticsMapper
import com.stathis.data.mappers.FaqMapper
import com.stathis.data.model.AnalyticsResponseDto
import com.stathis.data.model.FaqDto
import com.stathis.data.util.ANALYTICS_CHILD_PATH
import com.stathis.data.util.ANALYTICS_DB_PATH
import com.stathis.data.util.FAQ_DB_PATH
import com.stathis.domain.model.analytics.AnalyticsResponse
import com.stathis.domain.model.faq.Faq
import com.stathis.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SettingsRepository {

    override suspend fun getApplicationAnalytics(): Flow<AnalyticsResponse> = flow {
        val result = firestore.collection(ANALYTICS_DB_PATH)
            .document(ANALYTICS_CHILD_PATH)
            .get()
            .await()
            .toObject(AnalyticsResponseDto::class.java)

        val data = AnalyticsMapper.toDomainModel(result)
        emit(data)
    }

    override suspend fun getFaqs(): Flow<List<Faq>> = flow {
        val result = firestore.collection(FAQ_DB_PATH)
            .get()
            .await()
            .toListOf<FaqDto>()

        val data = FaqMapper.toDomainModel(result)
        emit(data.sortedBy { it.seq })
    }
}