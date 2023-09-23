package de.theoptik.conferencetrackmanagement.fixtures

import de.theoptik.conferencetrackmanagement.model.Session
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream


val ALL_SESSIONS = listOf(
    Session("Writing Fast Tests Against Enterprise Rails", 60),
    Session("Overdoing it in Python", 45),
    Session("Lua for the Masses", 30),
    Session("Ruby Errors from Mismatched Gem Versions", 45),
    Session("Common Ruby Errors", 45),
    Session("Rails for Python Developers", 5),
    Session("Communicating Over Distance", 60),
    Session("Accounting-Driven Development", 45)
)

val ALL_SESSIONS_UNPARSED = listOf(
    "Writing Fast Tests Against Enterprise Rails 60min",
    "Overdoing it in Python 45min",
    "Lua for the Masses 30min",
    "Ruby Errors from Mismatched Gem Versions 45min",
    "Common Ruby Errors 45min",
    "Rails for Python Developers lightning",
    "Communicating Over Distance 60min",
    "Accounting-Driven Development 45min"
)

object SessionFixtures {
    @JvmStatic
    fun sessionsWithTitleAndLength(): Stream<Arguments> {
        return ALL_SESSIONS_UNPARSED.zip(ALL_SESSIONS).map { Arguments.of(it.first, it.second) }.stream()
    }

}