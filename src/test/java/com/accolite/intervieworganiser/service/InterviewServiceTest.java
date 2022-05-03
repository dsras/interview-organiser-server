package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @Mock
    InterviewService interviewService;

    @Mock
    InterviewRepository interviewRepository;

    final LocalDate interviewDate = LocalDate.of(2022, 5, 3);
    final LocalTime timeStart = LocalTime.of(10,0);
    final LocalTime timeEnd = LocalTime.of(11,0);
    final String status = "available";
    final String outcome = "passed";

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
        Integer id = newInterview.getId();
        interviewService.updateStatus(status, id);
        assertThat(newInterview.getStatus()).isEqualTo(status);
    }

    //needs adjustment
    @Test
    void testUpdateOutcome(){
        Integer id = newInterview.getId();
        interviewService.updateStatus(outcome, id);
        assertThat(newInterview.getOutcome()).isEqualTo(outcome);
    }

//    @Test
//    void testSaveSkill() {
//        /* return new skill to mock repository layer saving skill */
//        when(interviewRepository.save(any(Interview.class))).thenReturn(newInterview);
//        /* ensure service saved new skill correctly */
//        assertThat(interviewService.save()).isEqualTo();
//    }


}
