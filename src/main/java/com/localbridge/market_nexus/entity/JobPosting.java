package com.localbridge.market_nexus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_postings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String wageType; // e.g., "Per Day", "Per Month", "Per Project"

    @Column(nullable = false)
    private Double amount;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "posted_at", updatable = false)
    private LocalDateTime postedAt = LocalDateTime.now();

    // The Relational Bridge
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)//this ensures only required information is fetched instead of giving every details
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
}