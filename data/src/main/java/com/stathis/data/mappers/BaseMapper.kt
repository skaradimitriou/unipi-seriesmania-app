package com.stathis.data.mappers

interface BaseMapper<T, R> {

    fun toDomainModel(dto: T): R
}