package de.theoptik.conferencetrackmanagement.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val TIME_FORMATER = DateTimeFormatter.ofPattern("hh:mma")

data class TimeTableEntry(val startTime: LocalTime, val title: String, val lengthInMinutes: Int? = null) {
    override fun toString(): String {
        return "${startTime.format(TIME_FORMATER)} $title ${formatLength(lengthInMinutes)}"
    }
}

private fun formatLength(length: Int?): String {
    if (length == null) return ""
    if (length == 5) return "lightning"
    return "${length}min"
}