package de.theoptik.conferencetrackmanagement.model

import java.time.LocalTime

data class TimeTableEntry(val startTime: LocalTime, val session: Session)