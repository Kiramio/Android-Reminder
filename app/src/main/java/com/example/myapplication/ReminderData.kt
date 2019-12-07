package com.example.myapplication

import android.location.Geocoder
import java.sql.Timestamp
import java.io.Serializable
import java.sql.Date
import java.sql.Time
import com.google.gson.annotations.SerializedName

class ReminderData(
    val uid: String = "",
    val header: String? = "",//header
    val content: String? = "",//overview
    val status: Int? = 0,//finished, ongoing or trashcan
    val date: Long? = 0,//or use int
    var checked: Boolean? = false,//if the reminder's check box is checked
    var deleted: Boolean? = false
) : Serializable
