package com.stathis.core.ext

fun String?.toNotNull() = toString()

fun Int?.toNotNull() = this ?: 0

fun Double?.toNotNull() = this ?: 0.0

fun <T> List<T>?.toNotNull() = this ?: listOf()