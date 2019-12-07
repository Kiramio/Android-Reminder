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

val rememos = """
    [
    {
        "id":1,
        "content":"CIS400 Presentation",
        "status":1,
        "header":"Final Presentation",
        "checked":false,
        "deleted":false
    },
    {
        "id":1,
        "content":"CSGo",
        "status":2,
        "header":"Final",
        "checked":false,
        "deleted":false
    },
    {
        "id":1,
        "content":"BAC",
        "status":1,
        "header":"Final",
        "checked":false,
        "deleted":false
    }
    ]
""".trimIndent()


