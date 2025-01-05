package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(
    private val repository: TimetableRepository
) {
    suspend operator fun invoke() {
        repository.updateData()
    }
}