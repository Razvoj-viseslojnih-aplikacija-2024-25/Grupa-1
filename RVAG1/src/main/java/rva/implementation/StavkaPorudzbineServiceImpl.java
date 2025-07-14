package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;
import rva.repository.StavkaPorudzbineRepository;
import rva.services.StavkaPorudzbineService;

@Component
public class StavkaPorudzbineServiceImpl implements StavkaPorudzbineService {

	@Autowired
	private StavkaPorudzbineRepository repo;
	
	@Override
	public List<StavkaPorudzbine> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}
	
	@Override
	public Optional<StavkaPorudzbine> findById(int id){
		return repo.findById(id);
	}

	@Override
	public StavkaPorudzbine create(StavkaPorudzbine t) {
		return repo.save(t);
	}

	@Override
	public Optional<StavkaPorudzbine> update(StavkaPorudzbine t, int id) {
		if(existsById(id)) {
			t.setId(id);
			return Optional.of(repo.save(t));
		}
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);

	}

	@Override
	public List<StavkaPorudzbine> getStavkasByCenaLargerOrEqualsThan(double cena) {
		return repo.findByCenaGreaterThanEqual(cena);
	}

	@Override
	public List<StavkaPorudzbine> getStavkasByArtikl(Artikl artikl) {
		return repo.findByArtikl(artikl);
	}

	@Override
	public List<StavkaPorudzbine> getStavkasByPorudzbina(Porudzbina porudzbina) {
		return repo.findByPorudzbina(porudzbina);
	}

}
