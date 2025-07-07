package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.models.Artikl;

public interface ArtiklRepository extends JpaRepository<Artikl,Integer>{

	List<Artikl> findByNazivContainingIgnoreCase(String naziv);
}
