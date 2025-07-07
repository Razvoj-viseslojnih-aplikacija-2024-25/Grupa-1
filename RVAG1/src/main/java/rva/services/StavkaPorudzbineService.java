package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

@Service
public interface StavkaPorudzbineService extends CrudService<StavkaPorudzbine> {

	List<StavkaPorudzbine> getStavkasByCenaLargerOrEqualsThan (double cena);

	List<StavkaPorudzbine> getStavkasByArtikl(Artikl artikl);
	
	List<StavkaPorudzbine> getStavkasByPorudzbina(Porudzbina porudzbina);
}
