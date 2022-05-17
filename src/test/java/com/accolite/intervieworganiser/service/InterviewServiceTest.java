package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.InterviewRequest;
import com.accolite.intervieworganiser.dto.InterviewRequestWrapper;
import com.accolite.intervieworganiser.dto.InterviewResponse;
import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.InterviewPanel;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.InterviewPanelRepository;
import com.accolite.intervieworganiser.repository.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utility.Constants;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewServiceTest {

    @Mock
    InterviewRepository interviewRepository;
    @Mock
    InterviewPanelRepository interviewPanelRepository;
    @Mock
    UserService userService;
    @Mock
    AvailabilityService availabilityService;
    InterviewService interviewService;

    @BeforeEach
    void init() {
        interviewService = new InterviewService(interviewRepository, interviewPanelRepository,
                userService, availabilityService);
    }

    /**
     * Tests that updateStatus correctly returns updated interview
     */
    @Test
    void testUpdateStatus(){
        /* interview */
        Interview interview = Constants.interview;
        interview.setStatus("Confirmed");
        interview.setId(1);
        Set<InterviewPanel> panel = new HashSet<>();
        panel.add(new InterviewPanel(interview, Constants.interviewer));
        interview.setInterviewPanel(panel);

        /* mock repository calls */
        when(interviewRepository.getById(1)).thenReturn(interview);
        when(interviewRepository.save(interview)).thenReturn(interview);

        /* expected list of interviewers */
        List<User> interviewers = new ArrayList<>();
        interviewers.add(Constants.interviewer);

        InterviewResponse interviewResponse = interviewService.updateStatus("Confirmed", 1);

        /* assert correct interview response object returned */
        assertEquals(1, interviewResponse.getInterviewId());
        assertEquals(interview.getInterviewDate(), interviewResponse.getDate());
        assertEquals(interview.getTimeStart(), interviewResponse.getStartTime());
        assertEquals(interview.getTimeEnd(), interviewResponse.getEndTime());
        assertEquals(interviewers.get(0).getName(), interviewResponse.getInterviewers().get(0));
        assertEquals("Confirmed", interviewResponse.getStatus());
        assertEquals(Constants.recruiter.getName(), interviewResponse.getOrganiser());
        assertEquals(" ", interviewResponse.getInfo());
        assertNull(interviewResponse.getOutcome());
    }

    /**
     * Tests that updateOutcome correctly returns updated interview
     */
    @Test
    void testUpdateOutcome(){
        /* interview */
        Interview interview = new Interview(Constants.recruiter, Constants.startDate, Constants.startTime,
                Constants.endTime, " ");
        interview.setOutcome("Hired");
        interview.setId(1);
        Set<InterviewPanel> panel = new HashSet<>();
        panel.add(new InterviewPanel(interview, Constants.interviewer));
        interview.setInterviewPanel(panel);

        /* mock repository calls */
        when(interviewRepository.getById(1)).thenReturn(interview);
        when(interviewRepository.save(interview)).thenReturn(interview);

        /* expected list of interviewers */
        List<User> interviewers = new ArrayList<>();
        interviewers.add(Constants.interviewer);

        InterviewResponse interviewResponse = interviewService.updateOutcome("Hired", 1);

        /* assert correct interview response object returned */
        assertEquals(1, interviewResponse.getInterviewId());
        assertEquals(interview.getInterviewDate(), interviewResponse.getDate());
        assertEquals(interview.getTimeStart(), interviewResponse.getStartTime());
        assertEquals(interview.getTimeEnd(), interviewResponse.getEndTime());
        assertEquals(interviewers.get(0).getName(), interviewResponse.getInterviewers().get(0));
        assertEquals("Hired", interviewResponse.getOutcome());
        assertEquals(Constants.recruiter.getName(), interviewResponse.getOrganiser());
        assertEquals(" ", interviewResponse.getInfo());
        assertNull(interviewResponse.getStatus());
    }

    /**
     * Tests that save correctly returns new interview
     */
    @Test
    void testSaveInterviewReturnsCorrectResponse(){
        /* prerequisites */
        Interview interview = new Interview(Constants.recruiter, Constants.startDate, Constants.startTime,
                Constants.endTime, " ");
        User interviewer = Constants.interviewer;
        List<Integer> interviewerIds = new ArrayList<>();
        interviewerIds.add(1);
        InterviewRequest interviewRequest = new InterviewRequest(Constants.startDate, Constants.startTime,
                Constants.endTime, interviewerIds);
        InterviewRequestWrapper interviewRequestWrapper = new InterviewRequestWrapper(interviewRequest,
                Constants.recruiter);

        /* mock interview repository and user service calls */
        when(interviewRepository.save(any())).thenReturn(interview);
        when(userService.getById(1)).thenReturn(interviewer);

        InterviewResponse returnedInterviewResponse = interviewService.save(interviewRequestWrapper);

        /* verify repository method to create interview panel objects is called 1 time (once per interviewer) */
        Mockito.verify(interviewPanelRepository, Mockito.times(1)).save(any());

        /* assert correct interview response object is returned */
        assertEquals(Constants.recruiter.getName(), returnedInterviewResponse.getOrganiser());
        assertEquals(Constants.startDate, returnedInterviewResponse.getDate());
        assertEquals(Constants.startTime, returnedInterviewResponse.getStartTime());
        assertEquals(Constants.endTime, returnedInterviewResponse.getEndTime());
        assertEquals(interviewer.getName(), returnedInterviewResponse.getInterviewers().get(0));
        assertNull(returnedInterviewResponse.getOutcome());
        assertNull(returnedInterviewResponse.getStatus());
        assertEquals(" ", returnedInterviewResponse.getInfo());
    }

    /**
     * Tests that save makes correct number of calls to interview panel repository to create interview panel objects
     * from new interview object
     */
    @Test
    void testSaveInterviewCreatesCorrectNumInterviewPanelObjects(){

        User interviewer1 = Constants.interviewer;
        User interviewer2 = Constants.interviewer;
        User interviewer3 = Constants.interviewer;
        List<Integer> interviewerIds = new ArrayList<>();

        /* 1 interviewer */
        interviewerIds.add(1);
        Interview interview = new Interview(Constants.recruiter, Constants.startDate, Constants.startTime,
                Constants.endTime, " ");

        InterviewRequest interviewRequest = new InterviewRequest(Constants.startDate, Constants.startTime,
                Constants.endTime, interviewerIds);
        InterviewRequestWrapper interviewRequestWrapper = new InterviewRequestWrapper(interviewRequest,
                Constants.recruiter);

        /* mock interview repository and user service calls */
        when(interviewRepository.save(any())).thenReturn(interview);
        when(userService.getById(1)).thenReturn(interviewer1);

        /* verify repository method to create interview panel objects is called 1 time (once per interviewer) */
        interviewService.save(interviewRequestWrapper);
        Mockito.verify(interviewPanelRepository, Mockito.times(1)).save(any());

        /* 3 interviewers */
        interviewerIds.add(2); interviewerIds.add(3);
        interviewRequest = new InterviewRequest(Constants.startDate, Constants.startTime,
                Constants.endTime, interviewerIds);
        interviewRequestWrapper = new InterviewRequestWrapper(interviewRequest,
                Constants.recruiter);

        /* mock interview repository and user service calls */
        when(userService.getById(2)).thenReturn(interviewer2);
        when(userService.getById(3)).thenReturn(interviewer3);

        /* verify repository method to create interview panel objects is called 3 times (once per interviewer) */
        Mockito.clearInvocations(interviewPanelRepository);
        interviewService.save(interviewRequestWrapper);
        Mockito.verify(interviewPanelRepository, Mockito.times(3)).save(any());
    }

    /**
     * Tests correct interview response objects are returned by getInterviewResponseList
     * and subsequently by findAll()
     */
    @Test
    void testFindAll(){
        /* interviewers and interview to pass */
        List<User> interviewers = new ArrayList<>();
        interviewers.add(Constants.interviewer);
        List<Interview> interviews = new ArrayList<>();
        Interview interview = Constants.interview;
        interview.setId(1);
        interviews.add(interview);

        /* mock repository call */
        when(interviewRepository.findAll()).thenReturn(interviews);
        when(interviewRepository.findInterviewers(1)).thenReturn(interviewers);

        /* assert correct interview response objects are returned */
        List<InterviewResponse> returnedInterviewResponse = interviewService.findAll();
        assertEquals(1, returnedInterviewResponse.size());
        assertEquals(1, returnedInterviewResponse.get(0).getInterviewers().size());
        assertEquals(interviewers.get(0).getName(), returnedInterviewResponse.get(0).getInterviewers().get(0));
        assertEquals(1,returnedInterviewResponse.get(0).getInterviewId());
    }

}
