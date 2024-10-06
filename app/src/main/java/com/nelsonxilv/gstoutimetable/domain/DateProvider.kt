package com.nelsonxilv.gstoutimetable.domain

import java.time.LocalDate

interface DateProvider {

    fun getDayOfWeekNumber(): Int

    fun getCurrentFormattedDate(): String

    fun getCurrentWeekNumber(date: LocalDate = LocalDate.now()): Int

}