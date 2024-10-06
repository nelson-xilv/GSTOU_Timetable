package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.nelsonxilv.gstoutimetable.data.model.GroupDbModel
import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel

data class GroupWithLessons(
    @Embedded val group: GroupDbModel,
    @Relation(
        parentColumn = "groupName",
        entityColumn = "lessonId",
        associateBy = Junction(GroupLessonCrossRef::class)
    )
    val lessons: List<LessonDbModel>
)