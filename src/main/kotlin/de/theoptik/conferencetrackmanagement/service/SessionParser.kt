package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.exception.SessionFormatException
import de.theoptik.conferencetrackmanagement.model.Session
import org.springframework.stereotype.Service

private val MINUTES = "min"
private val LIGHTNING = "lightning"
private val SESSION_PATTERN = Regex("([^0-9]+)([0-9]+$MINUTES|$LIGHTNING)");

@Service
class SessionParser {
    fun parseSession(rawSession: String): Session {
        val matchResult = SESSION_PATTERN.matchEntire(rawSession) ?: throw SessionFormatException(rawSession)

        val title = matchResult.groupValues[1].trim()
        val length = matchResult.groupValues[2]

        if (length == LIGHTNING) {
            return Session(title, 5)
        }

        return Session(title, length.dropLast(MINUTES.length).toInt())
    }

}