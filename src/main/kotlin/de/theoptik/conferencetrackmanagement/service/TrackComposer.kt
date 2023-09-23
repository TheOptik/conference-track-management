package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.extensions.totalLengthInMinutes
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.springframework.stereotype.Service

@Service
class TrackComposer {

    fun composeTracks(sessions: List<Session>): List<Track> {
        val morningSessions =
            selectSessionsFromPool(listOf(), sessions, Track.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
        val afternoonSessions =
            selectSessionsFromPool(
                listOf(),
                sessions - morningSessions,
                Track.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES
            )

        return listOf(Track(morningSessions, afternoonSessions))
    }


    private fun selectSessionsFromPool(
        selectedSessions: List<Session>, sessionPool: List<Session>, maxSessionsLengthInMinutes: Int
    ): List<Session> {
        if (selectedSessions.totalLengthInMinutes() == maxSessionsLengthInMinutes || sessionPool.isEmpty()) {
            return selectedSessions
        }

        val poolHead = sessionPool.first()
        val poolTail = sessionPool.subList(1, sessionPool.size)

        if (selectedSessions.totalLengthInMinutes() + poolHead.lengthInMinutes > maxSessionsLengthInMinutes) {
            return selectSessionsFromPool(selectedSessions, poolTail, maxSessionsLengthInMinutes)
        }

        return selectSessionsFromPool(selectedSessions + poolHead, poolTail, maxSessionsLengthInMinutes)
    }


}