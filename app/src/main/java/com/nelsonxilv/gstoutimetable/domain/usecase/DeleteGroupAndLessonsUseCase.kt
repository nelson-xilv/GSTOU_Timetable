package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import javax.inject.Inject

class DeleteGroupAndLessonsUseCase @Inject constructor(
    private val timetableRepository: TimetableRepository
) {
    suspend operator fun invoke(groupName: String) {
        timetableRepository.deleteGroupAndLessons(groupName)
    }
}