package de.theoptik.conferencetrackmanagement.service

import org.springframework.stereotype.Service

@Service
class LineWriter {

    fun println(line: String) {
        kotlin.io.println(line)
    }

}