package com.stathis.domain.usecases

interface BaseUseCase<T> {

    suspend fun invoke(vararg args: Any?): T
}