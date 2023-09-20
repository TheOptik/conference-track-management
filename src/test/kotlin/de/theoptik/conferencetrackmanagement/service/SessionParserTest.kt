package de.theoptik.conferencetrackmanagement.service

import de.theoptik.conferencetrackmanagement.model.Session
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class SessionParserTest{

    @ParameterizedTest
    @MethodSource("sessionsWithTitleAndLength")
    fun parsesSessionWithTitleAndLengthCorrectly(rawSession:String, parsedSession:Session){

        val sessionParser = SessionParser()

        val result = sessionParser.parseSession(rawSession)

        assertThat(result).isEqualTo(parsedSession)
    }


    companion object{
        @JvmStatic
        fun sessionsWithTitleAndLength(): Stream<Arguments>{
            return Stream.of(
                Arguments.of("Writing Fast Tests Against Enterprise Rails 60min", Session("Writing Fast Tests Against Enterprise Rails",60)),
                Arguments.of("Overdoing it in Python 45min", Session("Overdoing it in Python",45)),
                Arguments.of("Lua for the Masses 30min", Session("Lua for the Masses",30)),
                Arguments.of("Ruby Errors from Mismatched Gem Versions 45min", Session("Ruby Errors from Mismatched Gem Versions",45)),
                Arguments.of("Common Ruby Errors 45min", Session("Common Ruby Errors",45)),
                Arguments.of("Communicating Over Distance 60min", Session("Communicating Over Distance",60)),
                Arguments.of("Accounting-Driven Development 45min", Session("Accounting-Driven Development",45)),
            )
        }
    }

}
