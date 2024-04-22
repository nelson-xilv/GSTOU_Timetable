package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Данный класс содержит в себе название дисциплины, тип и учителей,
 * которые ведут данную дисциплину.
 */
@Serializable
data class Discipline(
    val id: Int,
    val name: String,
    @SerialName("lecture_teacher")
    val lectureTeacher: LectureTeacher?,
    @SerialName("practice_teacher")
    val practiceTeacher: PracticeTeacher?,
    @SerialName("lab_teacher")
    val labTeacher: LabTeacher?,
    /**
     * [reportingType] - тип предмета: экзамен или зачет.
     */
    @SerialName("reporting_type")
    val reportingType: String,
    val semester: String
)