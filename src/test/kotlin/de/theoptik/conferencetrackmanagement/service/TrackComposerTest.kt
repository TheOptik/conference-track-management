package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.fixtures.ALL_SESSIONS
import de.theoptik.conferencetrackmanagement.model.Session
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TrackComposerTest {

    @Test
    fun sessionsDoNotExceedTheirMaximumLength() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        assertThat(tracks).allSatisfy {
            assertThat(totalLength(it.morningSessions)).isLessThanOrEqualTo(12 - 9)
            assertThat(totalLength(it.afternoonSessions)).isLessThanOrEqualTo(17 - 13)
        }
    }

    private fun totalLength(sessions: List<Session>) =
        sessions.fold(0) { acc, session -> acc + session.lengthInMinutes }

}