package com.accolite.intervieworganiser.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Interview data transfer interface.
 */
public interface Interview {
    public Integer getInterviewId();

    public String getInterviewers();

    public String getOrganiser();

    public LocalDate getDate();

    public LocalTime getStartTime();

    public LocalTime getEndTime();

    public String getStatus();

    public String getOutcome();

    public String getAdditionalInfo();
}
