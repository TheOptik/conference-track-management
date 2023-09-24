package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime
import java.time.temporal.ChronoUnit.MINUTES

private val NINE = LocalTime.of(9, 0)
private val TWELVE = LocalTime.of(12, 0)
private val ONE = LocalTime.of(13, 0)
private val FOUR = LocalTime.of(16, 0)
private val FIVE = LocalTime.of(17, 0)

internal class TimeTableComposerTest {

    @Test
    fun sessionsStartRightAfterThePreviousSession() {
        val tracks = listOf(Track(listOf(Session("first session", 60), Session("second session", 45)), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).hasSize(1)
        val entries = result[0].entries
        assertThat(entries).hasSize(2)

        val firstSession = entries[0]
        val secondSession = entries[1]

        assertThat(
            MINUTES.between(secondSession.startTime, firstSession.startTime)
        ).isEqualTo(firstSession.session.lengthInMinutes)
    }

    @Test
    fun morningSessionsStartAt9Am() {
        val tracks = listOf(Track(listOf(Session("first session", 60)), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).hasSize(1)
        val entries = result[0].entries
        assertThat(entries).hasSize(1)

        val firstSession = entries[0]
        assertThat(firstSession.startTime).isEqualTo(NINE)
    }

    @Test
    fun afternoonSessionsStartAt1Pm() {
        val tracks = listOf(Track(listOf(), listOf(Session("first session", 60))))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).hasSize(1)
        val entries = result[0].entries
        assertThat(entries).hasSize(1)

        val firstSession = entries[0]
        assertThat(firstSession.startTime).isEqualTo(ONE)
    }

    @Test
    fun lunchStartsAt12Pm() {
        val tracks = listOf(Track(listOf(), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).allSatisfy {
            assertThat(it.entries.find { it.startTime == TWELVE }).isNotNull()
                .extracting { it?.session?.title }.isEqualTo(LUNCH_SESSION_NAME)
        }
    }

    @Test
    fun networkingEventStartsBetween4PmAnd5Pm() {
        val tracks = listOf(Track(listOf(), listOf()))

        val composer = TimeTableComposer()
        val result = composer.composeTimeTable(tracks)

        assertThat(result).allSatisfy {
            assertThat(it.entries.find { !it.startTime.isBefore(FOUR) && !it.startTime.isAfter(FIVE) }).isNotNull()
                .extracting { it?.session?.title }.isEqualTo(NETWORKING_EVENT_SESSION_NAME)
        }
    }
}