package com.udacity.vehicles.domain;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Available values for condition of a given car.
 */
@Schema(enumAsRef = true)
public enum Condition {
    USED,
    NEW;
}