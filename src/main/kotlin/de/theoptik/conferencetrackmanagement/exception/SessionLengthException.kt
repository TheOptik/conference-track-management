package de.theoptik.conferencetrackmanagement.exception

class SessionLengthException(rawSession: String, maxLengthInMinutes: Int) :
    RuntimeException("Session '$rawSession' exceeds the maximum length of ${maxLengthInMinutes}min.")