package com.udacity.vehicles.domain.car;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import java.time.LocalDateTime;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Declares the Car class, related variables and methods.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Schema(name = "Car", description = "A vehicle managed by the Vehicles API")
public class Car {
    @Id
    @GeneratedValue
    @Schema(description = "The unique identifier for the car", example = "1")
    private Long id;
    @CreatedDate
    @Schema(description = "The timestamp when the car record was created")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Schema(description = "The timestamp when the car record was last modified")
    private LocalDateTime modifiedAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "The condition of the vehicle", example = "USED", allowableValues = {"NEW", "USED"})
    private Condition condition;
    @Valid
    @Embedded
    @Schema(description = "Detailed specifications of the vehicle")
    private Details details = new Details();
    @Valid
    @Embedded
    @Schema(description = "The geographic location of the vehicle")
    private Location location = new Location(0d, 0d);
    @Transient
    @Schema(description = "The current market price of the vehicle", example = "$25,000.00")
    private String price;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    public Condition getCondition() {
        return condition;
    }
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {
        this.details = details;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}