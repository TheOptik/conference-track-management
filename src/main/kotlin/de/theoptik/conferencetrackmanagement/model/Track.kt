package de.theoptik.conferencetrackmanagement.model

import de.theoptik.conferencetrackmanagement.service.FIVE
import de.theoptik.conferencetrackmanagement.service.NINE
import de.theoptik.conferencetrackmanagement.service.ONE
import de.theoptik.conferencetrackmanagement.service.TWELVE
import java.time.temporal.ChronoUnit.MINUTES

data class Track(val morningSessions: List<Session>, val afternoonSessions: List<Session>) {
    companion object {
        val MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES = MINUTES.between(NINE, TWELVE).toInt()
        val AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES = MINUTES.between(ONE, FIVE).toInt()
    }
}