package de.theoptik.conferencetrackmanagement.service

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ConferenceTrackManager(
    private val sessionParser: SessionParser,
    private val trackComposer: TrackComposer,
    private val timeTableComposer: TimeTableComposer,
    private val lineProvider: LineProvider,
    private val lineWriter: LineWriter
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        TODO()
    }
}