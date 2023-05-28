package com.stathis.domain.combiners

import com.stathis.domain.model.TvSeriesWrapper
import com.stathis.domain.model.UiModel
import com.stathis.domain.usecases.cast.GetActorDetailsUseCase
import com.stathis.domain.usecases.cast.GetKnownSeriesForActorUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ActorDetailsCombiner @Inject constructor(
    private val detailsUseCase: GetActorDetailsUseCase,
    private val knownSeriesUseCase: GetKnownSeriesForActorUseCase
) : BaseCombiner<List<UiModel>> {

    override suspend fun invoke(vararg args: Any?): List<UiModel> = coroutineScope {
        val actorId = args.getOrNull(0) as? Int ?: 0
        val details = async { detailsUseCase.invoke(actorId) }.await()
        val knownFor = async { knownSeriesUseCase.invoke(actorId) }.await()

        val list = mutableListOf<UiModel>()
        list.add(details)
        list.add(TvSeriesWrapper(knownFor))
        return@coroutineScope list
    }
}