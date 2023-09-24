package de.theoptik.conferencetrackmanagement.model

data class TimeTableTrack(val trackName: String, val entries: List<TimeTableEntry>) {
    override fun toString(): String {
        return "${trackName}:${System.lineSeparator()}" + entries.map { it.toString() }
            .joinToString(System.lineSeparator())
    }

}