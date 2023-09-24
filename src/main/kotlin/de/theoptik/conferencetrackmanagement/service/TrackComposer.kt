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
        return composeTracks(emptyList(), sessions)
    }

    private fun composeTracks(tracks: List<Track>, sessionPool: List<Session>): List<Track> {
        if (sessionPool.isEmpty()) {
            return tracks
        }
        val morningSessions =
            selectSessionsFromPool(emptyList(), sessionPool, MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
        val afternoonSessions = selectSessionsFromPool(
            emptyList(), sessionPool - morningSessions, AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES
        )
        return composeTracks(
            tracks + Track(morningSessions, afternoonSessions), (sessionPool - morningSessions) - afternoonSessions
        )
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