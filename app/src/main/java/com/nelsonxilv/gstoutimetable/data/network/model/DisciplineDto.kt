package com.nelsonxilv.gstoutimetable.data.network.model

import com.nelsonxilv.gstoutimetable.data.network.model.teacher.LabTeacherDto
import com.nelsonxilv.gstoutimetable.data.network.model.teacher.LectureTeacherDto
import com.nelsonxilv.gstoutimetable.data.network.model.teacher.PracticeTeacherDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a discipline.
 *
 * This class encapsulates information about a specific discipline, including its name,
 * and the teachers responsible for different types of instruction: lectures, practical sessions, and labs.
 * Each teacher type is represented by a dedicated DTO (LectureTeacherDto, PracticeTeacherDto, LabTeacherDto).
 *
 * @property name The name of the discipline. Can be null if the discipline has no assigned name.
 * @property lectureTeacherDto Information about the teacher responsible for the lectures in this discipline. Can be null if there's no assigned lecture teacher.
 * @property practiceTeacherDto Information about the teacher responsible for the practical sessions in this discipline. Can be null if there's no assigned practice teacher.
 * @property labTeacherDto Information about the teacher responsible for the lab sessions in this discipline. Can be null if there's no assigned lab teacher.
 */
@Serializable
data class DisciplineDto(
    @SerialName("name") val name: String?,
    @SerialName("lecture_teacher") val lectureTeacherDto: LectureTeacherDto?,
    @SerialName("practice_teacher") val practiceTeacherDto: PracticeTeacherDto?,
    @SerialName("lab_teacher") val labTeacherDto: LabTeacherDto?,
)