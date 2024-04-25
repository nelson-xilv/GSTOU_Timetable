package com.nelsonxilv.gstoutimetable.data.network

import com.nelsonxilv.gstoutimetable.data.entity.Lesson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://backend-isu.gstou.ru/"

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface TimetableApiService {

    @GET("api/timetable/public/entrie/?group=ист-23-1э")
    suspend fun getSchedule(): List<Lesson>
}

object TimetableApi {

    val retrofitService: TimetableApiService by lazy {
        retrofit.create(TimetableApiService::class.java)
    }
}