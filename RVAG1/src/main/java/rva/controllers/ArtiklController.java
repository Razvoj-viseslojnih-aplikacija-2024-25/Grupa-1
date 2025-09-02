package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.implementation.ArtiklServiceImpl;
import rva.models.Artikl;

@CrossOrigin
@RestController
public class ArtiklController {
	
	@Autowired
	private ArtiklServiceImpl service;
	
	@GetMapping("/artikls")
	public ResponseEntity<?> getAllArtikls(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/artikl/naziv/{naziv}")
	public ResponseEntity<?> getArtiklsByNaziv(@PathVariable String naziv){
		List<Artikl> artikls = service.getArtiklsByNaziv(naziv);
		if(artikls.isEmpty()) return new ResponseEntity<String>(String.format("No artikls exists with naziv: %s", naziv), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(artikls);
	}
	
	@GetMapping("/artikl/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<Artikl> artikl = service.findById(id);
		if(artikl.isEmpty()) 
			return new ResponseEntity<String>(String.format("Resource with id: %s does not exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(artikl.get());
	}
	
	@PostMapping("/artikl")
	public ResponseEntity<?> createArtikl(@RequestBody Artikl artikl){
		if(service.existsById(artikl.getId())) 
			return new ResponseEntity<String>(String.format("Entity with id: %s already exists", artikl.getId()), HttpStatus.CONFLICT);
		Artikl createdArtikl = service.create(artikl);
		URI uri = URI.create("/artikl/id/" + createdArtikl.getId());
		return ResponseEntity.created(uri).body(createdArtikl);
	}
	
	@PutMapping("/artikl/{id}")
	public ResponseEntity<?> updateArtikl(@PathVariable int id, @RequestBody Artikl artikl){
		Optional<Artikl> updatedArtikl = service.update(artikl, id);
		if(updatedArtikl.isEmpty())
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(updatedArtikl.get());
	}
	
	@DeleteMapping("/artikl/{id}")
	public ResponseEntity<?> deleteArtikl(@PathVariable int id){
		if(!service.existsById(id))
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		service.delete(id);
		return ResponseEntity.ok(String.format("Entity with id: %s has been deleted", id));
	}
}
