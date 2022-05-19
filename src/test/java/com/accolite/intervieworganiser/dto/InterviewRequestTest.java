package com.accolite.intervieworganiser.dto;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InterviewRequestTest {

    LocalDate date = Constants.startDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;
    static List<Integer> interviewerIds = new ArrayList<>();
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeAll
    static void init(){
        interviewerIds.add(1);
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testCreationCorrectAttributes() {
        /* valid interview request object */
        InterviewRequest validInterviewRequest = new InterviewRequest(date, startTime, endTime,interviewerIds);

        /* assert attributes are correctly assigned */
        assertEquals(date, validInterviewRequest.getDate());
        assertEquals(startTime, validInterviewRequest.getStartTime());
        assertEquals(endTime, validInterviewRequest.getEndTime());
        assertEquals(interviewerIds, validInterviewRequest.getInterviewerIds());
        assertNull(validInterviewRequest.getInfo());
    }

    @Test
    void testCreationNullDateIsViolation() {
        /* interview request with null date */
        InterviewRequest interviewRequestNullDate = new InterviewRequest(
                null, startTime, endTime, interviewerIds);

        /* assert 1 violation (null date) */
        Set<ConstraintViolation<InterviewRequest>> violations = validator.validate(interviewRequestNullDate);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide an interview date", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullStartTimeIsViolation() {
        /* interview request with null start time */
        InterviewRequest interviewRequestNullStartTime = new InterviewRequest(
                date, null, endTime, interviewerIds);

        /* assert 1 violation (null start time) */
        Set<ConstraintViolation<InterviewRequest>> violations = validator.validate(interviewRequestNullStartTime);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide an interview start time", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullEndTimeIsViolation() {
        /* interview request with null end time */
        InterviewRequest interviewRequestNullEndTime = new InterviewRequest(
                date, startTime, null, interviewerIds);

        /* assert 1 violation (null end time) */
        Set<ConstraintViolation<InterviewRequest>> violations = validator.validate(interviewRequestNullEndTime);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide an interview end time", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullInterviewerIdsIsViolation() {
        /* interview request with null interviewer ids list */
        InterviewRequest interviewRequestNullInterviewerIds= new InterviewRequest(
                date, startTime, endTime, null);

        /* assert 1 violation (null  date) */
        Set<ConstraintViolation<InterviewRequest>> violations = validator.validate(interviewRequestNullInterviewerIds);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide list of interviewer IDs", violations.iterator().next().getMessage());
    }
}
