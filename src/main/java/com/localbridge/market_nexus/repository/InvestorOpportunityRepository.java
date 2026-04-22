package com.localbridge.market_nexus.repository;

import com.localbridge.market_nexus.entity.InvestorOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorOpportunityRepository extends JpaRepository<InvestorOpportunity, Long> {
    // Easily filter between Government Bids or Commercial Spaces
    List<InvestorOpportunity> findByOpportunityTypeAndIsActiveTrue(String opportunityType);
}