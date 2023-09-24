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
        println("Welcome to the conference track manager!")
        println("Please input the sessions line by line, followed by an empty line after the last session.")
        println()

        val sessions = generateSequence { lineProvider.nextLine() }.takeWhile { it.isNotBlank() }
            .mapNotNull {
                try {
                    return@mapNotNull sessionParser.parseSession(it)
                } catch (formatException: SessionFormatException) {
                    println(formatException.message)
                    println("Please provide the session in the following format: <session name> <session duration>")
                    println("Session titles may not contain numbers, e.g: My Cool Session")
                    println("Session duration may either be denoted in minutes or must be 'lightning', e.g: 45min.")
                    println()
                    return@mapNotNull null
                }
            }.toList()

        val tracks = trackComposer.composeTracks(sessions)

        timeTableComposer.composeTimeTable(tracks)

            .forEach {
                lineWriter.println(it.toString())
            }
    }
}