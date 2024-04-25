package com.nelsonxilv.gstoutimetable.domain

class GetScheduleUseCase(
    private val repository: TimetableRepository
) {

    operator fun invoke() = repository.getSchedule()
}