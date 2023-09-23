package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.extensions.totalLengthInMinutes
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.springframework.stereotype.Service

@Service
class TrackComposer {

    fun composeTracks(sessions: List<Session>): List<Track> {
        var pool = sessions
        val tracks = mutableListOf<Track>()

        while (pool.isNotEmpty()) {
            val morningSessions =
                selectSessionsFromPool(listOf(), pool, Track.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
            val afternoonSessions = selectSessionsFromPool(
                listOf(), pool - morningSessions, Track.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES
            )
            pool = (pool - morningSessions) - afternoonSessions
            tracks.add(Track(morningSessions, afternoonSessions))
        }
        
        return tracks
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