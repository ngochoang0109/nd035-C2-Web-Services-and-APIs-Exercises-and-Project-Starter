package com.udacity.pricing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {

	}

	@Test
	public void getPriceValidId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(new URI("/services/price?vehicleId=" + 10)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void getPriceInvalidId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(new URI("/services/price?vehicleId=" + 21)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
