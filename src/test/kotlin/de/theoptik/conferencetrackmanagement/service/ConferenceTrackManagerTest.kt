package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.fixtures.ALL_SESSIONS
import de.theoptik.conferencetrackmanagement.fixtures.ALL_SESSIONS_UNPARSED
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class ConferenceTrackManagerTest {

    @Test
    fun allSessionNamesMustBeContainedInThePrintedConferenceProgram() {
        val mockLineProvider = setupMockLineProvider(ALL_SESSIONS_UNPARSED)
        val mockLineWriter: LineWriter = mock()

        val conferenceTrackManager = ConferenceTrackManager(
            SessionParser(), TrackComposer(), TimeTableComposer(), mockLineProvider, mockLineWriter
        )

        conferenceTrackManager.run()

        ALL_SESSIONS.forEach { verify(mockLineWriter).println(contains(it.title)) }
    }

    @Test
    fun itPrintsHelpfulMessageToTheUserWhenTheSessionInputIsNotValid() {
        val mockLineProvider = setupMockLineProvider(listOf("Invalid Session"))
        val mockLineWriter: LineWriter = mock()

        val conferenceTrackManager = ConferenceTrackManager(
            SessionParser(), TrackComposer(), TimeTableComposer(), mockLineProvider, mockLineWriter
        )

        conferenceTrackManager.run()

        verify(mockLineWriter).println(contains("<session name> <session duration>"))
    }

    @Test
    fun itPrintsHelpfulMessageToTheUserWhenTheSessionExceedsMaximumSessionLength() {
        val mockLineProvider = setupMockLineProvider(listOf("Very long session 600000min"))
        val mockLineWriter: LineWriter = mock()

        val conferenceTrackManager = ConferenceTrackManager(
            SessionParser(), TrackComposer(), TimeTableComposer(), mockLineProvider, mockLineWriter
        )

        conferenceTrackManager.run()

        verify(mockLineWriter).println(contains("exceeds the maximum length"))
    }

    @Test
    fun itPrintsHelpfulMessageToTheUserNoSessionsAreProvided() {
        val mockLineProvider = setupMockLineProvider(listOf(""))
        val mockLineWriter: LineWriter = mock()

        val conferenceTrackManager = ConferenceTrackManager(
            SessionParser(), TrackComposer(), TimeTableComposer(), mockLineProvider, mockLineWriter
        )

        conferenceTrackManager.run()

        verify(mockLineWriter).println(contains("no sessions"))
    }

    fun setupMockLineProvider(lines: List<String>): LineProvider {
        val firstLine = lines[0]
        val followingLines = lines.subList(1, lines.size).toTypedArray()

        val mockLineProvider: LineProvider = mock()
        //ends with an empty line to signal the conference track manager that the input is over
        `when`(mockLineProvider.nextLine()).thenReturn(firstLine, *followingLines, "")
        return mockLineProvider
    }
}