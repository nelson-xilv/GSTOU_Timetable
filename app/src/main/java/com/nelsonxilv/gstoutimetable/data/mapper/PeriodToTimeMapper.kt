package com.nelsonxilv.gstoutimetable.data.mapper

import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import javax.inject.Inject

class PeriodToTimeMapper @Inject constructor() {

    private val periodToTime = mapOf(
        1 to TimeInterval("9:00", "10:20"),
        2 to TimeInterval("10:30", "11:50"),
        3 to TimeInterval("13:00", "14:20"),
        4 to TimeInterval("14:30", "15:50"),
        5 to TimeInterval("16:00", "17:20"),
        6 to TimeInterval("17:30", "18:50"),
        7 to TimeInterval("19:00", "20:20"),
        8 to TimeInterval("20:30", "21:50")
    )

    fun getPeriodTime(period: Int): TimeInterval {
        return periodToTime[period] ?: TimeInterval("00:00", "00:00")
    }
}