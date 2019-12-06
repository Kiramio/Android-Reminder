package com.example.myapplication

import android.location.Geocoder
import java.sql.Timestamp
import java.io.Serializable

class ReminderData(
    val id: String?,
    val uid: String,
    val title: String?,
    val content: String?,
    val catagory: Int?,
    val time: Timestamp?,
    //val location_long: Float?,
    //val location_lat: Float?,
    val priority: String?
    //val repeat: Int?
) : Serializable