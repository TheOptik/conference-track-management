package de.theoptik.conferencetrackmanagement.exception

class SessionFormatException(rawSession:String) : RuntimeException("Session '$rawSession' has an invalid format.")