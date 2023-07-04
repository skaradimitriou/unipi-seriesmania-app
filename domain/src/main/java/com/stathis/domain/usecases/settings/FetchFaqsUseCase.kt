package com.stathis.domain.usecases.settings

import com.stathis.domain.model.faq.Faq
import com.stathis.domain.repositories.SettingsRepository
import com.stathis.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFaqsUseCase @Inject constructor(
    private val repo: SettingsRepository
) : BaseUseCase<Flow<List<Faq>>> {

    override suspend fun invoke(vararg args: Any?) = repo.getFaqs()
}