package de.theoptik.conferencetrackmanagement.model


const val NETWORKING_EVENT_NAME = "Networking Event"

data class Session(val title: String, val lengthInMinutes: Int) {
    companion object {
        fun NETWORKING_EVENT(lengthInMinutes: Int): Session {
            return Session(NETWORKING_EVENT_NAME, lengthInMinutes)
        }
    }
}

