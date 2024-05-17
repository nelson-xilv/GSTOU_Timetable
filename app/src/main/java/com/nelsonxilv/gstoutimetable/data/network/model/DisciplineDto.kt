package com.nelsonxilv.gstoutimetable.data.network.model

import com.nelsonxilv.gstoutimetable.data.network.model.teacher.LabTeacherDto
import com.nelsonxilv.gstoutimetable.data.network.model.teacher.LectureTeacherDto
import com.nelsonxilv.gstoutimetable.data.network.model.teacher.PracticeTeacherDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Данный класс содержит в себе название дисциплины, тип и учителей,
 * которые ведут данную дисциплину.
 */
@Serializable
data class DisciplineDto(
    val id: Int?,
    val name: String?,
    @SerialName("lecture_teacher")
    val lectureTeacherDto: LectureTeacherDto?,
    @SerialName("practice_teacher")
    val practiceTeacherDto: PracticeTeacherDto?,
    @SerialName("lab_teacher")
    val labTeacherDto: LabTeacherDto?,
    /**
     * [reportingType] - тип предмета: экзамен или зачет.
     */
    @SerialName("reporting_type")
    val reportingType: String?,
    val semester: String?
)