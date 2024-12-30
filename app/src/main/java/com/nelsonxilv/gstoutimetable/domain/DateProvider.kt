package com.nelsonxilv.gstoutimetable.domain

interface DateProvider {

    fun getDayOfWeekNumber(dateType: DateType): Int

    fun getFormattedDate(dateType: DateType): String

    fun getWeekNumber(dateType: DateType): Int

}