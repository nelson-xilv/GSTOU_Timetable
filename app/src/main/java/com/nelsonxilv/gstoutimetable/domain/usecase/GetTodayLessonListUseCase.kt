package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.DateProvider
import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodayLessonListUseCase @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val dateProvider: DateProvider
) {
    suspend operator fun invoke(groupName: String, subgroupNumber: Int): Flow<List<Lesson>> {
        val dayOfWeek = dateProvider.getDayOfWeekNumber()
        val currentWeekNumber = dateProvider.getCurrentWeekNumber()

        return timetableRepository.getLessonList(groupName).map { lessons ->
            lessons.filterLessons(subgroupNumber, dayOfWeek, currentWeekNumber)
        }
    }

    private fun List<Lesson>.filterLessons(
        selectedSubgroupNumber: Int,
        dayOfWeek: Int? = null,
        currentWeekNumber: Int? = null
    ): List<Lesson> {
        return this.filter { lesson ->
            (dayOfWeek == null || lesson.dayOfWeek == dayOfWeek) &&
                    (currentWeekNumber == null || lesson.week == currentWeekNumber
                            || lesson.week == NO_WEEK) &&
                    (lesson.subgroupNumber == NO_SUBGROUP
                            || lesson.subgroupNumber == selectedSubgroupNumber)
        }.sortedBy { lesson ->
            lesson.period
        }
    }

    companion object {
        private const val NO_SUBGROUP = 0
        private const val NO_WEEK = 0
    }
}