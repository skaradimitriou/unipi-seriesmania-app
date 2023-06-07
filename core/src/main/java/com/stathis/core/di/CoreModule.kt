package com.stathis.core.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.auth.AuthenticatorImpl
import com.stathis.core.util.session.SessionManager
import com.stathis.core.util.session.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSessionManager(
        app: Application,
        gson: Gson
    ): SessionManager = SessionManagerImpl(app, gson)

    @Provides
    @Singleton
    fun provideAuthenticator(
        auth: FirebaseAuth,
        sessionManager: SessionManager
    ): Authenticator = AuthenticatorImpl(auth, sessionManager)
}