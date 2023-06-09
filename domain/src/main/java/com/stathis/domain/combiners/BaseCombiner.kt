package com.stathis.domain.combiners

interface BaseCombiner<T> {

    suspend fun invoke(vararg args: Any?): T
}