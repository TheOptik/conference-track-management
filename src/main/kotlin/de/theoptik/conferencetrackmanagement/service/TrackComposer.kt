package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.extensions.totalLengthInMinutes
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import de.theoptik.conferencetrackmanagement.model.Track.Companion.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES
import de.theoptik.conferencetrackmanagement.model.Track.Companion.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES
import org.springframework.stereotype.Service

@Service
class TrackComposer {
    fun composeTracks(sessions: List<Session>): List<Track> {
        return composeTracks(sessionPool = sessions)
    }

    private fun composeTracks(sessionPool: List<Session>, tracks: List<Track> = emptyList()): List<Track> {
        if (sessionPool.isEmpty()) {
            return tracks
        }
        val morningSessions = selectSessionsFromPool(sessionPool, MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
        val afternoonSessions = selectSessionsFromPool(
            sessionPool - morningSessions, AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES,
        )
        return composeTracks(
            (sessionPool - morningSessions) - afternoonSessions, tracks + Track(morningSessions, afternoonSessions)
        )
    }

    private fun selectSessionsFromPool(
        sessionPool: List<Session>, maxSessionsLengthInMinutes: Int, selectedSessions: List<Session> = emptyList()
    ): List<Session> {
        if (selectedSessions.totalLengthInMinutes() == maxSessionsLengthInMinutes || sessionPool.isEmpty()) {
            return selectedSessions
        }

        val poolHead = sessionPool.first()
        val poolTail = sessionPool.subList(1, sessionPool.size)

        if (selectedSessions.totalLengthInMinutes() + poolHead.lengthInMinutes > maxSessionsLengthInMinutes) {
            return selectSessionsFromPool(poolTail, maxSessionsLengthInMinutes, selectedSessions)
        }

        return selectSessionsFromPool(poolTail, maxSessionsLengthInMinutes, selectedSessions + poolHead)
    }
}