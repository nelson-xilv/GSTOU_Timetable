package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonDto(
    val id: Int?,
    /**
     * [activityType] - тип пары:
     * 1 - лекция;
     * 2 - лаба;
     * 3 - практика.
     */
    @SerialName("activity_type")
    val activityType: Int?,
    @SerialName("auditorium")
    val auditoriumDto: AuditoriumDto?,
    @SerialName("discipline")
    val disciplineDto: DisciplineDto?,
    /**
     * [duration] помогает понять сколько клеток занимает пара по вертикали:
     * 1 - пара только на одной определенной неделе [week];
     * 2 - пара на 2-х неделях.
     */
    val duration: Int?,
    /**
     * [groupNumber] отображает для какой подгруппы пройдет данная пара.
     * Всего подгрупп 2, но если это общая пара для всей группы, то значение будет равно null.
     */
    @SerialName("group_number")
    val groupNumber: Int?,
    @SerialName("groups")
    val groupsDto: List<GroupDto>?,
    /**
     * [isSplit] содержит в себе значение о том,
     * разделена ли данная пара для подгрупп
     */
    @SerialName("is_split")
    val isSplit: Boolean?,
    /**
     * [period] говорит о том, в какой последовательности идут пары.
     * Грубо говоря, [period] - порядковый номер пары.
     */
    val period: Int,
    /**
     * [week] говорит о том, на какой неделе будет данная пара.
     * Если значение [duration] 1, то в [week] будет содержаться какое-то значение,
     * если значение [duration] 2, то значение [week] будет равно null.
     */
    val week: Int?,
    /**
     * [weekDay] обозначает день недели по порядку
     */
    @SerialName("week_day")
    val weekDay: Int?
)