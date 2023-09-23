package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.extensions.totalLengthInMinutes
import de.theoptik.conferencetrackmanagement.fixtures.ALL_SESSIONS
import de.theoptik.conferencetrackmanagement.model.Track
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TrackComposerTest {

    @Test
    fun tracksDoNotExceedTheirMaximumLength() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        assertThat(tracks).isNotEmpty().allSatisfy {
            assertThat(it.morningSessions.totalLengthInMinutes()).isLessThanOrEqualTo(Track.MORNING_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
            assertThat(it.afternoonSessions.totalLengthInMinutes()).isLessThanOrEqualTo(Track.AFTERNOON_SESSIONS_MAXIMUM_LENGTH_IN_MINUTES)
        }
    }

    @Test
    fun allSessionsMustBeContainedWithinTheTracks() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        val allIncludedSessions = tracks.flatMap { it.morningSessions + it.afternoonSessions }
        assertThat(allIncludedSessions).hasSameElementsAs(ALL_SESSIONS)
    }

    @Test
    fun sessionsMayOnlyOccurOnceWithinAllTracks() {
        val composer = TrackComposer()

        val tracks = composer.composeTracks(ALL_SESSIONS)

        val allIncludedSessions = tracks.flatMap { it.morningSessions + it.afternoonSessions }
        assertThat(allIncludedSessions).doesNotHaveDuplicates()
    }
}