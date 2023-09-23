package de.theoptik.conferencetrackmanagement.extensions

import de.theoptik.conferencetrackmanagement.model.Session


fun List<Session>.totalLengthInMinutes(): Int {
    return this.fold(0) { acc, session -> acc + session.lengthInMinutes }
}