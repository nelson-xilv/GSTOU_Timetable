package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson

data class GroupWithLessons(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "groupName",
        entityColumn = "lessonId",
        associateBy = Junction(GroupLessonCrossRef::class)
    )
    val lessons: List<Lesson>
)