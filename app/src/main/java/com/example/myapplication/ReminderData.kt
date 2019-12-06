package com.example.myapplication

import android.location.Geocoder
import java.sql.Timestamp
import java.io.Serializable
import java.sql.Date
import java.sql.Time

class ReminderData(
    val id: Int?,
    val content: String?,//overview
    val status: Int?,//finished, ongoing or trashcan
    val header: String?,//header
    val date: Date,//or use int
    val time: Time,//or use string
    val catagory: String?,
    val location_long: Float?,
    val location_lat: Float?,
    val priority: String?,
    val repeat: Int?,
    val checked: Boolean?//if the reminder's check box is checked
) : Serializable