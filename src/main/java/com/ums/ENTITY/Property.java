package com.ums.ENTITY;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id") //FR keys TO JOIN THE TABLES TO LOCATION ENTITY
    private Location location; //NOT IN THE SET<> DUE TO ONE

    @ManyToOne
    @JoinColumn(name = "country_id")// FR keys TO JOIN THE TABLES TO COUNTRY ENTITY
    private Country country; //NOT IN THE SET<> DUE TO ONE

    @Column(name = "property_name", nullable = false, length = 200)
    private String propertyName;

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "beds", nullable = false)
    private Integer beds;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

}