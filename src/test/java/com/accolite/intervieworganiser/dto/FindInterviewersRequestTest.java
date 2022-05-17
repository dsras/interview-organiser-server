package com.accolite.intervieworganiser.dto;

import org.junit.jupiter.api.AfterAll;
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


public class FindInterviewersRequestTest {

    LocalDate startDate = Constants.startDate, endDate = Constants.endDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;
    static List<Integer> skillIds = new ArrayList<>();
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeAll
    public static void init() {
        skillIds.add(1);
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    void testCreationCorrectAttributes(){
        /* valid find interviewers request object */
        FindInterviewersRequest validRequest = new FindInterviewersRequest(startDate,endDate,startTime,endTime,skillIds);

        /* assert attributes are correctly assigned */
        assertEquals(startDate, validRequest.getStartDate());
        assertEquals(endDate, validRequest.getEndDate());
        assertEquals(startTime, validRequest.getStartTime());
        assertEquals(endTime, validRequest.getEndTime());
        assertEquals(skillIds, validRequest.getSkills());
    }

    @Test
    void testCreationNullStartDateIsViolation() {
        /* find interviewers request with null start date */
        FindInterviewersRequest requestNullStartDate = new FindInterviewersRequest(
                null, endDate, startTime, endTime, skillIds);

        /* assert 1 violation (start date null) */
        Set<ConstraintViolation<FindInterviewersRequest>> violations = validator.validate(requestNullStartDate);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide a start date", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullEndDateIsViolation() {
        /* find interviewers request with null end date */
        FindInterviewersRequest requestNullEndDate = new FindInterviewersRequest(
                startDate, null, startTime, endTime, skillIds);

        /* assert 1 violation (end date null) */
        Set<ConstraintViolation<FindInterviewersRequest>> violations = validator.validate(requestNullEndDate);
        assertEquals(1,violations.size());
        assertEquals("Please provide an end date", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullStartTimeIsViolation() {
        /* find interviewers request with null start time */
        FindInterviewersRequest requestNullStartTime = new FindInterviewersRequest(
                startDate, endDate, null, endTime, skillIds);

        /* assert 1 violation (start time null) */
        Set<ConstraintViolation<FindInterviewersRequest>> violations = validator.validate(requestNullStartTime);
        assertEquals(1,violations.size());
        assertEquals("Please provide a start time", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullEndTimeIsViolation() {
        /* find interviewers request with null end time */
        FindInterviewersRequest requestNullEndTime= new FindInterviewersRequest(
                startDate, endDate, startTime, null, skillIds);

        /* assert 1 violation (end time null) */
        Set<ConstraintViolation<FindInterviewersRequest>> violations = validator.validate(requestNullEndTime);
        assertEquals(1,violations.size());
        assertEquals("Please provide an end time", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullSkillListIsViolation() {
        /* find interviewers request with null skill list */
        FindInterviewersRequest requestNullSkillList= new FindInterviewersRequest(
                startDate, endDate, startTime, endTime, null);

        /* assert 1 violation (skill list null) */
        Set<ConstraintViolation<FindInterviewersRequest>> violations = validator.validate(requestNullSkillList);
        assertEquals(1,violations.size());
        assertEquals("Please provide a list of required skill IDs", violations.iterator().next().getMessage());
    }
}
