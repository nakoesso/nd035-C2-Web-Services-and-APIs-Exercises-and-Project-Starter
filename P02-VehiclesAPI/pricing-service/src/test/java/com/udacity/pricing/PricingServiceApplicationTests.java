package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
	}

	/**
	 * Test that the application successfully retrieves a price for a valid vehicle ID
	 */
	@Test
	public void testGetPriceForValidVehicleId() throws PriceException {
		Long validVehicleId = 1L;

		// Get the price for a valid vehicle ID
		Price price = PricingService.getPrice(validVehicleId);

		// Verify the price is not null and has the correct vehicle ID
		assertNotNull(price, "Price should not be null");
		assertEquals(validVehicleId, price.getVehicleId(), "Vehicle ID should match");
		assertNotNull(price.getPrice(), "Price value should not be null");
		assertNotNull(price.getCurrency(), "Currency should not be null");
		assertEquals("USD", price.getCurrency(), "Currency should be USD");
	}

	/**
	 * Test that the application throws an exception for an invalid vehicle ID
	 */
	@Test
	public void testGetPriceForInvalidVehicleId() {
		Long invalidVehicleId = 999L;

		// Verify that PriceException is thrown for an invalid vehicle ID
		assertThrows(PriceException.class, () -> {
			PricingService.getPrice(invalidVehicleId);
		}, "PriceException should be thrown for invalid vehicle ID");
	}

	/**
	 * Test that multiple valid vehicle IDs return prices with the correct structure
	 */
	@Test
	public void testGetPriceForMultipleValidVehicleIds() throws PriceException {
		// Test prices for vehicles 1-5
		for (long vehicleId = 1; vehicleId <= 5; vehicleId++) {
			Price price = PricingService.getPrice(vehicleId);

			assertNotNull(price, "Price should not be null for vehicle ID " + vehicleId);
			assertEquals(vehicleId, price.getVehicleId(), "Vehicle ID should match");
			assertTrue(price.getPrice().signum() > 0, "Price should be positive");
			assertEquals("USD", price.getCurrency(), "Currency should be USD");
		}
	}

	/**
	 * Test REST endpoint - retrieve price for valid vehicle ID via HTTP
	 */
	@Test
	public void testGetPriceEndpointWithValidVehicleId() {
		Long validVehicleId = 1L;

		// Call the REST endpoint
		ResponseEntity<Price> response = restTemplate.getForEntity(
				"/services/price?vehicleId=" + validVehicleId,
				Price.class
		);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be 200 OK");
		assertNotNull(response.getBody(), "Response body should not be null");
		assertEquals(validVehicleId, response.getBody().getVehicleId(), "Vehicle ID should match");
		assertEquals("USD", response.getBody().getCurrency(), "Currency should be USD");
	}

	/**
	 * Test REST endpoint - verify 404 error for invalid vehicle ID
	 */
	@Test
	public void testGetPriceEndpointWithInvalidVehicleId() {
		Long invalidVehicleId = 999L;

		// Call the REST endpoint with an invalid vehicle ID
		ResponseEntity<Price> response = restTemplate.getForEntity(
				"/services/price?vehicleId=" + invalidVehicleId,
				Price.class
		);

		// Verify the response returns a 404 Not Found status
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response status should be 404 Not Found");
	}

}
