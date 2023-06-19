package com.stathis.domain.usecases.profile

import com.stathis.domain.model.profile.uimodel.SeriesPreference
import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class SaveSeriesPreferenceUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val preferences = args.getOrNull(0) as? List<SeriesPreference> ?: listOf()
        return repo.saveUserPreferences(preferences)
    }
}