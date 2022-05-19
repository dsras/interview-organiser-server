package com.accolite.intervieworganiser.dto;

import org.junit.jupiter.api.Test;
import utility.Constants;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AvailabilityRequestTest {

    LocalDate startDate = Constants.startDate, endDate = Constants.endDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;

    @Test
    void testCreationCorrectAttributes(){
        /* valid availability request */
        AvailabilityRequest validAvailabilityRequest = new AvailabilityRequest(startDate, endDate, startTime, endTime);
        /* assert attributes are correctly assigned */
        assertEquals(startDate, validAvailabilityRequest.getStartDate());
        assertEquals(endDate, validAvailabilityRequest.getEndDate());
        assertEquals(startTime, validAvailabilityRequest.getStartTime());
        assertEquals(endTime, validAvailabilityRequest.getEndTime());
    }

    @Test
    void testCreationNullStartDate() {
        /* assert creation throws null pointer when start date is null */
        Exception exception = assertThrows(NullPointerException.class,
                () -> new AvailabilityRequest(null, endDate, startTime, endTime));
        /* assert correct error message */
        String expectedMessage = "start date must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullEndDate() {
        /* assert creation throws null pointer when end date is null */
        Exception exception = assertThrows(NullPointerException.class, () ->
            new AvailabilityRequest(startDate, null, startTime, endTime));
        /* assert correct error message */
        String expectedMessage = "end date must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullStartTime() {
        /* assert creation throws null pointer when start time is null */
        Exception exception = assertThrows(NullPointerException.class, () ->
            new AvailabilityRequest(startDate, endDate, null, endTime));
        /* assert correct error message */
        String expectedMessage = "start time must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullEndTime() {
        /* assert creation throws null pointer when end time is null */
        Exception exception = assertThrows(NullPointerException.class, () ->
            new AvailabilityRequest(startDate, endDate, startTime, null));
        /* assert correct error message */
        String expectedMessage = "end time must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}
