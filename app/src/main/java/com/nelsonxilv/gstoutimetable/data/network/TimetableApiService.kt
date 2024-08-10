package com.nelsonxilv.gstoutimetable.data.network

import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TimetableApiService {

    @GET(GET_TIMETABLE_VALUE)
    suspend fun getSchedule(@Query(QUERY_PARAM_GROUP) groupName: String): List<LessonDto>

    companion object {
        private const val GET_TIMETABLE_VALUE = "api/timetable/public/entrie/"
        private const val QUERY_PARAM_GROUP = "group"
    }
}