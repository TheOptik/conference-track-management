package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit.MINUTES


internal class TimeTableComposerTest {

    @Test
    fun sessionsStartRightAfterThePreviousSession() {
        val tracks = listOf(Track(listOf(Session("first session", 60), Session("second session", 45)), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        val firstSession = result[0].entries[0]
        val secondSession = result[0].entries[1]

        assertThat(
            MINUTES.between(firstSession.startTime, secondSession.startTime)
        ).isEqualTo(firstSession.lengthInMinutes?.toLong())
    }

    @Test
    fun morningSessionsStartAt9Am() {
        val tracks = listOf(Track(listOf(Session("first session", 60)), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        val firstSession = result[0].entries[0]
        assertThat(firstSession.startTime).isEqualTo(NINE)
    }

    @Test
    fun afternoonSessionsStartAt1Pm() {
        val tracks = listOf(Track(listOf(), listOf(Session("afternoon session", 60))))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        val firstSession = result[0].entries.first { it.title == "afternoon session" }
        assertThat(firstSession.startTime).isEqualTo(ONE)
    }

    @Test
    fun lunchStartsAt12Pm() {
        val tracks = listOf(Track(listOf(), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).allSatisfy {
            assertThat(it.entries.find { it.startTime == TWELVE }).isNotNull()
                .extracting { it?.title }.isEqualTo(LUNCH_SESSION_NAME)
        }
    }

    @Test
    fun networkingEventStartsBetween4PmAnd5Pm() {
        val tracks = listOf(Track(listOf(), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).allSatisfy {
            assertThat(it.entries.find { !it.startTime.isBefore(FOUR) && !it.startTime.isAfter(FIVE) }).isNotNull()
                .extracting { it?.title }.isEqualTo(NETWORKING_EVENT_SESSION_NAME)
        }
    }
}