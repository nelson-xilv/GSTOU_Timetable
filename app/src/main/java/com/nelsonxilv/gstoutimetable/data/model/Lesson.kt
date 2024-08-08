package com.nelsonxilv.gstoutimetable.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey val lessonId: Int,
    val name: String,
    val teacher: String,
    val auditorium: String,
    val groupsNames: List<String>,
    @Embedded val timeInterval: TimeInterval,
    val activityType: String,
    val period: Int,
    val dayOfWeek: Int,
    val week: Int,
    val subgroupNumber: Int
)
