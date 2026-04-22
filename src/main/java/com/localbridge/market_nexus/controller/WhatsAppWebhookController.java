package com.localbridge.market_nexus.controller;

import com.localbridge.market_nexus.entity.JobPosting;
import com.localbridge.market_nexus.entity.Shop;
import com.localbridge.market_nexus.repository.JobPostingRepository;
import com.localbridge.market_nexus.repository.ShopRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class WhatsAppWebhookController {

    private final ShopRepository shopRepository;
    private final JobPostingRepository jobPostingRepository;

    public WhatsAppWebhookController(ShopRepository shopRepository, JobPostingRepository jobPostingRepository) {
        this.shopRepository = shopRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    // Twilio sends data as form-urlencoded, not standard JSON!
    @PostMapping(value = "/whatsapp", produces = MediaType.APPLICATION_XML_VALUE)
    public String handleIncomingWhatsApp(
            @RequestParam("From") String sender,
            @RequestParam("Body") String messageBody) {

        // Twilio formats numbers like "whatsapp:+919876543210". We just want the number.
        String cleanPhone = sender.replace("whatsapp:", "");

        System.out.println("📩 Received WhatsApp from: " + cleanPhone);
        System.out.println("💬 Message: " + messageBody);

        // 1. Check if this phone number belongs to a registered shop
        Shop shop = shopRepository.findByPhoneNumber(cleanPhone);

        if (shop == null) {
            return generateTwilioResponse("Sorry, this number is not registered as a Shop on MarketNexus.");
        }

        // 2. Parse the message. Let's assume the owner texts: "JOB, Dishwasher, 400"
        try {
            String[] parts = messageBody.split(",");
            if (parts[0].trim().equalsIgnoreCase("JOB") && parts.length == 3) {

                String jobTitle = parts[1].trim();
                Double wage = Double.parseDouble(parts[2].trim());

                // 3. Save the new job to the database!
                JobPosting newJob = new JobPosting();
                newJob.setTitle(jobTitle);
                newJob.setDescription("Urgent requirement posted via WhatsApp.");
                newJob.setWageType("Per Day");
                newJob.setAmount(wage);
                newJob.setShop(shop);

                jobPostingRepository.save(newJob);

                return generateTwilioResponse("✅ Success! Your job for '" + jobTitle + "' paying ₹" + wage + " has been posted live on the map.");
            } else {
                return generateTwilioResponse("⚠️ Invalid format. Please text: JOB, [Job Title], [Daily Wage]");
            }
        } catch (Exception e) {
            return generateTwilioResponse("❌ An error occurred. Please make sure your wage is a number.");
        }
    }

    // Helper method to format the reply exactly how WhatsApp/Twilio expects it (TwiML XML)
    private String generateTwilioResponse(String textMessage) {
        return "<Response><Message>" + textMessage + "</Message></Response>";
    }
}