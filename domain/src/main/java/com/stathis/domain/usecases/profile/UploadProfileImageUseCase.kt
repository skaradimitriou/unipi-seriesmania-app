package com.stathis.domain.usecases.profile

import android.graphics.Bitmap
import com.stathis.domain.repositories.ProfileRepository
import com.stathis.domain.usecases.BaseUseCase
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repo: ProfileRepository
) : BaseUseCase<Boolean> {

    override suspend fun invoke(vararg args: Any?): Boolean {
        val bitmap = args.getOrNull(0) as Bitmap
        return repo.uploadProfileImage(bitmap)
    }
}