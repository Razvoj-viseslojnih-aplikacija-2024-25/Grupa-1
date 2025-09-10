package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.implementation.DobavljacServiceImpl;
import rva.models.Dobavljac;

@CrossOrigin
@RestController
public class DobavljacController {

	@Autowired
	private DobavljacServiceImpl service;
	
	@GetMapping("/dobavljacs")
	public ResponseEntity<?> getAllDobavljacs(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/dobavljac/naziv/{naziv}")
	public ResponseEntity<?> getDobavljacsByNaziv(@PathVariable String naziv){
		List<Dobavljac> dobavljacs = service.getDobavljacsByNaziv(naziv);
		if(dobavljacs.isEmpty()) return new ResponseEntity<String>(String.format("No dobavljac's exists with naziv: %s", naziv), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(dobavljacs);
	}
	
	@GetMapping("/dobavljac/id/{id}")
	public ResponseEntity<?> getDobavljacById(@PathVariable int id){
		Optional<Dobavljac> dobavljac = service.findById(id);
		if(dobavljac.isEmpty()) 
			return new ResponseEntity<String>(String.format("Resource with id: %s does not exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(dobavljac.get());
	}
	
	@PostMapping("/dobavljac")
	public ResponseEntity<?> createDobavljac(@RequestBody Dobavljac dobavljac){
		if(service.existsById(dobavljac.getId())) 
			return new ResponseEntity<String>(String.format("Entity with id: %s already exists", dobavljac.getId()), HttpStatus.CONFLICT);
		Dobavljac createdDobavljac = service.create(dobavljac);
		URI uri = URI.create("/dobavljac/id/" + createdDobavljac.getId());
		return ResponseEntity.created(uri).body(createdDobavljac);
	}
	
	@PutMapping("/dobavljac/{id}")
	public ResponseEntity<?> updateDobavljac(@PathVariable int id, @RequestBody Dobavljac dobavljac){
		Optional<Dobavljac> updatedDobavljac = service.update(dobavljac, id);
		if(updatedDobavljac.isEmpty())
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(updatedDobavljac.get());
	}
	
	@DeleteMapping("/dobavljac/{id}")
	public ResponseEntity<?> deleteDobavljac(@PathVariable int id){
		if(!service.existsById(id))
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		service.delete(id);
		return ResponseEntity.ok(String.format("Entity with id: %s has been deleted", id));
	}
}
