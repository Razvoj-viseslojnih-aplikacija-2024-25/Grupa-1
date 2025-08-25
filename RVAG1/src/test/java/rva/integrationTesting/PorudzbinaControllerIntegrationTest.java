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

import rva.models.Porudzbina;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PorudzbinaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int getHighestId() {
		int highestId = 0;
		ResponseEntity<List<Porudzbina>> response = 
				template.exchange("/porudzbinas", HttpMethod.GET, null, new ParameterizedTypeReference<List<Porudzbina>>() {});
		
		if(response.getBody().isEmpty()) return highestId;
		for(Porudzbina a: response.getBody()) {
			if(highestId < a.getId()) {
				highestId = a.getId();
			}
		}
		return highestId;
		
	}

	@Test
	@Order(1)
	void testGetAllPorudzbinas() {
		ResponseEntity<List<Porudzbina>> response = 
				template.exchange("/porudzbinas", HttpMethod.GET, null, new ParameterizedTypeReference<List<Porudzbina>>() {});
		
		assertEquals(200, response.getStatusCode().value());
//		assertNotNull(response.getBody());
		assertTrue(!response.getBody().isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetPorudzbinasByPlaceno() {
		boolean placeno = true;
		ResponseEntity<List<Porudzbina>> response = 
				template.exchange("/porudzbina/placeno/" + placeno, HttpMethod.GET, null, new ParameterizedTypeReference<List<Porudzbina>>() {});
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(!response.getBody().isEmpty());
		assertTrue(response.getBody().get(0).isPlaceno());
	}
	
	@Test
	@Order(3)
	void testGetPorudzbinaById() {
		int id = 1;
		ResponseEntity<Porudzbina> response = null;
		try {
			response = 
					template.exchange("/porudzbina/id/" + id, HttpMethod.GET, null, Porudzbina.class);
			
		} catch (RestClientException e) {
			fail("No data found with primary key: " + id);
		}
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(4)
	void testGetPorudzbineByDobavljac() {
		int dobavljacId = 1;
		ResponseEntity<List<Porudzbina>> response = template.exchange("/porudzbina/dobavljac/" + dobavljacId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Porudzbina>>(){});
		int statusCode = response.getStatusCode().value();
		List<Porudzbina> porudzbinas =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(porudzbinas.get(0));
		for(Porudzbina p: porudzbinas) {
			assertTrue(p.getDobavljac().getId() == 1);
		}
	}
	
	@Test
	@Order(5)
	void testCreatePorudzbina() {
		Porudzbina porudzbina = new Porudzbina();
		porudzbina.setIznos(2025);
		porudzbina.setPlaceno(true);
		
		HttpEntity<Porudzbina> entity = new HttpEntity<Porudzbina>(porudzbina);
		
		ResponseEntity<Porudzbina> response = 
				template.exchange("/porudzbina", HttpMethod.POST, entity, Porudzbina.class);
		int highestId = getHighestId();
		
		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getHeaders().getLocation());
		assertEquals("/porudzbina/id/" + highestId, response.getHeaders().getLocation().toString());
		assertEquals(porudzbina.getIznos(), response.getBody().getIznos());
		assertEquals(porudzbina.isPlaceno(), response.getBody().isPlaceno());
		
	}
	
	@Test
	@Order(6)
	void testUpdatePorudzbina() {
		Porudzbina porudzbina = new Porudzbina();
		porudzbina.setIznos(2025);
		porudzbina.setPlaceno(true);
		
		int highestId = getHighestId();
		HttpEntity<Porudzbina> entity = new HttpEntity<Porudzbina>(porudzbina);
		ResponseEntity<Porudzbina> response = 
				template.exchange("/porudzbina/" + highestId, HttpMethod.PUT, entity, Porudzbina.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(porudzbina.getIznos(), response.getBody().getIznos());
		assertEquals(porudzbina.isPlaceno(), response.getBody().isPlaceno());
	}
	
	@Test
	@Order(7)
	void testDeletePorudzbina() {
		int highestId = getHighestId();
		ResponseEntity<String> response = 
				template.exchange("/porudzbina/" + highestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(String.format("Entity with id: %s has been deleted", highestId), response.getBody());
		
		ResponseEntity<String> responseGet = 
				template.exchange("/porudzbina/id/" + highestId, HttpMethod.GET, null, String.class);
		
		assertEquals(404, responseGet.getStatusCode().value());
		assertEquals(String.format("Resource with id: %s does not exist", highestId), responseGet.getBody());
	}

}
