package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson

@Entity(
    tableName = "lesson_group_cross_ref",
    primaryKeys = ["lessonId", "groupName"],
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["lessonId"],
            childColumns = ["lessonId"]
        ),
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupName"],
            childColumns = ["groupName"]
        )
    ],
    indices = [Index("lessonId"), Index("groupName")]
)
data class GroupLessonCrossRef(
    val groupName: String,
    val lessonId: Int
)
