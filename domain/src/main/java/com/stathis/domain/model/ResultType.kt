package com.stathis.domain.model

import java.io.Serializable

enum class ResultType : Serializable {
    TOP_RATED,
    TRENDING,
    AIRING_TODAY,
    SPECIFIC_GENRE
}