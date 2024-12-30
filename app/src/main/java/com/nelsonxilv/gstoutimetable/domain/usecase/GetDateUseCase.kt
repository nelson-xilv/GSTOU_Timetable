package com.nelsonxilv.gstoutimetable.domain.usecase

import com.nelsonxilv.gstoutimetable.domain.DateProvider
import com.nelsonxilv.gstoutimetable.domain.DateType
import com.nelsonxilv.gstoutimetable.domain.entity.DateInfo
import javax.inject.Inject

class GetDateUseCase @Inject constructor(
    private val dateProvider: DateProvider
) {
    operator fun invoke(dateType: DateType): DateInfo {
        val formattedDate = dateProvider.getFormattedDate(dateType)
        val weekNumber = dateProvider.getWeekNumber(dateType)

        return DateInfo(formattedDate, weekNumber)
    }
}