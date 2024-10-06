package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.DateProvider
import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import javax.inject.Inject

class GetDateUseCase @Inject constructor(
    private val dateProvider: DateProvider
) {
    operator fun invoke(): DateInfo {
        val currentFormattedDate = dateProvider.getCurrentFormattedDate()
        val currentWeekNumber = dateProvider.getCurrentWeekNumber()

        return DateInfo(currentFormattedDate, currentWeekNumber)
    }
}