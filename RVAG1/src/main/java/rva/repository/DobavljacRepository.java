package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.models.Dobavljac;

public interface DobavljacRepository extends JpaRepository<Dobavljac, Integer>{

	List<Dobavljac> findByNazivContainingIgnoreCase(String naziv);
}
