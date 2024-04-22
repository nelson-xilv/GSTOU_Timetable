package com.nelsonxilv.gstoutimetable.data.network

import com.nelsonxilv.gstoutimetable.data.entity.Lesson
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://backend-isu.gstou.ru/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface TimetableApiService {

    @GET("api/timetable/public/entrie/?group=Ист-23м")
    suspend fun getSchedule(): List<Lesson>
}

object TimetableApi {

    val retrofitService: TimetableApiService by lazy {
        retrofit.create(TimetableApiService::class.java)
    }
}