package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.fixtures.ALL_SESSIONS
import de.theoptik.conferencetrackmanagement.model.NETWORKING_EVENT_NAME
import de.theoptik.conferencetrackmanagement.model.Session
import de.theoptik.conferencetrackmanagement.model.Track
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TrackComposerTest {

    @Test
    fun tracksDoNotExceedTheirMaximumLength() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        assertThat(tracks).isNotEmpty().allSatisfy {
            assertThat(totalLength(it.morningSessions)).isLessThanOrEqualTo(Track.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
            assertThat(totalLength(it.afternoonSessions)).isLessThanOrEqualTo(Track.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
        }
    }

    @Test
    fun lastAfternoonSessionMustBeNetworkingEvent() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        assertThat(tracks).isNotEmpty().allSatisfy {
            assertThat(it.afternoonSessions.lastOrNull()).isNotNull().extracting { it?.title }
                .isEqualTo(NETWORKING_EVENT_NAME)
        }
    }

    private fun totalLength(sessions: List<Session>) =
        sessions.fold(0) { acc, session -> acc + session.lengthInMinutes }

}