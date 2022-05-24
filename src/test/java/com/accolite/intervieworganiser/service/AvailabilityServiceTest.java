package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.*;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import utility.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    UserSkillService userSkillService;

    AvailabilityService availabilityService;
    User interviewer = Constants.interviewer, organiser = Constants.recruiter;
    LocalDate startDate = Constants.startDate, endDate = Constants.endDate;
    LocalTime startTime = Constants.startTime, endTime = Constants.endTime;
    UserAvailability availability1, availability2;

    @BeforeEach
    void init(){
        availabilityService = new AvailabilityService(availabilityRepository,
                userSkillService);
        availability1  = Constants.availability1; availability2 = Constants.availability2;
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
        AvailabilityWrapper availabilityWrapper = new AvailabilityWrapper(availabilityRequest, interviewer);

        /* mock repository saving availability */
        when(availabilityRepository.save(any(UserAvailability.class))).thenAnswer(i -> i.getArgument(0));

        /* save availability request containing 2 days of availability */
        List<UserAvailability> returnedAvailabilityList = availabilityService.save(availabilityWrapper);

        /* ensure services correctly saves availabilities for 2 days */
        assertThat(returnedAvailabilityList.get(0).getAvailableDate()).isEqualTo(startDate);
        assertThat(returnedAvailabilityList.get(1).getAvailableDate()).isEqualTo(endDate);
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
        List<UserAvailability> returnedAvailabilityList = mockAddInterview(interviewRequest);

        /* ensure new availability list has existing availability only with correctly amended start time */
        assertThat(returnedAvailabilityList.get(0).getAvailableFrom()).isEqualTo(interviewEndTime);
        assertThat(returnedAvailabilityList.size()).isEqualTo(1);
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
        List<UserAvailability> returnedAvailabilityList = mockAddInterview(interviewRequest);

        /* ensure new availability list has existing availability only with correctly amended start time */
        assertThat(returnedAvailabilityList.get(0).getAvailableTo()).isEqualTo(interviewStartTime);
        assertThat(returnedAvailabilityList.size()).isEqualTo(1);
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
        List<UserAvailability> returnedAvailabilityList = mockAddInterview(interviewRequest);

        /* ensure new availability list has no availability */
        assertThat(returnedAvailabilityList.size()).isEqualTo(0);
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
        LocalTime interviewStartTime = startTime.plusMinutes(15); //10:15
        LocalTime interviewEndTime = endTime.minusMinutes(15); //10:45
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1); interviewerIds.add(2);
        InterviewRequest interviewRequest = new InterviewRequest(interviewDate, interviewStartTime, interviewEndTime,
                interviewerIds);

        /* amend availability */
        List<UserAvailability> returnedAvailabilityList = mockAddInterview(interviewRequest);

        /* ensure new availability list has two availabilities */
        assertThat(returnedAvailabilityList.size()).isEqualTo(2);

        /* ensure first availability starts at interview end time */
        assertThat(returnedAvailabilityList.get(0).getAvailableFrom()).isEqualTo(interviewEndTime);

        /* ensure second availability ends at interview start time */
        assertThat(returnedAvailabilityList.get(1).getAvailableTo()).isEqualTo(interviewStartTime);

    }

    /**
     * Mocks saving an interview and amending availabilities
     *
     * @param interviewRequest the interview request
     * @return list of user availabilities amended after interview is saved
     */
    private List<UserAvailability> mockAddInterview(InterviewRequest interviewRequest){
        /* new interview request wrapper to attach organising user */
        InterviewRequestWrapper interviewRequestWrapper = new InterviewRequestWrapper(interviewRequest, organiser);

        /* new user availability list */
        List<UserAvailability> originalAvailabilityList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2022, 5, 3);
        LocalTime startTime = LocalTime.of(10,0);
        LocalTime endTime = LocalTime.of(11,0);
        availability1 = new UserAvailability(interviewer, startDate, startTime, endTime);
        originalAvailabilityList.add(availability1);

        /* mock repository layer returning current availability and new availability */
        when(availabilityRepository.getInTimeInterval(any(LocalDate.class), any(LocalTime.class),
                any(LocalTime.class))).thenReturn(originalAvailabilityList);

        /* amend availability */
        List<UserAvailability> returnedAvailabilityList = availabilityService.amendAvailability(interviewRequestWrapper);
        return returnedAvailabilityList;
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
        users.add(interviewer);

        /* mock skill service */
        when(userSkillService.findBySkills(anyList(), anyLong())).thenReturn(users);

        /* new user availability list */
        UserAvailability availability = new UserAvailability(interviewer, startDate, startTime, endTime);
        List<UserAvailability> originalAvailabilityList = new ArrayList<>();
        originalAvailabilityList.add(availability);

        /* mock availability repository and call findBySkills method in service class */
        when(availabilityRepository.getByUsers(anyList())).thenReturn(originalAvailabilityList);
        List<UserAvailability> returnedAvailabilityList = availabilityService.findBySkills(skillIds);

        /* ensure availability returned in list has correct interview name */
        assertThat(returnedAvailabilityList.get(0).getInterviewer()).isEqualTo(interviewer.getName());
    }

    /**
     * Tests that getAllAvailability correctly returns all availability as returned by repository &
     * assigns interviewer attributes (required for JSON output of UserAvailability objects)
     */
    @Test
    void testGetAllAvailability(){
        /* list of user availabilities */
        List<UserAvailability> originalAvailabilityList = new ArrayList<>();
        originalAvailabilityList.add(availability1);
        originalAvailabilityList.add(availability2);

        /* mock repository call to find all and call getAllAvailability method in service class*/
        when(availabilityRepository.findAll()).thenReturn(originalAvailabilityList);
        List<UserAvailability> returnedAvailabilityList = availabilityService.getAllAvailability();

        /* assert returned availabilityList is correct */
        assertEquals(originalAvailabilityList, returnedAvailabilityList);
        /* assert interviewer details are assigned */
        assertThat(returnedAvailabilityList.get(0).getInterviewer()).isEqualTo(interviewer.getName());
        assertThat(returnedAvailabilityList.get(1).getInterviewer()).isEqualTo(interviewer.getName());
    }

    @Test
    void testGetAvailableInterviewers(){
        /* list of interviewer availability */
        List<User> users = new ArrayList<>();
        users.add(interviewer);
        List<UserAvailability> originalAvailabilityList = new ArrayList<>();
        originalAvailabilityList.add(availability1);
        originalAvailabilityList.add(availability2);

        /* find interviewers request */
        List<Integer> skillIds = new ArrayList<>();
        skillIds.add(1);
        FindInterviewersRequest findInterviewersRequest = new FindInterviewersRequest(startDate, endDate,
                startTime, endTime, skillIds);

        /* mock repository returning filtered results */
        when(availabilityRepository.getAvailableInterviewers(users,
                startDate, endDate, startTime,endTime)).thenReturn(originalAvailabilityList);

        List<UserAvailability> returnedAvailabilityList = availabilityService.getAvailableInterviewers(users,
                findInterviewersRequest);
        /* assert correct list is returned */
        assertEquals(originalAvailabilityList, returnedAvailabilityList);

        /* assert interviewer attributes correctly assigned */
        assertEquals(interviewer.getName(), returnedAvailabilityList.get(0).getInterviewer());
        assertEquals(interviewer.getName(), returnedAvailabilityList.get(1).getInterviewer());
    }

    /**
     * Tests that getUserAvailability correctly returns user availability as returned by repository &
     * assigns interviewer attributes (required for JSON output of UserAvailability objects)
     */
    @Test
    void testGetUserAvailability(){
        /* list of user availabilities */
        List<UserAvailability> originalAvailabilityList = new ArrayList<>();
        originalAvailabilityList.add(availability1);
        originalAvailabilityList.add(availability2);

        /* mock repository call to find all and call getAllAvailability method in service class*/
        when(availabilityRepository.getByUsername(interviewer.getUsername())).thenReturn(originalAvailabilityList);
        List<UserAvailability> returnedAvailabilityList = availabilityService.getUserAvailability(interviewer.getUsername());

        /* assert returned availabilityList is correct */
        assertEquals(originalAvailabilityList, returnedAvailabilityList);
        /* assert interviewer details are assigned */
        assertThat(returnedAvailabilityList.get(0).getInterviewer()).isEqualTo(interviewer.getName());
        assertThat(returnedAvailabilityList.get(1).getInterviewer()).isEqualTo(interviewer.getName());
    }

}

