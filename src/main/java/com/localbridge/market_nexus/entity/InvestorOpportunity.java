package com.localbridge.market_nexus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "investor_opportunities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorOpportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // e.g., "10x10 Stall at Mela" OR "City Road Repair Tender"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    // NEW: This tells the frontend how to display this specific opportunity
    @Column(name = "opportunity_type", nullable = false)
    private String opportunityType; // e.g., "COMMERCIAL_SPACE", "GOVT_BIDDING", "BUSINESS_EQUITY"

    // Renamed 'price' to be more flexible for bids or equity
    @Column(name = "investment_amount")
    private Double investmentAmount;

    // NEW: Crucial for government tenders
    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    // We removed 'nullable = false' here. A tender might have a deadline, but no strict 'end date'.
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // A flexible catch-all instead of 'spaceSize'.
    // Can hold "100 sq ft" for stalls, or "Requires Class A Contractor License" for bids.
    @Column(columnDefinition = "TEXT", name = "specific_requirements")
    private String specificRequirements;

    @Column(name = "is_active")
    private boolean isActive = true;

    // The "Shop" here acts as the issuer (e.g., The Mela Committee OR the Municipal Corporation)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "issuer_id", nullable = false)
    private Shop issuer;
}