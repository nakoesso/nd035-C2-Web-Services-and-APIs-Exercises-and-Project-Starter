package com.udacity.vehicles.domain;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Stores information about a given location.
 * Latitude and longitude must be provided, while other
 * location information must be gathered each time from
 * the maps API.
 */
@Embeddable
@Schema(name = "Location", description = "Geographic location information")
public class Location {
    @NotNull
    @Schema(description = "Latitude coordinate", example = "40.730610")
    private Double lat;
    @NotNull
    @Schema(description = "Longitude coordinate", example = "-73.935242")
    private Double lon;
    @Transient
    @Schema(description = "Street address of the location")
    private String address;
    @Transient
    @Schema(description = "City name")
    private String city;
    @Transient
    @Schema(description = "State abbreviation")
    private String state;
    @Transient
    @Schema(description = "Zip code")
    private String zip;
    public Location() {
    }
    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLon() {
        return lon;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
}