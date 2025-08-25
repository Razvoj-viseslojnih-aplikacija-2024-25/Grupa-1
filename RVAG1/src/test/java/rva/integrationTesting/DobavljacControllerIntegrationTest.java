package rva.integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import rva.models.Dobavljac;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DobavljacControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int getHighestId() {
		int highestId = 0;
		ResponseEntity<List<Dobavljac>> response = 
				template.exchange("/dobavljacs", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dobavljac>>() {});
		
		if(response.getBody().isEmpty()) return highestId;
		for(Dobavljac a: response.getBody()) {
			if(highestId < a.getId()) {
				highestId = a.getId();
			}
		}
		return highestId;
		
	}

	@Test
	@Order(1)
	void testGetAllDobavljacs() {
		ResponseEntity<List<Dobavljac>> response = 
				template.exchange("/dobavljacs", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dobavljac>>() {});
		
		assertEquals(200, response.getStatusCode().value());
//		assertNotNull(response.getBody());
		assertTrue(!response.getBody().isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetDobavljacsByNaziv() {
		String naziv = "Fruit";
		ResponseEntity<List<Dobavljac>> response = 
				template.exchange("/dobavljac/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Dobavljac>>() {});
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(!response.getBody().isEmpty());
		assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
	}
	
	@Test
	@Order(3)
	void testGetDobavljacById() {
		int id = 1;
		ResponseEntity<Dobavljac> response = null;
		try {
			response = 
					template.exchange("/dobavljac/id/" + id, HttpMethod.GET, null, Dobavljac.class);
			
		} catch (RestClientException e) {
			fail("No data found with primary key: " + id);
		}
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(4)
	void testCreateDobavljac() {
		Dobavljac dobavljac = new Dobavljac();
		dobavljac.setNaziv("POST TEST");
		dobavljac.setAdresa("Test Adresa 2025");
		
		HttpEntity<Dobavljac> entity = new HttpEntity<Dobavljac>(dobavljac);
		
		ResponseEntity<Dobavljac> response = 
				template.exchange("/dobavljac", HttpMethod.POST, entity, Dobavljac.class);
		int highestId = getHighestId();
		
		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getHeaders().getLocation());
		assertEquals("/dobavljac/id/" + highestId, response.getHeaders().getLocation().toString());
		assertEquals(dobavljac.getNaziv(), response.getBody().getNaziv());
		assertEquals(dobavljac.getAdresa(), response.getBody().getAdresa());
		
	}
	
	@Test
	@Order(5)
	void testUpdateDobavljac() {
		Dobavljac dobavljac = new Dobavljac();
		dobavljac.setNaziv("PUT TEST");
		dobavljac.setAdresa("Test Adresa 2025 - azuriranje");
		
		int highestId = getHighestId();
		HttpEntity<Dobavljac> entity = new HttpEntity<Dobavljac>(dobavljac);
		ResponseEntity<Dobavljac> response = 
				template.exchange("/dobavljac/" + highestId, HttpMethod.PUT, entity, Dobavljac.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(dobavljac.getNaziv(), response.getBody().getNaziv());
		assertEquals(dobavljac.getAdresa(), response.getBody().getAdresa());
	}
	
	@Test
	@Order(6)
	void testDeleteDobavljac() {
		int highestId = getHighestId();
		ResponseEntity<String> response = 
				template.exchange("/dobavljac/" + highestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(String.format("Entity with id: %s has been deleted", highestId), response.getBody());
		
		ResponseEntity<String> responseGet = 
				template.exchange("/dobavljac/id/" + highestId, HttpMethod.GET, null, String.class);
		
		assertEquals(404, responseGet.getStatusCode().value());
		assertEquals(String.format("Resource with id: %s does not exist", highestId), responseGet.getBody());
	}

}
