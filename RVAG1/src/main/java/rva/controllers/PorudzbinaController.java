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

import rva.implementation.DobavljacServiceImpl;
import rva.implementation.PorudzbinaServiceImpl;
import rva.models.Dobavljac;
import rva.models.Porudzbina;

@RestController
public class PorudzbinaController {

	@Autowired
	private PorudzbinaServiceImpl service;
	
	@Autowired
	private DobavljacServiceImpl dobavljacService;
	
	@GetMapping("/porudzbinas")
	public ResponseEntity<?> getAllPorudzbinas(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/porudzbina/placeno/{placeno}")
	public ResponseEntity<?> getPorudzbinasByNaziv(@PathVariable boolean placeno){
		List<Porudzbina> porudzbinas = service.getPorudzbinasByPlaceno(placeno);
		if(porudzbinas.isEmpty()) 
			if(placeno) {
				return new ResponseEntity<String>(String.format("No porudzbinas were paid"), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<String>(String.format("No porudzbinas are pending payment"), HttpStatus.NOT_FOUND);
			}
		return ResponseEntity.ok(porudzbinas);
	}
	
	@GetMapping("/porudzbina/id/{id}")
	public ResponseEntity<?> getPorudzbinaById(@PathVariable int id){
		Optional<Porudzbina> porudzbina = service.findById(id);
		if(porudzbina.isEmpty()) 
			return new ResponseEntity<String>(String.format("Resource with id: %s does not exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(porudzbina.get());
	}
	
	@PostMapping("/porudzbina")
	public ResponseEntity<?> createPorudzbina(@RequestBody Porudzbina porudzbina){
		if(service.existsById(porudzbina.getId())) 
			return new ResponseEntity<String>(String.format("Entity with id: %s already exists", porudzbina.getId()), HttpStatus.CONFLICT);
		Porudzbina createdPorudzbina = service.create(porudzbina);
		URI uri = URI.create("/porudzbina/id/" + createdPorudzbina.getId());
		return ResponseEntity.created(uri).body(createdPorudzbina);
	}
	
	@PutMapping("/porudzbina/{id}")
	public ResponseEntity<?> updatePorudzbina(@PathVariable int id, @RequestBody Porudzbina porudzbina){
		Optional<Porudzbina> updatedPorudzbina = service.update(porudzbina, id);
		if(updatedPorudzbina.isEmpty())
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(updatedPorudzbina.get());
	}
	
	@DeleteMapping("/porudzbina/{id}")
	public ResponseEntity<?> deletePorudzbina(@PathVariable int id){
		if(!service.existsById(id))
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		service.delete(id);
		return ResponseEntity.ok(String.format("Entity with id: %s has been deleted", id));
	}
	
	@GetMapping("/porudzbina/dobavljac/{foreignKey}")
	public ResponseEntity<?> getPorudzbinasByDobavljac(@PathVariable int foreignKey){
		Optional<Dobavljac> dobavljac = dobavljacService.findById(foreignKey);
		if(dobavljac.isEmpty())
			return new ResponseEntity<String>(String.format("Dobavljac with id: %s doesnt exist",foreignKey), HttpStatus.NOT_FOUND);
		List<Porudzbina> porudzbine = service.getPorudzbinasByDobavljac(dobavljac.get());
		if(porudzbine.isEmpty())
			return new ResponseEntity<String>(String.format("No entities with foreign key: %S",foreignKey), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(porudzbine);
	}
}
