package com.udacity.vehicles.domain.manufacturer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Declares class to hold car manufacturer information.
 */
@Entity
@Schema(name = "Manufacturer", description = "Vehicle manufacturer information")
public class Manufacturer {
    @Id
    @Schema(description = "Manufacturer code", example = "101")
    private Integer code;
    @Schema(description = "Manufacturer name", example = "Chevrolet")
    private String name;
    public Manufacturer() { }
    public Manufacturer(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    public Integer getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}