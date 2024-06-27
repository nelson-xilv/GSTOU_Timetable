package com.nelsonxilv.gstoutimetable.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

private const val LANGUAGE = "ru"
private const val COUNTRY = "RU"
private const val PATTERN_DATE_FORMAT = "dd MMMM, EEEE"
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

fun getCurrentWeekType(): Int {
    val currentDate = Calendar.getInstance()
    val currentYear = currentDate.get(Calendar.YEAR)

    val startOfStudyYear = Calendar.getInstance().apply {
        set(Calendar.YEAR, currentYear)
        set(Calendar.MONTH, Calendar.SEPTEMBER)
        set(Calendar.DAY_OF_MONTH, 16)
    }

    if (currentDate.before(startOfStudyYear)) {
        startOfStudyYear.add(Calendar.YEAR, -1)
    }

    val weeksDifference =
        ((currentDate.timeInMillis - startOfStudyYear.timeInMillis) / (1000 * 60 * 60 * 24 * 7))
            .toInt()

    return if (weeksDifference % 2 == 0) SECOND_WEEK else FIRST_WEEK
}