package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.exception.SessionFormatException
import de.theoptik.conferencetrackmanagement.exception.SessionLengthException
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.springframework.stereotype.Service

private val MINUTES = "min"
private val LIGHTNING = "lightning"
private val SESSION_PATTERN = Regex("([^0-9]+)([0-9]+$MINUTES|$LIGHTNING)")
private val MAXIMUM_SESSION_LENGTH_IN_MINUTES =
    Track.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES.coerceAtLeast(Track.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)

@Service
class SessionParser {
    fun parseSession(rawSession: String): Session {
        val matchResult = SESSION_PATTERN.matchEntire(rawSession) ?: throw SessionFormatException(rawSession)

        val title = matchResult.groupValues[1].trim()
        val length = matchResult.groupValues[2]

        if (length == LIGHTNING) {
            return Session(title, 5)
        }

        val lengthInMinutes = length.dropLast(MINUTES.length).toInt()

        if (lengthInMinutes > MAXIMUM_SESSION_LENGTH_IN_MINUTES) {
            throw SessionLengthException(rawSession, MAXIMUM_SESSION_LENGTH_IN_MINUTES)
        }

        return Session(
            title, lengthInMinutes
        )
    }
}