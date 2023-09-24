package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.extensions.totalLengthInMinutes
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.TimeTableEntry
import de.theoptik.conferencetrackmanagement.model.TimeTableTrack
import de.theoptik.conferencetrackmanagement.model.Track
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.time.temporal.ChronoUnit.MINUTES

val NINE: LocalTime = LocalTime.of(9, 0)
val TWELVE: LocalTime = LocalTime.of(12, 0)
val ONE: LocalTime = LocalTime.of(13, 0)
val FOUR: LocalTime = LocalTime.of(16, 0)
val FIVE: LocalTime = LocalTime.of(17, 0)

const val LUNCH_SESSION_NAME = "Lunch"
const val NETWORKING_EVENT_SESSION_NAME = "Networking Event"

@Service
class TimeTableComposer {
    fun composeTimeTable(tracks: List<Track>): List<TimeTableTrack> {

        val temporaryTimeTable = tracks.mapIndexed { index, track ->
            val morningSessionEntries = mapSessions(track.morningSessions, NINE)
            val lunchSessionEntry = TimeTableEntry(TWELVE, LUNCH_SESSION_NAME)

            val afternoonSessionEntries = mapSessions(track.afternoonSessions, ONE)
            val networkingSessionEntry = TimeTableEntry(
                ONE.plusMinutes(
                    track.afternoonSessions.totalLengthInMinutes().toLong().coerceAtLeast(
                        MINUTES.between(
                            ONE, FOUR
                        )
                    )
                ), NETWORKING_EVENT_SESSION_NAME
            )

            TimeTableTrack(
                "Track ${index + 1}",
                morningSessionEntries + lunchSessionEntry + afternoonSessionEntries + networkingSessionEntry
            )
        }
        return moveAllNetworkingEventsToTheSameStartTime(temporaryTimeTable)
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

    private fun moveAllNetworkingEventsToTheSameStartTime(timeTableTracks: List<TimeTableTrack>): List<TimeTableTrack> {
        val latestNetworkingEventStartTime =
            timeTableTracks.flatMap { it.entries }.map { it.startTime }.maxOrNull() ?: FOUR
        return timeTableTracks.map {
            it.copy(entries = it.entries.map {
                if (it.title != NETWORKING_EVENT_SESSION_NAME) it else it.copy(
                    startTime = latestNetworkingEventStartTime
                )
            })
        }
    }
}