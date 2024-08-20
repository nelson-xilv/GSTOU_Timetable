package com.nelsonxilv.gstoutimetable.data.mapper.mappers

fun interface Mapper<From, To> {
    fun transform(data: From): To
}