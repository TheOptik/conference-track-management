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
        println("Welcome to the conference track manager!")
        println("Please input the sessions line by line, followed by an empty line after the last session.")

        val sessions = generateSequence { lineProvider.nextLine() }.takeWhile { it.isNotBlank() }
            .map { sessionParser.parseSession(it) }.toList()

        val tracks = trackComposer.composeTracks(sessions)

        timeTableComposer.composeTimeTable(tracks)

            .forEach {
                lineWriter.println(it.toString())
            }
    }
}