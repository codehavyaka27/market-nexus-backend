package com.localbridge.market_nexus.repository;

import com.localbridge.market_nexus.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    // Spring magically writes the SQL for this just by reading the method name!
    List<JobPosting> findByIsActiveTrue();
}