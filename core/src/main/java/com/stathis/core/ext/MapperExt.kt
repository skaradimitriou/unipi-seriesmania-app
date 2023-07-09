package com.stathis.core.ext

import com.google.firebase.firestore.QuerySnapshot
import retrofit2.Response

fun String?.toNotNull() = this ?: ""

fun Int?.toNotNull() = this ?: 0

fun Double?.toNotNull() = this ?: 0.0

fun Boolean?.toNotNull() = this ?: false

fun <T> List<T>?.toNotNull() = this ?: listOf()

fun <DtoModel, DomainModel> List<DtoModel>?.toListOf(
    performMapping: (DtoModel) -> DomainModel
): List<DomainModel> = this?.map { performMapping.invoke(it) } ?: listOf()

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
    return try {
        val result = call.invoke()
        return if (result.isSuccessful) {
            mapper.invoke(result.body())
        } else {
            mapper.invoke(null)
        }
    } catch (e: Exception) {
        mapper.invoke(null)
    }
}

/**
 * Helper fun to transform firestore results to a list of a certain object.
 *
 * Usage:
 *
 * val documents = firestore.collection("XXX").get().await()
 * val list = documents.toListOf<Foo>()
 *
 * list is now either a list of foo objects or an empty list
 */
suspend inline fun <reified T> QuerySnapshot.toListOf(): List<T> = try {
    val list = mutableListOf<T>()
    documents.forEach { document ->
        val model = document.toObject(T::class.java)
        model?.let { list.add(it) }
    }
    list
} catch (e: Exception) {
    listOf()
}