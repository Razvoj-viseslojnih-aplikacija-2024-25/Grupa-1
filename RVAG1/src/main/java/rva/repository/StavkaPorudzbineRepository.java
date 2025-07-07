package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

public interface StavkaPorudzbineRepository extends JpaRepository<StavkaPorudzbine, Integer> {
	
	List<StavkaPorudzbine> findByCenaGreaterThanEqual (double cena);

	List<StavkaPorudzbine> findByArtikl(Artikl artikl);
	
	List<StavkaPorudzbine> findByPorudzbina(Porudzbina porudzbina);
}
