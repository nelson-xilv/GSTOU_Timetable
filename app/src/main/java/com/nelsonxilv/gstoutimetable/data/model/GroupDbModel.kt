package com.nelsonxilv.gstoutimetable.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupDbModel(
    @PrimaryKey val groupName: String
)