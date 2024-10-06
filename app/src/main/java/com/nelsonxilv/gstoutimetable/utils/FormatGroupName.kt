package com.nelsonxilv.gstoutimetable.utils

import java.util.Locale.getDefault

fun formatGroupName(groupName: String): String {
    return groupName.trim().uppercase(getDefault())
}