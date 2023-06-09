package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.mappers.WatchlistMapper
import com.stathis.data.util.WATCHLIST_DB_PATH
import com.stathis.domain.model.TvSeries
import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.repositories.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: Authenticator
) : WatchlistRepository {

    override suspend fun addNewItem(item: TvSeries) {
        val list = getAllItems().first().toMutableList().apply { add(0, item) }
        val data = mapOf("series" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(WATCHLIST_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun removeItem(item: TvSeries) {
        val list = getAllItems().first().toMutableList().apply { remove(item) }
        val data = mapOf("series" to list)
        val uuid = auth.getActiveUserId()
        firestore.collection(WATCHLIST_DB_PATH).document(uuid).set(data).await()
    }

    override suspend fun getAllItems(): Flow<List<TvSeries>> = flow {
        val result = firestore.collection(WATCHLIST_DB_PATH)
            .document(auth.getActiveUserId())
            .get()
            .await()
            .toObject(TvSeriesWrapper::class.java)

        val data = WatchlistMapper.toDomainModel(result)
        emit(data)
    }
}