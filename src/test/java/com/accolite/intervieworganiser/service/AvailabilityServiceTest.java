package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.AvailabilityRequest;
import com.accolite.intervieworganiser.dto.AvailabilityRequestWrapper;
import com.accolite.intervieworganiser.dto.InterviewRequest;
import com.accolite.intervieworganiser.dto.InterviewRequestWrapper;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    UserSkillService userSkillService;

    @Mock
    SkillService skillService;

    AvailabilityService availabilityService;

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";
    User user;

    final LocalDate startDate = LocalDate.of(2022, 5, 3);
    final LocalDate endDate = LocalDate.of(2022, 5, 4);
    final LocalTime startTime = LocalTime.of(10,0);
    final LocalTime endTime = LocalTime.of(11,0);

    @BeforeEach
    void initUseCase() {
        /* initialise availability service */
        availabilityService = new AvailabilityService(availabilityRepository, skillService, userSkillService);
        /* new user */
        user = new User(username, password, email, name, title);
    }

    /**
     * Tests saving availabilities when availability request details availability windows over multiple days.
     * <p>Tests availability per day is saved when passed request spanning multiple days.</p>
     */
    @Test
    void testSaveAvailabilitiesOverMultipleDays() {
        /* new availability request containing 2 days of availability */
        AvailabilityRequest availabilityRequest = new AvailabilityRequest(startDate, endDate, startTime, endTime);
        /* new availability request wrapper to include user placing request */
        AvailabilityRequestWrapper availabilityRequestWrapper = new AvailabilityRequestWrapper(availabilityRequest, user);
        /* mock repository saving availability */
        when(availabilityRepository.save(any(UserAvailability.class))).thenAnswer(i -> i.getArgument(0));
        /* save availability request containing 2 days of availability */
        List<UserAvailability> availabilities = availabilityService.save(availabilityRequestWrapper);
        /* ensure services correctly saves availabilities for 2 days */
        assertThat(availabilityService.save(availabilityRequestWrapper).get(0).getAvailableDate()).isEqualTo(startDate);
        assertThat(availabilityService.save(availabilityRequestWrapper).get(1).getAvailableDate()).isEqualTo(endDate);
    }

    /**
     * Tests amending availability with interview at start of availability.
     * <p>Tests amending availability with:
     * interview start time before or on availability start time and
     * interview end time after availability start time and before availability end time.</p>
     */
    @Test
    void testAmendAvailabilityInterviewAtStart(){
        /* new interview request */
        LocalDate interviewDate = startDate;
        LocalTime interviewStartTime = startTime;
        LocalTime interviewEndTime = startTime.plusMinutes(15);
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1); interviewerIds.add(2);
        InterviewRequest interviewRequest = new InterviewRequest(interviewDate, interviewStartTime, interviewEndTime,
                interviewerIds);
        /* amend availability */
        List<UserAvailability> availabilities = mockAddInterview(interviewRequest);
        /* ensure new availability list has existing availability only with correctly amended start time */
        assertThat(availabilities.get(0).getAvailableFrom()).isEqualTo(interviewEndTime);
        assertThat(availabilities.size()).isEqualTo(1);
    }

    /**
     * Tests amending availability with interview at end of availability.
     * <p>Tests amending availability with:
     * interview start time before availability end time and
     * interview end time after availability end time.</p>
     */
    @Test
    void testAmendAvailabilityInterviewAtEnd(){
        /* new interview request */
        LocalDate interviewDate = startDate;
        LocalTime interviewStartTime = endTime.minusMinutes(15);
        LocalTime interviewEndTime = endTime;
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1); interviewerIds.add(2);
        InterviewRequest interviewRequest = new InterviewRequest(interviewDate, interviewStartTime, interviewEndTime,
                interviewerIds);
        /* amend availability */
        List<UserAvailability> availabilities = mockAddInterview(interviewRequest);
        /* ensure new availability list has existing availability only with correctly amended start time */
        assertThat(availabilities.get(0).getAvailableTo()).isEqualTo(interviewStartTime);
        assertThat(availabilities.size()).isEqualTo(1);
    }

    /**
     * Tests amending availability with interview spanning availability.
     * <p>Tests amending availability with:
     * interview start time before or on availability start time and
     * interview end time on or after availability end time.</p>
     */
    @Test
    void testAmendAvailabilityInterviewOverEntireAvailability(){
        /* new interview request */
        LocalDate interviewDate = startDate;
        LocalTime interviewStartTime = startTime;
        LocalTime interviewEndTime = endTime;
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1); interviewerIds.add(2);
        InterviewRequest interviewRequest = new InterviewRequest(interviewDate, interviewStartTime, interviewEndTime,
                interviewerIds);
        /* amend availability */
        List<UserAvailability> availabilities = mockAddInterview(interviewRequest);
        /* ensure new availability list has no availability */
        assertThat(availabilities.size()).isEqualTo(0);
    }

    /**
     * Tests amending availability with interview splitting availability.
     * <p>Tests amending availability with:
     * interview start time after availability start time
     * interview end time before availability end time.</p>
     */
    @Test
    void testAmendAvailabilityInterviewSplittingAvailability(){
        /* new interview request */
        LocalDate interviewDate = startDate;
        LocalTime interviewStartTime = startTime.plusMinutes(15);
        LocalTime interviewEndTime = endTime.minusMinutes(15);
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1); interviewerIds.add(2);
        InterviewRequest interviewRequest = new InterviewRequest(interviewDate, interviewStartTime, interviewEndTime,
                interviewerIds);
        /* amend availability */
        List<UserAvailability> availabilities = mockAddInterview(interviewRequest);
        /* ensure new availability list has two availabilities */
        assertThat(availabilities.size()).isEqualTo(2);
        /* ensure first availability starts at interview end time */
        assertThat(availabilities.get(0).getAvailableFrom()).isEqualTo(interviewEndTime);
        /* ensure second availability ends at interview start time */
        assertThat(availabilities.get(1).getAvailableTo()).isEqualTo(interviewStartTime);

    }

    private List<UserAvailability> mockAddInterview(InterviewRequest interviewRequest){
        /* new interview request wrapper to attach organising user */
        InterviewRequestWrapper interviewRequestWrapper = new InterviewRequestWrapper(interviewRequest, user);
        /* new user availability list */
        UserAvailability userAvailability = new UserAvailability(user, startDate, startTime, endTime);
        List<UserAvailability> availabilityList = new ArrayList<>();
        availabilityList.add(userAvailability);
        /* mock repository layer returning current availability and new availability */
        when(availabilityRepository.getInTimeInterval(any(LocalDate.class), any(LocalTime.class),
                any(LocalTime.class))).thenReturn(availabilityList);
        /* amend availability */
        List<UserAvailability> availabilities = availabilityService.amendAvailability(interviewRequestWrapper);
        return availabilities;
    }

    /**
     * Tests that getting availability by skill set correctly assigns interviewers to availabilities.
     */
    @Test
    void testGetBySkillsAssignsInterviewerAttributes() {
        /* new skill IDs list */
        List<Integer> skillIds = new ArrayList<>();
        skillIds.add(1);
        skillIds.add(2);
        /* new user list */
        List<User> users = new ArrayList<>();
        users.add(user);
        /* mock skill service */
        when(userSkillService.findBySkills(anyList(), anyLong())).thenReturn(users);
        /* new user availability list */
        UserAvailability availability = new UserAvailability(user, startDate, startTime, endTime);
        List<UserAvailability> availabilityList = new ArrayList<>();
        availabilityList.add(availability);
        /* mock availability repository */
        when(availabilityRepository.getByUsers(anyList())).thenReturn(availabilityList);
        List<UserAvailability> availabilities = availabilityService.findBySkills(skillIds);
        /* ensure availability returned in list has correct interview name */
        assertThat(availabilities.get(0).getInterviewer()).isEqualTo(user.getName());
    }
}

