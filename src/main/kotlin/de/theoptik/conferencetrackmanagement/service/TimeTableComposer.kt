package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.TimeTableEntry
import de.theoptik.conferencetrackmanagement.model.TimeTableTrack
import de.theoptik.conferencetrackmanagement.model.Track
import java.time.LocalTime


val NINE = LocalTime.of(9, 0)
val TWELVE = LocalTime.of(12, 0)
val ONE = LocalTime.of(13, 0)
val FOUR = LocalTime.of(16, 0)
val FIVE = LocalTime.of(17, 0)

const val LUNCH_SESSION_NAME = "Lunch"
const val NETWORKING_EVENT_SESSION_NAME = "Networking Event"

class TimeTableComposer {

    fun composeTimeTable(tracks: List<Track>): List<TimeTableTrack> {

        return tracks.mapIndexed { index, track ->
            TimeTableTrack(
                "Track ${index + 1}",
                mapSessions(track.morningSessions, NINE) + TimeTableEntry(TWELVE, LUNCH_SESSION_NAME, 60)
                        + mapSessions(track.afternoonSessions, ONE) + TimeTableEntry(
                    FIVE,
                    NETWORKING_EVENT_SESSION_NAME
                )
            )
        }


    }

    private fun mapSessions(
        sessions: List<Session>, currentSessionTime: LocalTime, accumulator: List<TimeTableEntry> = emptyList()
    ): List<TimeTableEntry> {
        if (sessions.isEmpty()) {
            return accumulator
        }
        val head = sessions[0]
        val tail = sessions.subList(1, sessions.size)

        val newEntry = TimeTableEntry(currentSessionTime, head.title, head.lengthInMinutes)
        return mapSessions(tail, currentSessionTime.plusMinutes(head.lengthInMinutes.toLong()), accumulator + newEntry)
    }

}