package com.udacity.vehicles.api;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
@Tag(name = "Cars", description = "API for managing vehicles")
class CarController {
    private final CarService carService;
    private final CarResourceAssembler assembler;
    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }
    /**
     * Retrieves a list of all available vehicles.
     * @return list of vehicles
     */
    @GetMapping
    @Operation(summary = "Get all vehicles", description = "Retrieves a paginated list of all available vehicles in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vehicles",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    CollectionModel<EntityModel<Car>> list() {
        List<EntityModel<Car>> resources = carService.list().stream().map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }
    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID", description = "Retrieves detailed information for a specific vehicle including pricing and location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    EntityModel<Car> get(
            @Parameter(description = "Vehicle ID", required = true, example = "1")
            @PathVariable Long id) {
        Car car = carService.findById(id);
        return assembler.toModel(car);
    }
    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle record with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    ResponseEntity<?> post(
            @Parameter(description = "Vehicle details", required = true)
            @Valid @RequestBody Car car) throws URISyntaxException {
        Car savedCar = carService.save(car);
        EntityModel<Car> resource = assembler.toModel(savedCar);
        return ResponseEntity.created(new URI(resource.getRequiredLink("self").getHref())).body(resource);
    }
    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a vehicle", description = "Updates an existing vehicle record with new information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    ResponseEntity<?> put(
            @Parameter(description = "Vehicle ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated vehicle details", required = true)
            @Valid @RequestBody Car car) {
        car.setId(id);
        Car updatedCar = carService.save(car);
        EntityModel<Car> resource = assembler.toModel(updatedCar);
        return ResponseEntity.ok(resource);
    }
    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle", description = "Removes a vehicle record from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    ResponseEntity<?> delete(
            @Parameter(description = "Vehicle ID", required = true, example = "1")
            @PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}