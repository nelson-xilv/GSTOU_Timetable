package com.nelsonxilv.gstoutimetable.data.network

import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TimetableApiService {

    @GET("api/timetable/public/entrie/")
    suspend fun getSchedule(@Query(QUERY_PARAM_GROUP) groupName: String): List<LessonDto>

    companion object {
        private const val QUERY_PARAM_GROUP = "group"
    }
}