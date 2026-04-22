package com.localbridge.market_nexus.controller;

import com.localbridge.market_nexus.entity.JobPosting;
import com.localbridge.market_nexus.repository.JobPostingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobPostingController {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingController(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    // Notice we use the custom method we wrote earlier!
    @GetMapping("/active")
    public List<JobPosting> getActiveJobs() {
        return jobPostingRepository.findByIsActiveTrue();
    }
}