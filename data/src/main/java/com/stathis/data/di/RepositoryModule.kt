package com.stathis.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.session.SessionManager
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
        authenticator: Authenticator,
        sessionRepository: SessionRepository
    ): OnboardingRepository = OnboardingRepositoryImpl(authenticator, sessionRepository)

    @Provides
    @Singleton
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        authenticator: Authenticator,
        sessionManager: SessionManager,
        storage: StorageReference
    ): ProfileRepository = ProfileRepositoryImpl(firestore, authenticator, sessionManager, storage)

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
        api: SeriesApi,
        firestore: FirebaseFirestore,
        authenticator: Authenticator,
        sessionRepository: SessionRepository,
        sessionManager: SessionManager
    ): ReviewsRepository = ReviewsRepositoryImpl(
        api,
        firestore,
        authenticator,
        sessionRepository,
        sessionManager
    )

    @Provides
    @Singleton
    fun provideForumRepository(
        firestore: FirebaseFirestore,
        authenticator: Authenticator,
        profileRepository: ProfileRepository
    ): ForumRepository = ForumRepositoryImpl(firestore, authenticator, profileRepository)

    @Provides
    @Singleton
    fun provideWatchlistRepository(
        firestore: FirebaseFirestore,
        authenticator: Authenticator
    ): WatchlistRepository = WatchlistRepositoryImpl(firestore, authenticator)

    @Provides
    @Singleton
    fun provideCommunityRepository(
        firestore: FirebaseFirestore,
        authenticator: Authenticator,
        profileRepository: ProfileRepository
    ): CommunityRepository = CommunityRepositoryImpl(firestore, authenticator, profileRepository)

    @Provides
    @Singleton
    fun provideSessionRepository(
        api: SeriesApi,
        sessionManager: SessionManager
    ): SessionRepository = SessionRepositoryImpl(api, sessionManager)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        firestore: FirebaseFirestore
    ): SettingsRepository = SettingsRepositoryImpl(firestore)
}