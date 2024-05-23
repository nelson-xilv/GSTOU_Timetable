package com.nelsonxilv.gstoutimetable.data

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class TimeService {

    fun getDayOfWeekNumber(): Int {
        val dayOfWeek = LocalDate.now().dayOfWeek
        val ruLocale = Locale("ru", "RU")

        return dayOfWeek.get(WeekFields.of(ruLocale).dayOfWeek())
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM, EEEE", Locale.getDefault())

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

        return if (weeksDifference % 2 == 0) 2 else 1
    }
}