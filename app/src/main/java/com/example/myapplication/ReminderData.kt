package com.example.myapplication

import android.location.Geocoder
import java.sql.Timestamp
import java.io.Serializable
import java.sql.Date
import java.sql.Time
import com.google.gson.annotations.SerializedName

data class ReminderData(
    val id: Int?,
    var content: String?,//overview
    var status: Int?,//finished, ongoing or trashcan
    var header: String?,//header
    var date: Date,//or use int
    var time: Time,//or use string
    var catagory: String?,
    val location_long: Float?,
    val location_lat: Float?,
    val priority: String?,
    val repeat: Int?,
    var checked: Boolean?,//if the reminder's check box is checked
    var deleted: Boolean?
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
        "status":1,
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

val movies = """
    [
    {
        "popularity": 94.041,
        "vote_count": 2374,
        "status": 1,
        "poster_path": "/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg",
        "id": 466272,
        "checked": false,
        "backdrop_path": "/kKTPv9LKKs5L3oO1y5FNObxAPWI.jpg",
        "original_language": "en",
        "content": "Once Upon a Time... in Hollywood",
        "genre_ids": [
        35,
        18
        ],
        "header": "Once Upon a Time... in Hollywood",
        "vote_average": 7.6,
        "overview": "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.",
        "release_date": "2019-07-26"
    }
    ]
""".trimIndent()