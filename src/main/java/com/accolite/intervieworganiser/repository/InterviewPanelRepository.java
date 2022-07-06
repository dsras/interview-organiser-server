package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.InterviewPanel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewPanelRepository
        extends
            JpaRepository<InterviewPanel, Integer> {
}
