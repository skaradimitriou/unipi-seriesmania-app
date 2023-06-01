package com.stathis.data.mappers

interface BaseMapper<T, R> {

    suspend fun toDomainModel(dto: T): R
}