package com.nelsonxilv.gstoutimetable.domain

import com.nelsonxilv.gstoutimetable.domain.TimetableRepository

class GetScheduleUseCase(
    private val repository: TimetableRepository
) {

    operator fun invoke() = repository.getSchedule()
}