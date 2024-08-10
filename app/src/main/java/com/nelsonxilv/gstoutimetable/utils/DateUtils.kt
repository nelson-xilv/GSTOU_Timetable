package com.nelsonxilv.gstoutimetable.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.time.LocalDate
import java.time.Month
import java.time.temporal.WeekFields
import java.util.Locale

private const val LANGUAGE = "ru"
private const val COUNTRY = "RU"
private const val PATTERN_DATE_FORMAT = "d MMMM, EEEE"
private const val FIRST_WEEK = 1
private const val SECOND_WEEK = 2

fun getDayOfWeekNumber(): Int {
    val dayOfWeek = LocalDate.now().dayOfWeek
    val ruLocale = Locale(LANGUAGE, COUNTRY)

    return dayOfWeek.get(WeekFields.of(ruLocale).dayOfWeek())
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(PATTERN_DATE_FORMAT, Locale.getDefault())

    return dateFormat.format(calendar.time)
}

fun getCurrentWeekNumber(date: LocalDate = LocalDate.now()): Int {
    val september16thYear = if (date.monthValue >= Month.SEPTEMBER.value && date.dayOfMonth >= 16) {
        date.year
    } else {
        date.year - 1
    }

    val september16th = LocalDate.of(september16thYear, Month.SEPTEMBER, 16)
    val daysDifference = date.toEpochDay() - september16th.toEpochDay()
    val weekNumber = (daysDifference / 7) + 1

    return if (weekNumber % 2 == 0L) SECOND_WEEK else FIRST_WEEK
}