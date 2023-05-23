package com.stathis.domain.usecases.genres

import com.stathis.domain.model.genres.Genre
import com.stathis.domain.repositories.SeriesRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repo: SeriesRepository
) : BaseUseCase<List<Genre>> {

    override suspend fun invoke(vararg args: Any?): List<Genre> = repo.getSeriesGenres()
}