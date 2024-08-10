package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Entity

@Entity(
    tableName = "lesson_group_cross_ref",
    primaryKeys = ["lessonId", "groupName"]
)
data class GroupLessonCrossRef(
    val groupName: String,
    val lessonId: Int
)
