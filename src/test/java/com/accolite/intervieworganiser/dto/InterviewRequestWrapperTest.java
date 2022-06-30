package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.Constants;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InterviewRequestWrapperTest {

    User organiser = Constants.recruiter;
    static LocalDate date = Constants.startDate;
    static LocalTime startTime = Constants.startTime;
    static LocalTime endTime = Constants.endTime;
    static List<Integer> interviewerIds = new ArrayList<>();
    static InterviewRequest interviewRequest;
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeAll
    static void init(){
        interviewerIds.add(1);
        interviewRequest = new InterviewRequest(date, startTime,
                endTime, interviewerIds, "pending", "Awaiting Completion");
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testCreationCorrectAttributes() {
        /* valid interview request wrapper object */
        InterviewRequestWrapper validInterviewRequestWrapper = new InterviewRequestWrapper(
                interviewRequest,organiser);

        /* assert attributes are correctly assigned */
        assertEquals(date, validInterviewRequestWrapper.getDate());
        assertEquals(startTime, validInterviewRequestWrapper.getStartTime());
        assertEquals(endTime, validInterviewRequestWrapper.getEndTime());
        assertEquals(interviewerIds, validInterviewRequestWrapper.getInterviewerIds());
        assertEquals(organiser, validInterviewRequestWrapper.getOrganiser());
        assertNull(validInterviewRequestWrapper.getInfo());
    }

    @Test
    void testCreationNullRequestThrowsException(){
        /* assert creation throws null pointer when null interview request */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewRequestWrapper(null, organiser);
        });

        /* assert correct error message */
        String expectedMessage = "InterviewRequest is required";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullOrganiserThrowsException(){
        /* assert creation throws null pointer when null organiser */
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new InterviewRequestWrapper(interviewRequest, null);
        });

        /* assert correct error message */
        String expectedMessage = "Organiser is required";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}
