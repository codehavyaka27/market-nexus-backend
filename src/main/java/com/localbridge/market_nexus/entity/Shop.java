package com.localbridge.market_nexus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity
@Table(name = "shops")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    // 1. Tell Jackson to ignore the complex math object so it doesn't crash
    @JsonIgnore
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point location;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    // 2. Add this "Fake Getter" to send clean, readable coordinates to your frontend!
    @JsonProperty("coordinates")
    public String getCoordinates() {
        if (this.location != null) {
            // Returns standard "Latitude, Longitude" format for Google Maps/React
            return this.location.getY() + ", " + this.location.getX();
        }
        return null;
    }

    // 3. Stop the infinite loop! Tell Jackson to stop reading when it hits these lists.
    @JsonIgnoreProperties("shop")
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPosting> jobPostings;

    @JsonIgnore
    @OneToMany(mappedBy = "issuer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestorOpportunity> investorOpportunities;

    // Helper method to make saving coordinates easy in our seeder
    public void setLocation(double longitude, double latitude) {
        org.locationtech.jts.geom.GeometryFactory factory = new org.locationtech.jts.geom.GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(), 4326);
        this.location = factory.createPoint(new org.locationtech.jts.geom.Coordinate(longitude, latitude));
    }
}