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
import rva.implementation.PorudzbinaServiceImpl;
import rva.implementation.StavkaPorudzbineServiceImpl;
import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

@CrossOrigin
@RestController
public class StavkaPorudzbineController {

	@Autowired
	private StavkaPorudzbineServiceImpl service;
	
	@Autowired
	private ArtiklServiceImpl artiklService;
	
	@Autowired
	private PorudzbinaServiceImpl porudzbinaService;
	
	@GetMapping("/stavkaPorudzbines")
	public ResponseEntity<?> getAllStavkaPorudzbines(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/stavkaPorudzbine/cena/{cena}")
	public ResponseEntity<?> getStavkaPorudzbineByCena(@PathVariable double cena){
		List<StavkaPorudzbine> stavkaPorudzbines = service.getStavkasByCenaLargerOrEqualsThan(cena);
		if(stavkaPorudzbines.isEmpty()) 
			return new ResponseEntity<String>(String.format("No stavka's found"), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(stavkaPorudzbines);
	}
	
	@GetMapping("/stavkaPorudzbine/id/{id}")
	public ResponseEntity<?> getStavkaPorudzbineById(@PathVariable int id){
		Optional<StavkaPorudzbine> stavkaPorudzbine = service.findById(id);
		if(stavkaPorudzbine.isEmpty()) 
			return new ResponseEntity<String>(String.format("Resource with id: %s does not exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(stavkaPorudzbine.get());
	}
	
	@PostMapping("/stavkaPorudzbine")
	public ResponseEntity<?> createStavkaPorudzbine(@RequestBody StavkaPorudzbine stavkaPorudzbine){
		if(service.existsById(stavkaPorudzbine.getId())) 
			return new ResponseEntity<String>(String.format("Entity with id: %s already exists", stavkaPorudzbine.getId()), HttpStatus.CONFLICT);
		StavkaPorudzbine createdStavkaPorudzbine = service.create(stavkaPorudzbine);
		URI uri = URI.create("/stavkaPorudzbine/id/" + createdStavkaPorudzbine.getId());
		return ResponseEntity.created(uri).body(createdStavkaPorudzbine);
	}
	
	@PutMapping("/stavkaPorudzbine/{id}")
	public ResponseEntity<?> updateStavkaPorudzbine(@PathVariable int id, @RequestBody StavkaPorudzbine stavkaPorudzbine){
		Optional<StavkaPorudzbine> updatedStavkaPorudzbine = service.update(stavkaPorudzbine, id);
		if(updatedStavkaPorudzbine.isEmpty())
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(updatedStavkaPorudzbine.get());
	}
	
	@DeleteMapping("/stavkaPorudzbine/{id}")
	public ResponseEntity<?> deleteStavkaPorudzbine(@PathVariable int id){
		if(!service.existsById(id))
			return new ResponseEntity<String>(String.format("Entity with id: %s doesnt exist", id), HttpStatus.NOT_FOUND);
		service.delete(id);
		return ResponseEntity.ok(String.format("Entity with id: %s has been deleted", id));
	}
	
	@GetMapping("/stavkaPorudzbine/artikl/{foreignKey}")
	public ResponseEntity<?> getStavkaPorudzbinesByArtikl(@PathVariable int foreignKey){
		Optional<Artikl> artikl = artiklService.findById(foreignKey);
		if(artikl.isEmpty())
			return new ResponseEntity<String>(String.format("Artikl with id: %s doesnt exist",foreignKey), HttpStatus.NOT_FOUND);
		List<StavkaPorudzbine> porudzbine = service.getStavkasByArtikl(artikl.get());
		if(porudzbine.isEmpty())
			return new ResponseEntity<String>(String.format("No entities with foreign key: %S",foreignKey), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(porudzbine);
	}
	
	@GetMapping("/stavkaPorudzbine/porudzbina/{foreignKey}")
	public ResponseEntity<?> getStavkaPorudzbinesByPorudzbine(@PathVariable int foreignKey){
		Optional<Porudzbina> porudzbina = porudzbinaService.findById(foreignKey);
		if(porudzbina.isEmpty())
			return new ResponseEntity<String>(String.format("Porudzbina with id: %s doesnt exist",foreignKey), HttpStatus.NOT_FOUND);
		List<StavkaPorudzbine> porudzbine = service.getStavkasByPorudzbina(porudzbina.get());
		if(porudzbine.isEmpty())
			return new ResponseEntity<String>(String.format("No entities with foreign key: %S",foreignKey), HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(porudzbine);
	}
}
