package com.nelsonxilv.gstoutimetable.data

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://backend-isu.gstou.ru/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TimetableApiService {

    @GET("api/timetable/public/entrie/?group=Ист-23м")
    suspend fun getSchedule(): String
}

object TimetableApi {

    val retrofitService: TimetableApiService by lazy {
        retrofit.create(TimetableApiService::class.java)
    }
}