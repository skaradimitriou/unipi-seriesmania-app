package com.stathis.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.ext.getAndMapResponse
import com.stathis.core.ext.toListOf
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.session.SessionManager
import com.stathis.data.api.SeriesApi
import com.stathis.data.mappers.RatingResponseMapper
import com.stathis.data.mappers.RatingsMapper
import com.stathis.data.mappers.ReviewsMapper
import com.stathis.data.model.RatingDto
import com.stathis.data.model.UpdateRatingRequest
import com.stathis.data.util.RATINGS_DB_PATH
import com.stathis.domain.model.reviews.Rating
import com.stathis.domain.repositories.ReviewsRepository
import com.stathis.domain.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val api: SeriesApi,
    private val firestore: FirebaseFirestore,
    private val auth: Authenticator,
    private val sessionRepository: SessionRepository,
    private val sessionManager: SessionManager
) : ReviewsRepository {

    override suspend fun setRatingForSeries(rating: Rating): Flow<Boolean> = flow {
        sessionManager.retrieveGuestSessionData().collect { session ->
            if (session.sessionExpired()) {
                sessionRepository.createGuestSession()
            } else {
                getAndMapResponse(
                    call = {
                        api.addRatingForSeries(
                            series_id = rating.seriesId,
                            sessionId = session.guestSessionId,
                            ratingRequest = UpdateRatingRequest(rating.value)
                        )
                    },
                    mapper = { RatingResponseMapper.toDomainModel(it) }
                )

                saveRatingToDb(rating)
                emit(true)
            }
        }
    }

    private suspend fun saveRatingToDb(rating: Rating) {
        firestore.collection(RATINGS_DB_PATH).document().set(rating).await()
    }

    override suspend fun getMyRatings(): Flow<List<Rating>> = flow {
        val result = firestore.collection(RATINGS_DB_PATH)
            .whereEqualTo("userId", auth.getActiveUserId())
            .get()
            .await()
            .toListOf<RatingDto>()

        val data = RatingsMapper.toDomainModel(result)
        emit(data)
    }

    override suspend fun getRatingsBySeriesId(seriesId: Int) = flow {
        val result = firestore.collection(RATINGS_DB_PATH)
            .whereEqualTo("userId", auth.getActiveUserId())
            .whereEqualTo("seriesId", seriesId.toString())
            .get()
            .await()
            .toListOf<RatingDto>()

        val data = RatingsMapper.toDomainModel(result)
        emit(data)
    }

    override suspend fun getReviewsForSeries(seriesId: Int) = getAndMapResponse(
        call = { api.getReviewsForSeries(seriesId) },
        mapper = { ReviewsMapper.toDomainModel(it) }
    )
}