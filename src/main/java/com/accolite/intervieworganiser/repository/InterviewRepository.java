package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {
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
        "WHERE i.interview_date <= current_date " +
        "AND i.interview_date >= :date " +
        "AND i.organiser_id = :userId) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
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
        "accolite_user u inner join interview_panel p #" +
        "on u.user_id=p.interviewer_id " +
        "group by p.interview_id) panel inner join interview i " +
        "ON panel.interview_id = i.interview_id) interviewInfo inner join accolite_user a " +
        "ON interviewInfo.organiser_id = a.user_id;",
        nativeQuery = true
    )
    List<com.accolite.intervieworganiser.dto.Interview> findAllInterviews();
}
