package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLessonListUseCase @Inject constructor(
    private val timetableRepository: TimetableRepository
) {
    suspend operator fun invoke(groupName: String): Flow<List<Lesson>> {
        return timetableRepository.getLessonList(groupName)
    }
}