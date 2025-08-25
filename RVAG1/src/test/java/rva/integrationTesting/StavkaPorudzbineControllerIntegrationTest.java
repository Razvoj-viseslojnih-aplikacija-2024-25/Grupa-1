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

import rva.models.StavkaPorudzbine;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StavkaPorudzbineControllerIntegrationTest {


	@Autowired
	TestRestTemplate template;
	
	int getHighestId() {
		int highestId = 0;
		ResponseEntity<List<StavkaPorudzbine>> response = 
				template.exchange("/stavkaPorudzbines", HttpMethod.GET, null, new ParameterizedTypeReference<List<StavkaPorudzbine>>() {});
		
		if(response.getBody().isEmpty()) return highestId;
		for(StavkaPorudzbine a: response.getBody()) {
			if(highestId < a.getId()) {
				highestId = a.getId();
			}
		}
		return highestId;
		
	}

	@Test
	@Order(1)
	void testGetAllStavkaPorudzbines() {
		ResponseEntity<List<StavkaPorudzbine>> response = 
				template.exchange("/stavkaPorudzbines", HttpMethod.GET, null, new ParameterizedTypeReference<List<StavkaPorudzbine>>() {});
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(!response.getBody().isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetStavkaPorudzbinesByCena() {
		double cena = 100;
		ResponseEntity<List<StavkaPorudzbine>> response = 
				template.exchange("/stavkaPorudzbine/cena/" + cena, HttpMethod.GET, null, new ParameterizedTypeReference<List<StavkaPorudzbine>>() {});
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(!response.getBody().isEmpty());
		assertTrue(response.getBody().get(0).getCena() >= 100);
	}
	
	@Test
	@Order(3)
	void testGetStavkaPorudzbineById() {
		int id = 1;
		ResponseEntity<StavkaPorudzbine> response = null;
		try {
			response = 
					template.exchange("/stavkaPorudzbine/id/" + id, HttpMethod.GET, null, StavkaPorudzbine.class);
			
		} catch (RestClientException e) {
			fail("No data found with primary key: " + id);
		}
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(4)
	void testGetStavkaPorudzbinesByArtikl() {
		int artiklId = 1;
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavkaPorudzbine/artikl/" + artiklId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>(){});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkaPorudzbines =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(stavkaPorudzbines.get(0));
		for(StavkaPorudzbine p: stavkaPorudzbines) {
			assertTrue(p.getArtikl().getId() == 1);
		}
	}
	
	@Test
	@Order(5)
	void testGetStavkaPorudzbinesByPorudzbina() {
		int porudzbinaId = 1;
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavkaPorudzbine/artikl/" + porudzbinaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>(){});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkaPorudzbines =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(stavkaPorudzbines.get(0));
		for(StavkaPorudzbine p: stavkaPorudzbines) {
			assertTrue(p.getPorudzbina().getId() == 1);
		}
	}
	
	@Test
	@Order(6)
	void testCreateStavkaPorudzbine() {
		StavkaPorudzbine stavkaPorudzbine = new StavkaPorudzbine();
		stavkaPorudzbine.setCena(100);
		stavkaPorudzbine.setJedinicaMere("komad");
		
		HttpEntity<StavkaPorudzbine> entity = new HttpEntity<StavkaPorudzbine>(stavkaPorudzbine);
		
		ResponseEntity<StavkaPorudzbine> response = 
				template.exchange("/stavkaPorudzbine", HttpMethod.POST, entity, StavkaPorudzbine.class);
		int highestId = getHighestId();
		
		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getHeaders().getLocation());
		assertEquals("/stavkaPorudzbine/id/" + highestId, response.getHeaders().getLocation().toString());
		assertEquals(stavkaPorudzbine.getCena(), response.getBody().getCena());
		assertEquals(stavkaPorudzbine.getJedinicaMere(), response.getBody().getJedinicaMere());
		
	}
	
	@Test
	@Order(7)
	void testUpdateStavkaPorudzbine() {
		StavkaPorudzbine stavkaPorudzbine = new StavkaPorudzbine();
		stavkaPorudzbine.setCena(100);
		stavkaPorudzbine.setJedinicaMere("komad");
		
		int highestId = getHighestId();
		HttpEntity<StavkaPorudzbine> entity = new HttpEntity<StavkaPorudzbine>(stavkaPorudzbine);
		ResponseEntity<StavkaPorudzbine> response = 
				template.exchange("/stavkaPorudzbine/" + highestId, HttpMethod.PUT, entity, StavkaPorudzbine.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(stavkaPorudzbine.getCena(), response.getBody().getCena());
		assertEquals(stavkaPorudzbine.getJedinicaMere(), response.getBody().getJedinicaMere());
	}
	
	@Test
	@Order(8)
	void testDeleteStavkaPorudzbine() {
		int highestId = getHighestId();
		ResponseEntity<String> response = 
				template.exchange("/stavkaPorudzbine/" + highestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(String.format("Entity with id: %s has been deleted", highestId), response.getBody());
		
		ResponseEntity<String> responseGet = 
				template.exchange("/stavkaPorudzbine/id/" + highestId, HttpMethod.GET, null, String.class);
		
		assertEquals(404, responseGet.getStatusCode().value());
		assertEquals(String.format("Resource with id: %s does not exist", highestId), responseGet.getBody());
	}

}


