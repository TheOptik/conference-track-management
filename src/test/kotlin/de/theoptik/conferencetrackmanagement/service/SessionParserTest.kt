package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.exception.SessionFormatException
import de.theoptik.conferencetrackmanagement.exception.SessionLengthException
import de.theoptik.conferencetrackmanagement.model.Session
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class SessionParserTest {

    @ParameterizedTest
    @MethodSource("de.theoptik.conferencetrackmanagement.fixtures.SessionFixtures#unparsedAndParsedSessions")
    fun parsesSessionWithTitleAndLengthCorrectly(rawSession: String, parsedSession: Session) {
        val sessionParser = SessionParser()

        val result = sessionParser.parseSession(rawSession)

        assertThat(result).isEqualTo(parsedSession)
    }

    @Test
    fun parsesLightningSessionsCorrectly() {
        val sessionParser = SessionParser()

        val result = sessionParser.parseSession("Rails for Python Developers lightning")

        assertThat(result).isEqualTo(Session("Rails for Python Developers", 5))
    }

    @Test
    fun sessionsWithNumbersInTitlesAreRejected() {
        val sessionParser = SessionParser()

        assertThrows<SessionFormatException> { sessionParser.parseSession("Five(5) minutes in developer heaven 5min") }
    }

    @Test
    fun sessionsWithoutLengthAreRejected() {
        val sessionParser = SessionParser()

        assertThrows<SessionFormatException> { sessionParser.parseSession("This is a session without length") }
    }

    @Test
    fun sessionsWhichAreTooLongAreRejected() {
        val sessionParser = SessionParser()

        assertThrows<SessionLengthException> { sessionParser.parseSession("Listen to my neverending story 120000000min") }
    }

}
