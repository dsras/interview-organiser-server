package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.InterviewPanel;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @Mock
    InterviewRepository interviewRepository;

    final LocalDate interviewDate = LocalDate.of(2022, 5, 3);
    final LocalTime timeStart = LocalTime.of(10,0);
    final LocalTime timeEnd = LocalTime.of(11,0);
    final String status = "available";

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User(username, password, email, name, title);
    }

    final User organiser = newUser;

    Interview newInterview;

    @BeforeEach
    void setup() { newInterview = new Interview(organiser,interviewDate, timeStart, timeEnd);}

    //needs adjustment
    @Test
    void testUpdateStatus(){
        newInterview.setStatus(status);
        newInterview = interviewRepository.save(newInterview);
        List<User> interviewers = newInterview.getInterviewPanel().stream()
                .map(InterviewPanel::getInterviewer)
                .collect(Collectors.toList());
    }
}
