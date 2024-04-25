package com.nelsonxilv.gstoutimetable.domain.entity

data class Discipline(
    val id: Int?,
    val name: String?,
    val lectureTeacher: LectureTeacher?,
    val practiceTeacher: PracticeTeacher?,
    val labTeacher: LabTeacher?,
    val reportingType: String?,
    val semester: String?
)