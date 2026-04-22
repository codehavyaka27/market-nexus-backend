package com.localbridge.market_nexus.controller;

import com.localbridge.market_nexus.entity.Shop;
import com.localbridge.market_nexus.repository.ShopRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {

    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    // 1. A basic endpoint to get all shops
    @GetMapping
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    // 2. The Spatial Query! (This is what you demo to professors)
    // Example URL: /api/shops/nearby?longitude=77.6411&latitude=12.9718&radius=5000
    @GetMapping("/nearby")
    public List<Shop> getNearbyShops(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius) {
        return shopRepository.findShopsWithinRadius(longitude, latitude, radius);
    }
}