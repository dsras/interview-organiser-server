package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.User;
import utility.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterviewResponseTest {

    LocalDate date = Constants.startDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;
    User organiser = Constants.recruiter, interviewer = Constants.interviewer;
    List<User> interviewers = new ArrayList<>();

    @BeforeEach
    void init() {
        interviewers.add(interviewer);
    }

    @Test
    void testCreationCorrectAttributes() {
        /* valid interview & interview response */
        Interview validInterview = new Interview(
                organiser,
                date,
                startTime,
                endTime,
                " ",
                "Pending",
                "Awaiting Completion"
        );
        validInterview.setId(1);
        InterviewResponse validInterviewResponse = new InterviewResponse(validInterview, interviewers);
        /* assert InterviewResponse attributes are correctly assigned */
        assertEquals(1, validInterviewResponse.getInterviewId());
        assertEquals(organiser.getName(), validInterviewResponse.getOrganiser());
        assertEquals(1, validInterviewResponse.getInterviewers().size());
        assertEquals(interviewer.getName(), validInterviewResponse.getInterviewers().get(0));
        assertEquals(date, validInterviewResponse.getDate());
        assertEquals(startTime, validInterviewResponse.getStartTime());
        assertEquals(endTime, validInterviewResponse.getEndTime());
        assertEquals(" ", validInterviewResponse.getInfo());
        assertNull(validInterviewResponse.getStatus());
        assertNull(validInterviewResponse.getOutcome());

    }

    @Test
    void testCreationNullOrganiserThrowsException() {
        /* interview with null organiser */
        Interview interviewNullOrganiser = new Interview(
                null,
                date,
                startTime,
                endTime,
                " ",
                "Pending",
                "Awaiting Completion"
        );
        /* assert interview response creation throws null pointer */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewResponse(interviewNullOrganiser, interviewers);
        });
        /* assert correct error message */
        String expectedMessage = "organiser must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullDateThrowsException() {
        /* interview with null date */
        Interview interviewNullDate = new Interview(
                organiser,
                null,
                startTime,
                endTime,
                " ",
                "Pending",
                "Awaiting Completion"
        );
        /* assert interview response creation throws null pointer */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewResponse(interviewNullDate, interviewers);
        });
        /* assert correct error message */
        String expectedMessage = "date must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullStartTimeThrowsException() {
        /* interview with null start time */
        Interview interviewNullStartTime = new Interview(
                organiser,
                date,
                null,
                endTime,
                " ",
                "Pending",
                "Awaiting Completion"
        );
        /* assert interview response creation throws null pointer */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewResponse(interviewNullStartTime, interviewers);
        });
        /* assert correct error message */
        String expectedMessage = "start time must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullEndTimeThrowsException() {
        /* interview with null end time */
        Interview interviewNullEndTime = new Interview(
                organiser,
                date,
                startTime,
                null,
                " ",
                "Pending",
                "Awaiting Completion"
        );
        /* assert interview response creation throws null pointer */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewResponse(interviewNullEndTime, interviewers);
        });
        /* assert correct error message */
        String expectedMessage = "end time must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
