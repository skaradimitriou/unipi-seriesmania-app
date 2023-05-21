package com.stathis.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.stathis.core.util.auth.Authenticator
import com.stathis.data.api.SeriesApi
import com.stathis.data.repository.*
import com.stathis.domain.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        authenticator: Authenticator
    ): OnboardingRepository = OnboardingRepositoryImpl(authenticator)

    @Provides
    @Singleton
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        authenticator: Authenticator
    ): ProfileRepository = ProfileRepositoryImpl(firestore, authenticator)

    @Provides
    @Singleton
    fun provideSeriesRepository(
        api: SeriesApi
    ): SeriesRepository = SeriesRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCastRepository(
        api: SeriesApi
    ): CastRepository = CastRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideReviewsRepository(
        api: SeriesApi
    ): ReviewsRepository = ReviewsRepositoryImpl(api)
}