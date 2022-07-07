package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.dto.AvailabilityWrapper;
import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.InterviewRequestWrapper;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.AvailabilityRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

    private AvailabilityRepository availabilityRepository;
    private UserSkillService userSkillService;
    Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    /**
     * Parameterised constructor.
     *
     * @param availabilityRepository availability data access layer
     * @param userSkillService user skills service layer
     */
    public AvailabilityService(
            @Autowired AvailabilityRepository availabilityRepository,
            @Autowired UserSkillService userSkillService
    ) {
        this.availabilityRepository = availabilityRepository;
        this.userSkillService = userSkillService;
    }

    public void delete(Integer oldAvailabilityId) {
        this.availabilityRepository.deleteAvailability(oldAvailabilityId);
    }

    public List<UserAvailability> getByRange(String userName, LocalDate start, LocalDate end) {
        List<UserAvailability> newAvailabilities = new ArrayList<>();
        if (userName == "") {
            newAvailabilities = availabilityRepository.getByRangeRec(start, end);
        } else {
            newAvailabilities = availabilityRepository.getByRange(userName, start, end);
        }

        for (UserAvailability a : newAvailabilities) {
            a.setInterviewer();
        }
        return newAvailabilities;
    }

    public List<UserAvailability> saveRange(List<AvailabilityWrapper> newAvailabilities) {
        List<UserAvailability> newAvailabilitiesList = new ArrayList<>();
        for (int i = 0; i < newAvailabilities.size(); i++) {
            User user = newAvailabilities.get(i).getInterviewer();
            LocalDate startDate = newAvailabilities.get(i).getStartDate();
            LocalDate endDate = newAvailabilities.get(i).getEndDate();
            LocalTime startTime = newAvailabilities.get(i).getStartTime();
            LocalTime endTime = newAvailabilities.get(i).getEndTime();
            List<Integer> ids = new ArrayList<>();
            ids.add(user.getId());
            for (
                    LocalDate date = startDate;
                    !date.isAfter(endDate);
                    date = date.plusDays(1)
            ) {

                List<UserAvailability> adjacentAvailabilities = availabilityRepository.getAdjacentAvailability(
                        date,
                        startTime,
                        endTime,
                        ids
                );
                if(adjacentAvailabilities.size() !=0){
                    newAvailabilitiesList.add(
                            adjustAdjacentAvailability(startTime, endTime, adjacentAvailabilities));
                }
                else {
                    newAvailabilitiesList.add(
                            availabilityRepository.save(
                                    new UserAvailability(user, date, startTime, endTime)
                            )
                    );
                }
            }
            for (UserAvailability a : newAvailabilitiesList) {
                a.setInterviewer();
            }
        }
        return newAvailabilitiesList;
    }

    public UserAvailability adjustAdjacentAvailability(LocalTime startTime, LocalTime endTime, List<UserAvailability> adjacent ){
        UserAvailability avail = adjacent.get(0);
        if(avail.getAvailableFrom().isBefore(startTime) && avail.getAvailableTo().isAfter(endTime)){
            //New time is completely inside old time; no change
        }
        else if(avail.getAvailableFrom() ==(startTime) && avail.getAvailableTo() ==(endTime)){
            //Times identical; no change
        }
        else if(avail.getAvailableFrom().isAfter(startTime) && avail.getAvailableTo().isBefore(endTime)){
            //Old time is completely inside new time; change both times on old slot to startTime, endTime
            availabilityRepository.updateStartTime(avail.getId(), startTime);
            availabilityRepository.updateEndTime(avail.getId(), endTime);
        }
        else if(avail.getAvailableFrom().isBefore(startTime) && (avail.getAvailableTo() ==(startTime) || avail.getAvailableTo().isAfter(startTime))){
            //StartTime between the slot times but end time later; change end time of old slot to endTime
            availabilityRepository.updateEndTime(avail.getId(), endTime);
        }
        else if((avail.getAvailableFrom().isBefore(endTime) || avail.getAvailableFrom() ==(endTime)) && avail.getAvailableTo().isAfter(endTime)){
            //EndTime between the slot times but start time earlier; change start time of old slot to startTime
            availabilityRepository.updateStartTime(avail.getId(), startTime);
        }

        return availabilityRepository.getById(avail.getId());
    }


    /* create new availability */
    public List<UserAvailability> save(AvailabilityWrapper newAvailability) {


            List<UserAvailability> newAvailabilities = new ArrayList<>();
            User user = newAvailability.getInterviewer();
            LocalDate startDate = newAvailability.getStartDate();
            LocalDate endDate = newAvailability.getEndDate();
            LocalTime startTime = newAvailability.getStartTime();
            LocalTime endTime = newAvailability.getEndTime();
            List<Integer> ids = new ArrayList<>();
            ids.add(user.getId());

            for (
                    LocalDate date = startDate;
                    !date.isAfter(endDate);
                    date = date.plusDays(1)
            ) {
                List<UserAvailability> adjacentAvailabilities = availabilityRepository.getAdjacentAvailability(
                        date,
                        startTime,
                        endTime,
                        ids
                );
                if(adjacentAvailabilities.size() !=0){
                    System.out.println("~~~~~~~~~~~~~~~~adjacent true~~~~~~~~~~~~~~~~~~~~");
                    newAvailabilities.add(
                            adjustAdjacentAvailability(startTime, endTime, adjacentAvailabilities));
                }
                else {
                    newAvailabilities.add(
                            availabilityRepository.save(
                                    new UserAvailability(user, date, startTime, endTime)
                            )
                    );
                }
            }
            for (UserAvailability a : newAvailabilities) {
                a.setInterviewer();
            }
            return newAvailabilities;

    }

    /* get user's availability */
    public List<UserAvailability> getUserAvailability(String username) {
        List<UserAvailability> availability = availabilityRepository.getByUsername(
            username
        );
        for (UserAvailability a : availability) {
            a.setInterviewer();
        }
        return availability;
    }

    /* get all availability */
    public List<UserAvailability> getAllAvailability() {
        logger.info("Getting all availability"); // Example logging
        List<UserAvailability> availability = availabilityRepository.findAll();
        for (UserAvailability a : availability) {
            a.setInterviewer();
        }
        return availability;
    }

    /* amend availability of user to reflect new booking */
    public List<UserAvailability> amendAvailability(
            InterviewRequestWrapper wrapper
    ) {

            List<UserAvailability> currentAvailability = availabilityRepository.getInTimeIntervalWithId(
                    wrapper.getDate(),
                    wrapper.getStartTime(),
                    wrapper.getEndTime(),
                    wrapper.getInterviewerIds()
            );
            /* lists to store availabilities that need to be removed or saved to database */
            List<UserAvailability> availabilitiesToRemove = new ArrayList<>();
            List<UserAvailability> availabilitiesToAdd = new ArrayList<>();
            for (UserAvailability availability : currentAvailability) {
                /* if interview spans entire availability: */
                if (
                        !wrapper
                                .getStartTime()
                                .isAfter(availability.getAvailableFrom())
                                &&
                                !wrapper.getEndTime().isBefore(availability.getAvailableTo())
                ) {
                    /* delete availability */
                    availabilitiesToRemove.add(availability);
                } /* if available before interview: */ else if (
                        availability.getAvailableFrom().isBefore(wrapper.getStartTime())
                ) {
                    /* and available after: */
                    if (
                            availability.getAvailableTo().isAfter(wrapper.getEndTime())
                    ) {
                        /* create new availability from interview end time until availability end time */
                        UserAvailability newAvailability = new UserAvailability(
                                availability.getUser(),
                                availability.getAvailableDate(),
                                wrapper.getEndTime(),
                                availability.getAvailableTo()
                        );
                        availabilitiesToAdd.add(newAvailability);
                    }
                    /* update existing availability to end at interview start time */
                    availability.setAvailableTo(wrapper.getStartTime());
                    availabilitiesToAdd.add(availability);
                } else {/* if not available before interview but available after: */
                    if (
                            availability.getAvailableTo().isAfter(wrapper.getEndTime())
                    ) {
                        /* update existing availability to start at interview end time */
                        availability.setAvailableFrom(wrapper.getEndTime());
                        availabilitiesToAdd.add(availability);
                    }
                }
            }
            /* update database with amended availabilities */
            availabilityRepository.deleteAllInBatch(availabilitiesToRemove);
            availabilityRepository.saveAll(availabilitiesToAdd);
            /* return list of new/amended availabilities (for testing) */
            return availabilitiesToAdd;


    }

    /* get availability by skill */
    public List<UserAvailability> findBySkills(List<Integer> skillIds) {
        /* get users with all listed skills */
        List<Integer> users = userSkillService.findBySkills(
            skillIds
        );
        /* get availability of users found to have all skills */
        List<UserAvailability> availabilities = availabilityRepository.getByUsers(
            users
        );
        /* assign interviewer attributes to availability objects */
        for (UserAvailability a : availabilities) {
            a.setInterviewer();
        }
        return availabilities;
    }

    /* get interviewer availabilities in time slot from list of qualified interviewers */
    public List<UserAvailability> getAvailableInterviewers(
            List<Integer> users,
            FindInterviewersRequest request
    ) {
        /* get available interviewers */
        List<UserAvailability> availabilities = availabilityRepository.getAvailableInterviewers(
            users,
            request.getStartDate(),
            request.getEndDate(),
            request.getStartTime(),
            request.getEndTime()
        );

        /* assign interviewer attributes to availabilities */
        for (UserAvailability a : availabilities) {
            a.setInterviewer();
        }
        return availabilities;
    }

    public List<UserAvailability> getAvailableInterviewersAccurate(
            List<Integer> users,
            FindInterviewersRequest request
    ) {
        /* get available interviewers */
        List<UserAvailability> availabilities = availabilityRepository.getAvailableInterviewersAccurate(
            users,
            request.getStartDate(),
            request.getEndDate(),
            request.getStartTime(),
            request.getEndTime()
        );

        /* assign interviewer attributes to availabilities */
        for (UserAvailability a : availabilities) {
            a.setInterviewer();
        }
        return availabilities;
    }
}
