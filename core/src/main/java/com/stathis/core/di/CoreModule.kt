package com.stathis.core.di

import com.google.firebase.auth.FirebaseAuth
import com.stathis.core.util.auth.Authenticator
import com.stathis.core.util.auth.AuthenticatorImpl
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
    fun provideAuthenticator(auth: FirebaseAuth): Authenticator = AuthenticatorImpl(auth)
}