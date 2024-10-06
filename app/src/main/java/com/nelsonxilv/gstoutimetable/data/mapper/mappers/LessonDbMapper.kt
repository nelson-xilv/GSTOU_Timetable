package com.nelsonxilv.gstoutimetable.data.mapper.mappers

import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import javax.inject.Inject

class LessonDbMapper @Inject constructor() : Mapper<LessonDbModel, Lesson> {
    override fun transform(data: LessonDbModel) = Lesson(
        lessonId = data.lessonId,
        name = data.name,
        teacher = data.teacher,
        auditorium = data.auditorium,
        groupNames = data.groupNames,
        timeInterval = data.timeInterval,
        activityType = data.activityType,
        period = data.period,
        dayOfWeek = data.dayOfWeek,
        week = data.week,
        subgroupNumber = data.subgroupNumber
    )
}