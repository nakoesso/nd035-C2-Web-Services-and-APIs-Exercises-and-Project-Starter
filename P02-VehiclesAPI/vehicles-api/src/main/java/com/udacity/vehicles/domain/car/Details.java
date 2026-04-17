package com.udacity.vehicles.domain.car;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Declares the additional detail variables for each Car object,
 * along with related methods for access and setting.
 */
@Embeddable
@Schema(name = "Details", description = "Technical specifications and details of the vehicle")
public class Details {
    @NotBlank
    @Schema(description = "Body style of the vehicle", example = "sedan")
    private String body;
    @NotBlank
    @Schema(description = "Model name of the vehicle", example = "Impala")
    private String model;
    @NotNull
    @ManyToOne
    @Schema(description = "Manufacturer information")
    private Manufacturer manufacturer;
    @Schema(description = "Number of doors", example = "4")
    private Integer numberOfDoors;
    @Schema(description = "Type of fuel the vehicle uses", example = "Gasoline")
    private String fuelType;
    @Schema(description = "Engine specifications", example = "3.6L V6")
    private String engine;
    @Schema(description = "Current mileage of the vehicle", example = "32280")
    private Integer mileage;
    @Schema(description = "Year the model was introduced", example = "2018")
    private Integer modelYear;
    @Schema(description = "Year the vehicle was produced", example = "2018")
    private Integer productionYear;
    @Schema(description = "External color of the vehicle", example = "white")
    private String externalColor;
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }
    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public Integer getMileage() {
        return mileage;
    }
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    public Integer getModelYear() {
        return modelYear;
    }
    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }
    public Integer getProductionYear() {
        return productionYear;
    }
    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }
    public String getExternalColor() {
        return externalColor;
    }
    public void setExternalColor(String externalColor) {
        this.externalColor = externalColor;
    }
}