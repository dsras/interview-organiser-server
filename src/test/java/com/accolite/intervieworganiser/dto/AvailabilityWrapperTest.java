package com.accolite.intervieworganiser.dto;

import com.accolite.intervieworganiser.entities.User;
import org.junit.jupiter.api.Test;
import utility.Constants;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AvailabilityWrapperTest {

    LocalDate startDate = Constants.startDate, endDate = Constants.endDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;
    User interviewer = Constants.interviewer;

    @Test
    void testCreationCorrectAttributes(){
        /* valid availability request */
        AvailabilityRequest validAvailabilityRequest = new AvailabilityRequest(startDate, endDate, startTime, endTime);
        /* assert availability wrapper attributes are correctly assigned */
        AvailabilityWrapper validAvailabilityWrapper = new AvailabilityWrapper(validAvailabilityRequest, interviewer);
        assertEquals(startDate, validAvailabilityWrapper.getStartDate());
        assertEquals(endDate, validAvailabilityWrapper.getEndDate());
        assertEquals(startTime, validAvailabilityWrapper.getStartTime());
        assertEquals(endTime, validAvailabilityWrapper.getEndTime());
        assertEquals(interviewer, validAvailabilityWrapper.getInterviewer());
    }

    @Test
    void testCreationNullAvailability() {
        /* assert creation throws null pointer when availability request is null */
        Exception exception = assertThrows(NullPointerException.class,
                () -> new AvailabilityWrapper(null, interviewer));
        /* assert correct error message */
        String expectedMessage = "availability request must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreationNullInterviewer() {
        /* valid availability request */
        AvailabilityRequest validAvailabilityRequest = new AvailabilityRequest(startDate, endDate, startTime, endTime);
        /* assert creation throws null pointer when interviewer is null */
        Exception exception = assertThrows(NullPointerException.class,
                () -> new AvailabilityWrapper(validAvailabilityRequest, null));
        /* assert correct error message */
        String expectedMessage = "interviewer must not be null";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
