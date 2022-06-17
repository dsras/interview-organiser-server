package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.dto.AvailTempReturn;
import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Transactional
    @Modifying
    @Query(
            value = "Delete " +
                    "from interview i " +
                    "where i.interview_id = :intId ",

            nativeQuery = true
    )
    void deleteInterview(
            @Param("intId") Integer intId
    );
    @Transactional
    @Modifying
    @Query(
            value =
                    "Delete " +
                    "from interview_panel ip " +
                    "where ip.interview_id = :intId ",
            nativeQuery = true
    )
    void deleteIPanel(
            @Param("intId") Integer intId
    );


    @Query(
            value = "Select " +
                    "info.availability_id as availability_id, " +
                    "au.username as username, " +
                    "info.available_date as date, " +
                    "info.available_from as start, " +
                    "info.available_to as end, " +
                    "info.interview_start as interview_start, " +
                    "info.interview_end as interview_end " +
                    "From ( " +
                        "Select ua.availability_id, ua.user_id, ua.available_date, ua.available_from, ua.available_to, iinfo.time_start as interview_start, iinfo.time_end as interview_end " +
                        "from user_availability ua " +
                        "join ( " +
                            "Select ip.interview_id, ip.interviewer_id, ifo.interview_date, ifo.time_start, ifo.time_end " +
                            "from ( " +
                                "Select i.interview_id, i.interview_date, i.time_start, i.time_end " +
                                "From interview i " +
                                "where i.interview_id = :id " +
                            ") as ifo " +
                            "join interview_panel ip " +
                            "on ifo.interview_id = ip.interview_id " +
                        ") as iinfo " +
                        "on iinfo.interviewer_id = ua.user_id " +
                        "where ua.available_date = iinfo.interview_date " +
                    ") as info " +
                    "join accolite_user au " +
                    "on au.user_id = info.user_id;",
            nativeQuery = true
    )
    List<AvailTempReturn> getAvailabilitiesToRecompile(
            @Param("id") String id
    );


    @Query(
            value = "Select majData.interview_id as InterviewId, " +
                    "majData.organiser as Organiser, " +
                    "majData.organiserID as OrganiserID, " +
                    "au2.name as interviewers , " +
                    "majData.interview_date as date , " +
                    "majData.time_start as startTime , " +
                    "majData.time_end as endTime, " +
                    "majData.status as status, " +
                    "majData.outcome as outcome, " +
                    "majData.additional_info as additionalInfo " +
                    "from accolite_user au2 " +
                    "join ( " +
                        "Select ip.interview_id, " +
                        "ints.organiser, " +
                        "ints.organiserID, " +
                        "ip.interviewer_id, " +
                        "ints.interview_date, " +
                        "ints.time_start, " +
                        "ints.time_end, " +
                        "ints.status, " +
                        "ints.outcome, " +
                        "ints.additional_info " +
                        "from interview_panel ip " +
                        "join ( " +
                            "Select i.interview_id, " +
                            "u.name as organiser, " +
                            "u.user_id as organiserID, " +
                            "i.interview_date, " +
                            "i.time_start, " +
                            "i.time_end, " +
                            "i.status, " +
                            "i.outcome, " +
                            "i.additional_info " +
                            "from ( " +
                                "Select * " +
                                "from interview " +
                                "where interview.interview_date > :startDate " +
                                "and interview.interview_date < :endDate " +
                            ") i " +
                            "join ( " +
                                "Select * " +
                                "from accolite_user au " +
                                "where au.userName = :username " +
                            ") as u " +
                            "on i.organiser_id = u.user_id " +
                        ") as ints " +
                        "on ip.interview_id = ints.interview_id " +
                    ") as majData " +
                "on au2.user_id = majData.interviewer_id;",
            nativeQuery = true

    )
    List<com.accolite.intervieworganiser.dto.Interview> findByInterviewerPerMonth(
            @Param("username") String username,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    @Query(
        "SELECT i.interviewer FROM InterviewPanel i WHERE i.interview.id = :id"
    )
    List<User> findInterviewers(@Param("id") Integer id);

    @Query(
        "SELECT COUNT(i) FROM Interview i, InterviewPanel j WHERE i = j.interview " +
        "and i.status = 'Confirmed' and j.interviewer = :user"
    )
    Integer findCompleted(@Param("user") User user);

    @Query(
        value = "Select a.name as organiser, " +
        "interviewInfo.interviewId as interviewId," +
        "interviewInfo.interview_date as date, " +
        "interviewInfo.time_start as startTime, " +
        "interviewInfo.time_end as endTime, " +
        "interviewInfo.status as status, " +
        "interviewInfo.outcome as outcome, " +
        "interviewInfo.interviewers as interviewers," +
        "interviewInfo.additional_info as additionalInfo " +
        "from " +
        "(Select panel.interview_id as interviewId, " +
        "panel.interviewers, " +
        "i.organiser_id, " +
        "i.interview_date, " +
        "i.time_start, " +
        "i.time_end, " +
        "i.status, " +
        "i.outcome, " +
        "i.additional_info " +
        "from " +
        "(select p.interview_id, p.interviewer_id, " +
        "group_concat(u.name) as interviewers " +
        "from " +
        "accolite_user u inner join interview_panel p " +
        "on u.user_id=p.interviewer_id " +
        "group by p.interview_id) panel inner join interview i " +
        "ON panel.interview_id = i.interview_id " +
        "WHERE i.interview_date <= current_date " +
        "AND i.interview_date >= :date " +
        "AND panel.interviewer_id = :userId) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findAllByInterviewer(
        @Param("userId") Integer userId,
        @Param("date") LocalDate minDate
    );

    @Query(
        value =
                "Select a.name as organiser, " +
                    "interviewInfo.interviewId as interviewId, " +
                    "interviewInfo.interview_date as date, " +
                    "interviewInfo.time_start as startTime, " +
                    "interviewInfo.time_end as endTime, " +
                    "interviewInfo.status as status, " +
                    "interviewInfo.outcome as outcome, " +
                    "interviewInfo.interviewers as interviewers," +
                    "interviewInfo.additional_info as additionalInfo " +
                    "from " +
                        "(Select panel.interview_id as interviewId, " +
                        "panel.interviewers, " +
                        "i.organiser_id, " +
                        "i.interview_date, " +
                        "i.time_start, " +
                        "i.time_end, " +
                        "i.status, "  +
                        "i.outcome, " +
                        "i.additional_info " +
                        "from " +
                        "(select p.interview_id," +
                        "group_concat(u.name) as interviewers " +
                        "from " +
                            "accolite_user u inner join interview_panel p "  +
                            "on u.user_id=p.interviewer_id " +
                            "group by p.interview_id) panel inner join interview i " +
                    "ON panel.interview_id = i.interview_id " +
                    "Where i.interview_date >= :date " +
                    "And i.organiser_id = :userId) interviewInfo inner join accolite_user a " +
                "ON interviewInfo.organiser_id = a.user_id; ",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findAllByRecruiter(
        @Param("userId") Integer userId,
        @Param("date") LocalDate minDate
    );

    @Query(
        value = "Select a.name as organiser, " +
        "interviewInfo.interviewId as interviewId," +
        "interviewInfo.interview_date as date, " +
        "interviewInfo.time_start as startTime, " +
        "interviewInfo.time_end as endTime, " +
        "interviewInfo.status as status, " +
        "interviewInfo.outcome as outcome, " +
        "interviewInfo.interviewers as interviewers," +
        "interviewInfo.additional_info as additionalInfo " +
        "from " +
        "(Select panel.interview_id as interviewId, " +
        "panel.interviewers, " +
        "i.organiser_id, " +
        "i.interview_date, " +
        "i.time_start, " +
        "i.time_end, " +
        "i.status, " +
        "i.outcome, " +
        "i.additional_info " +
        "from " +
        "(select p.interview_id, " +
        "group_concat(u.name) as interviewers " +
        "from " +
        "accolite_user u inner join interview_panel p " +
        "on u.user_id=p.interviewer_id " +
        "group by p.interview_id) panel inner join interview i " +
        "ON panel.interview_id = i.interview_id " +
        "where i.status = :status " +
        "AND i.interview_date <= current_date " +
        "AND i.interview_date >= :date " +
        "AND i.organiser_id = :userId) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findByStatus(
        @Param("userId") Integer userId,
        @Param("status") String status,
        @Param("date") LocalDate minDate
    );

    @Query(
        value = "Select a.name as organiser, " +
        "interviewInfo.interviewId as interviewId," +
        "interviewInfo.interview_date as date, " +
        "interviewInfo.time_start as startTime, " +
        "interviewInfo.time_end as endTime, " +
        "interviewInfo.status as status, " +
        "interviewInfo.outcome as outcome, " +
        "interviewInfo.interviewers as interviewers," +
        "interviewInfo.additional_info as additionalInfo " +
        "from " +
        "(Select panel.interview_id as interviewId, " +
        "panel.interviewers, " +
        "i.organiser_id, " +
        "i.interview_date, " +
        "i.time_start, " +
        "i.time_end, " +
        "i.status, " +
        "i.outcome, " +
        "i.additional_info " +
        "from " +
        "(select p.interview_id, " +
        "group_concat(u.name) as interviewers " +
        "from " +
        "accolite_user u inner join interview_panel p " +
        "on u.user_id=p.interviewer_id " +
        "group by p.interview_id) panel inner join interview i " +
        "ON panel.interview_id = i.interview_id " +
        "where i.outcome = :outcome " +
        "AND i.interview_date <= current_date " +
        "AND i.interview_date >= :date " +
        "AND i.organiser_id = :userId) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findByOutcome(
        @Param("userId") Integer userId,
        @Param("date") LocalDate minDate,
        @Param("outcome") String outcome
    );

    @Query(
        value = "Select a.name as organiser, " +
        "interviewInfo.interviewId as interviewId," +
        "interviewInfo.interview_date as date, " +
        "interviewInfo.time_start as startTime, " +
        "interviewInfo.time_end as endTime, " +
        "interviewInfo.status as status, " +
        "interviewInfo.outcome as outcome, " +
        "interviewInfo.interviewers as interviewers," +
        "interviewInfo.additional_info as additionalInfo " +
        "from " +
        "(Select panel.interview_id as interviewId, " +
        "panel.interviewers, " +
        "i.organiser_id, " +
        "i.interview_date, " +
        "i.time_start, " +
        "i.time_end, " +
        "i.status, " +
        "i.outcome, " +
        "i.additional_info " +
        "from " +
        "(select p.interview_id, " +
        "group_concat(u.name) as interviewers " +
        "from " +
        "accolite_user u inner join interview_panel p " +
        "on u.user_id=p.interviewer_id " +
        "group by p.interview_id) panel inner join interview i " +
        "ON panel.interview_id = i.interview_id) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findAllInterviews();
}
