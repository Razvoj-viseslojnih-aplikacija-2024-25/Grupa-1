package rva.integrationTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

import rva.models.Artikl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtiklControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template;
	
	int getHighestId() {
		int highestId = 0;
		ResponseEntity<List<Artikl>> response = 
				template.exchange("/artikls", HttpMethod.GET, null, new ParameterizedTypeReference<List<Artikl>>() {});
		
		if(response.getBody().isEmpty()) return highestId;
		for(Artikl a: response.getBody()) {
			if(highestId < a.getId()) {
				highestId = a.getId();
			}
		}
		return highestId;
		
	}

	@Test
	@Order(1)
	void testGetAllArtikls() {
		ResponseEntity<List<Artikl>> response = 
				template.exchange("/artikls", HttpMethod.GET, null, new ParameterizedTypeReference<List<Artikl>>() {});
		
		assertEquals(200, response.getStatusCode().value());
//		assertNotNull(response.getBody());
		assertTrue(!response.getBody().isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetArtiklsByNaziv() {
		String naziv = "Zlat";
		ResponseEntity<List<Artikl>> response = 
				template.exchange("/artikl/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Artikl>>() {});
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(!response.getBody().isEmpty());
		assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
	}
	
	@Test
	@Order(3)
	void testGetArtiklById() {
		int id = 1;
		ResponseEntity<Artikl> response = null;
		try {
			response = 
					template.exchange("/artikl/id/" + id, HttpMethod.GET, null, Artikl.class);
			
		} catch (RestClientException e) {
			fail("No data found with primary key: " + id);
		}
		
		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(4)
	void testCreateArtikl() {
		Artikl artikl = new Artikl();
		artikl.setNaziv("POST TEST");
		artikl.setProizvodjac("RVA 2025");
		
		HttpEntity<Artikl> entity = new HttpEntity<Artikl>(artikl);
		
		ResponseEntity<Artikl> response = 
				template.exchange("/artikl", HttpMethod.POST, entity, Artikl.class);
		int highestId = getHighestId();
		
		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getHeaders().getLocation());
		assertEquals("/artikl/id/" + highestId, response.getHeaders().getLocation().toString());
		assertEquals(artikl.getNaziv(), response.getBody().getNaziv());
		assertEquals(artikl.getProizvodjac(), response.getBody().getProizvodjac());
		
	}
	
	@Test
	@Order(5)
	void testUpdateArtikl() {
		Artikl artikl = new Artikl();
		artikl.setNaziv("PUT TEST");
		artikl.setProizvodjac("RVA 2025 - azuriranje");
		
		int highestId = getHighestId();
		HttpEntity<Artikl> entity = new HttpEntity<Artikl>(artikl);
		ResponseEntity<Artikl> response = 
				template.exchange("/artikl/" + highestId, HttpMethod.PUT, entity, Artikl.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(artikl.getNaziv(), response.getBody().getNaziv());
	}
	
	@Test
	@Order(6)
	void testDeleteArtikl() {
		int highestId = getHighestId();
		ResponseEntity<String> response = 
				template.exchange("/artikl/" + highestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(String.format("Entity with id: %s has been deleted", highestId), response.getBody());
		
		ResponseEntity<String> responseGet = 
				template.exchange("/artikl/id/" + highestId, HttpMethod.GET, null, String.class);
		
		assertEquals(404, responseGet.getStatusCode().value());
		assertEquals(String.format("Resource with id: %s does not exist", highestId), responseGet.getBody());
	}
	
	

}
