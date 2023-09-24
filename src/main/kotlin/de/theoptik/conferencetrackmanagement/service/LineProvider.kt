package de.theoptik.conferencetrackmanagement.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class LineProvider(private val scanner: Scanner = Scanner(System.`in`)) {
    fun nextLine(): String {
        return scanner.nextLine()
    }
}