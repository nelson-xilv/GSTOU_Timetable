package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.TypeConverter

class ListConverter {

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(", ").map { it.trim() }
    }

}