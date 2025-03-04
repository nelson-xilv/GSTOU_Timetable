package com.nelsonxilv.gstoutimetable.utils

import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import java.util.Locale.getDefault

fun String.formatGroupName(): String {
    return this.trim().uppercase(getDefault())
}

fun List<Lesson>.filterLessonsBySubgroup(subgroupNumber: Int) = this.filter { lesson ->
    lesson.subgroupNumber == 0 || lesson.subgroupNumber == subgroupNumber
}