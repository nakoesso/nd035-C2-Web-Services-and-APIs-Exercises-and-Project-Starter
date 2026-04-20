package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PriceRepository priceRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDataLoaded() {
		long count = priceRepository.count();
		assertEquals(19, count, "Should have 19 prices loaded from data.sql");
	}

	@Test
	public void testFindPriceById() {
		Price price = priceRepository.findById(1L).orElse(null);
		assertNotNull(price);
		assertEquals("USD", price.getCurrency());
		assertNotNull(price.getPrice());
	}

	@Test
	public void testGetPricesEndpoint() throws Exception {
		mockMvc.perform(get("/prices"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetSinglePriceEndpoint() throws Exception {
		mockMvc.perform(get("/prices/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.price").isNumber())
				.andExpect(jsonPath("$.vehicleId").value(1));
	}

	@Test
	public void testGetNonExistentPrice() throws Exception {
		mockMvc.perform(get("/prices/999"))
				.andExpect(status().isNotFound());
	}
}
