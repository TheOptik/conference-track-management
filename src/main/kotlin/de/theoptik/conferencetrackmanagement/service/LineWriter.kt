package de.theoptik.conferencetrackmanagement.service

import org.springframework.stereotype.Service

@Service
class LineWriter {
    fun println(line: Any? = null) {
        if (line == null) {
            kotlin.io.println()
            return
        }
        kotlin.io.println(line)
    }
}