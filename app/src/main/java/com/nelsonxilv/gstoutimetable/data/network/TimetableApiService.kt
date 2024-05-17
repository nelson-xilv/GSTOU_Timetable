package com.nelsonxilv.gstoutimetable.data.network

import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import retrofit2.http.GET

interface TimetableApiService {

    @GET("api/timetable/public/entrie/?group=ист-23-1э")
    suspend fun getSchedule(): List<LessonDto>
}