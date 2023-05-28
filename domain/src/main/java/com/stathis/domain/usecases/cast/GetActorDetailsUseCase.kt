package com.stathis.domain.usecases.cast

import com.stathis.domain.model.cast.ActorDetails
import com.stathis.domain.repositories.CastRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val repo: CastRepository
) : BaseUseCase<ActorDetails> {

    override suspend fun invoke(vararg args: Any?): ActorDetails {
        val personId = args.getOrNull(0) as? Int? ?: 0
        return repo.getPersonDetails(personId)
    }
}