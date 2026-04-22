package com.localbridge.market_nexus.repository;

import com.localbridge.market_nexus.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    /**
     * This is the PostGIS Spatial Query.
     * It finds all shops within 'radiusInMeters' of a specific Longitude and Latitude.
     * ST_DWithin calculates the actual geographic distance over the curvature of the earth.
     */
    // Find a shop exactly by its registered phone number
    Shop findByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT * FROM shops s WHERE ST_DWithin(s.location\\:\\:geography, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)\\:\\:geography, :radiusInMeters)", nativeQuery = true)
    List<Shop> findShopsWithinRadius(
            @Param("longitude") double longitude,
            @Param("latitude") double latitude,
            @Param("radiusInMeters") double radiusInMeters
    );
}