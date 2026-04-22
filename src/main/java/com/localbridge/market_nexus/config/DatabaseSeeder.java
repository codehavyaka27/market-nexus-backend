package com.localbridge.market_nexus.config;

import com.localbridge.market_nexus.entity.InvestorOpportunity;
import com.localbridge.market_nexus.entity.JobPosting;
import com.localbridge.market_nexus.entity.Shop;
import com.localbridge.market_nexus.repository.InvestorOpportunityRepository;
import com.localbridge.market_nexus.repository.JobPostingRepository;
import com.localbridge.market_nexus.repository.ShopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ShopRepository shopRepository;
    private final JobPostingRepository jobPostingRepository;
    private final InvestorOpportunityRepository investorRepository;

    // Spring automatically injects our Warehouse Managers here
    public DatabaseSeeder(ShopRepository shopRepository, JobPostingRepository jobPostingRepository, InvestorOpportunityRepository investorRepository) {
        this.shopRepository = shopRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.investorRepository = investorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only run the seeder if the database is completely empty
        if (shopRepository.count() == 0) {
            System.out.println("🌱 Database is empty. Seeding initial dummy data...");

            // --- 1. CREATE ISSUERS (SHOPS/COMMITTEES) ---

            Shop teaStall = new Shop();
            teaStall.setName("Ramesh Tea & Snacks");
            teaStall.setCategory("Eatery");
            teaStall.setPhoneNumber("+919876543210");
            // Coordinates for a bustling area in Bengaluru (e.g., Indiranagar)
            teaStall.setLocation(77.6411, 12.9718);

            Shop melaCommittee = new Shop();
            melaCommittee.setName("Sirsi Marikamba Jatre Committee");
            melaCommittee.setCategory("Event Organizer");
            melaCommittee.setPhoneNumber("+919876543211");
            // Coordinates for Sirsi
            melaCommittee.setLocation(74.8354, 14.6195);

            // Save them to the database
            shopRepository.saveAll(List.of(teaStall, melaCommittee));

            // --- 2. CREATE JOB POSTINGS ---

            JobPosting deliveryJob = new JobPosting();
            deliveryJob.setTitle("Morning Delivery Boy");
            deliveryJob.setDescription("Need a reliable person to deliver morning tea to nearby offices. Cycle provided.");
            deliveryJob.setWageType("Per Month");
            deliveryJob.setAmount(6000.00);
            deliveryJob.setShop(teaStall); // Link it to the Tea Stall

            JobPosting securityJob = new JobPosting();
            securityJob.setTitle("Night Watchman for Festival");
            securityJob.setDescription("Temporary security guard needed for the 3-day Jatre.");
            securityJob.setWageType("Per Day");
            securityJob.setAmount(500.00);
            securityJob.setShop(melaCommittee); // Link it to the Committee

            jobPostingRepository.saveAll(List.of(deliveryJob, securityJob));

            // --- 3. CREATE INVESTOR OPPORTUNITIES ---

            InvestorOpportunity stallSpace = new InvestorOpportunity();
            stallSpace.setTitle("10x10 Commercial Stall at Jatre");
            stallSpace.setDescription("Prime location near the main temple entrance. High footfall guaranteed.");
            stallSpace.setOpportunityType("COMMERCIAL_SPACE");
            stallSpace.setInvestmentAmount(15000.00);
            stallSpace.setStartDate(LocalDate.now().plusDays(10));
            stallSpace.setEndDate(LocalDate.now().plusDays(15));
            stallSpace.setIssuer(melaCommittee); // Link to the Committee

            investorRepository.save(stallSpace);

            System.out.println("✅ Seeding complete! Database is now populated.");
        } else {
            System.out.println("⚡ Database already contains data. Skipping seeder.");
        }
    }
}