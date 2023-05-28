package com.stathis.core.ext

import retrofit2.Response

fun String?.toNotNull() = this ?: ""

fun Int?.toNotNull() = this ?: 0

fun Double?.toNotNull() = this ?: 0.0

fun Boolean?.toNotNull() = this ?: false

fun <T> List<T>?.toNotNull() = this ?: listOf()

/**
 * Helper fun to reduce boiletplate code inside repository impl classes.
 *
 * [DataModel] : The model that the api returns
 * [DomainModel] : The model that is used inside the app after the mapping.
 */

suspend fun <DataModel, DomainModel> getAndMapResponse(
    call: suspend () -> Response<DataModel?>,
    mapper: suspend (DataModel?) -> DomainModel
): DomainModel {
    val result = call.invoke()
    return if (result.isSuccessful) {
        mapper.invoke(result.body())
    } else {
        mapper.invoke(null)
    }
}