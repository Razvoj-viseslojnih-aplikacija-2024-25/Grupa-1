package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.models.Dobavljac;
import rva.models.Porudzbina;

public interface PorudzbinaRepository extends JpaRepository<Porudzbina,Integer> {

	List<Porudzbina> findByPlacenoEquals(boolean placeno);
	
	List<Porudzbina> findByDobavljac(Dobavljac dobavljac);
}
