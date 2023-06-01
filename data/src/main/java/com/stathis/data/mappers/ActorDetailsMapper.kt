package com.stathis.data.mappers

import com.stathis.core.ext.toNotNull
import com.stathis.data.model.ActorDetailsDto
import com.stathis.domain.model.cast.ActorDetails

object ActorDetailsMapper : BaseMapper<ActorDetailsDto?, ActorDetails> {

    override suspend fun toDomainModel(dto: ActorDetailsDto?): ActorDetails = ActorDetails(
        adult = dto?.adult.toNotNull(),
        also_known_as = dto?.also_known_as.toNotNull(),
        biography = dto?.biography.toNotNull(),
        birthday = dto?.birthday.toNotNull(),
        gender = dto?.gender.toNotNull(),
        id = dto?.id.toNotNull(),
        name = dto?.name.toNotNull(),
        place_of_birth = dto?.place_of_birth.toNotNull(),
        popularity = dto?.popularity.toNotNull(),
        profile_path = dto?.profile_path.toNotNull()
    )
}