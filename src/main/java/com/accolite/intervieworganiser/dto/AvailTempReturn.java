package com.accolite.intervieworganiser.dto;


import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailTempReturn {

    public String getAvailability_id();
    public String getUsername();
    public LocalDate getDate();
    public LocalTime getStart();
    public LocalTime getEnd();
    public LocalTime getInterview_start();
    public LocalTime getInterview_end();


}
