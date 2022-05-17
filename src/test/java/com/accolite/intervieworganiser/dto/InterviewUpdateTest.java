package com.accolite.intervieworganiser.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterviewUpdateTest {

    String update = "update";
    Integer interviewId = 1;
    static ValidatorFactory validatorFactory;
    static Validator validator;

    @BeforeAll
    static void init(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testCreationCorrectAttributes() {
        /* valid interview update object */
        InterviewUpdate validInterviewUpdate = new InterviewUpdate(interviewId, update);

        /* assert attributes are correctly assigned */
        assertEquals(interviewId, validInterviewUpdate.getInterviewId());
        assertEquals(update, validInterviewUpdate.getUpdate());
    }

    @Test
    void testCreationNullInterviewIdIsViolation() {
        /* interview update with null interview ID */
        InterviewUpdate interviewUpdateNullInterviewId = new InterviewUpdate(null, update);

        /* assert 1 violation (null interview ID) */
        Set<ConstraintViolation<InterviewUpdate>> violations = validator.validate(interviewUpdateNullInterviewId);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide interview ID", violations.iterator().next().getMessage());
    }

    @Test
    void testCreationNullUpdateIsViolation() {
        /* interview update with null update */
        InterviewUpdate interviewUpdateNullUpdate = new InterviewUpdate(interviewId, null);

        /* assert 1 violation (null updated message) */
        Set<ConstraintViolation<InterviewUpdate>> violations = validator.validate(interviewUpdateNullUpdate);
        System.err.println("VIOLATION: "+violations);
        assertEquals(1,violations.size());
        assertEquals("Please provide update", violations.iterator().next().getMessage());
    }
}
