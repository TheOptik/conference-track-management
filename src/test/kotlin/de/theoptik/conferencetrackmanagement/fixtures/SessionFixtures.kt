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
    Session("Accounting-Driven Development", 45),
    Session("Woah", 30),
    Session("Sit Down and Write", 30),
    Session("Pair Programming vs Noise", 45),
    Session("Rails Magic", 60),
    Session("Ruby on Rails: Why We Should Move On", 60),
    Session("Clojure Ate Scala (on my project)", 45),
    Session("Programming in the Boondocks of Seattle", 30),
    Session("Ruby vs. Clojure for Back-End Development", 30),
    Session("Ruby on Rails Legacy App Maintenance", 60),
    Session("A World Without HackerNews", 30),
    Session("User Interface CSS in Rails Apps", 30),
)

val ALL_SESSIONS_UNPARSED = listOf(
    "Writing Fast Tests Against Enterprise Rails 60min",
    "Overdoing it in Python 45min",
    "Lua for the Masses 30min",
    "Ruby Errors from Mismatched Gem Versions 45min",
    "Common Ruby Errors 45min",
    "Rails for Python Developers lightning",
    "Communicating Over Distance 60min",
    "Accounting-Driven Development 45min",
    "Woah 30min",
    "Sit Down and Write 30min",
    "Pair Programming vs Noise 45min",
    "Rails Magic 60min",
    "Ruby on Rails: Why We Should Move On 60min",
    "Clojure Ate Scala (on my project) 45min",
    "Programming in the Boondocks of Seattle 30min",
    "Ruby vs. Clojure for Back-End Development 30min",
    "Ruby on Rails Legacy App Maintenance 60min",
    "A World Without HackerNews 30min",
    "User Interface CSS in Rails Apps 30min"
)

object SessionFixtures {
    @JvmStatic
    fun sessionsWithTitleAndLength(): Stream<Arguments> {
        return ALL_SESSIONS_UNPARSED.zip(ALL_SESSIONS).map { Arguments.of(it.first, it.second) }.stream()
    }

}