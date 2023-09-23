package de.theoptik.conferencetrackmanagement.model

data class Track(val morningSessions: List<Session>, val afternoonSessions: List<Session>) {
    companion object {
        const val MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES = (12 - 9) * 60;
        const val AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES = (17 - 13) * 60;
    }
}