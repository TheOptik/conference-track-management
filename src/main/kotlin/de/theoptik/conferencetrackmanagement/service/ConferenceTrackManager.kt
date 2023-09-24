package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.exception.SessionFormatException
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
        lineWriter.println("Welcome to the conference track manager!")
        lineWriter.println("Please input the sessions line by line, followed by an empty line after the last session.")
        lineWriter.println()

        val sessions = generateSequence { lineProvider.nextLine() }.takeWhile { it.isNotBlank() }
            .mapNotNull {
                try {
                    return@mapNotNull sessionParser.parseSession(it)
                } catch (formatException: SessionFormatException) {
                    lineWriter.println(formatException.message)
                    lineWriter.println("Please provide the session in the following format: <session name> <session duration>")
                    lineWriter.println("Session titles may not contain numbers, e.g: My Cool Session")
                    lineWriter.println("Session duration may either be denoted in minutes or must be 'lightning', e.g: 45min.")
                    lineWriter.println()
                    return@mapNotNull null
                }
            }.toList()

        if (sessions.isEmpty()) {
            lineWriter.println("No time table will be generated, because no sessions are provided.")
            return
        }

        val tracks = trackComposer.composeTracks(sessions)

        timeTableComposer.composeTimeTable(tracks)
            .forEach {
                lineWriter.println(it.toString())
            }
    }
}