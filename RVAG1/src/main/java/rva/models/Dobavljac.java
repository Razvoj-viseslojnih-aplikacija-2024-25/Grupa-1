package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Dobavljac implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DOBAVLJAC_ID_GEN", sequenceName = "DOBAVLJAC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOBAVLJAC_ID_GEN")
	private int id;
	private String naziv;
	private String kontakt;
	private String adresa;
	
	@OneToMany(mappedBy = "dobavljac")
	@JsonIgnore
	private List<Porudzbina> porudzbine;
	
	public Dobavljac() {
		super();
	}
	public Dobavljac(int id, String naziv, String kontakt, String adresa) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.kontakt = kontakt;
		this.adresa = adresa;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getKontakt() {
		return kontakt;
	}
	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public List<Porudzbina> getPorudzbine() {
		return porudzbine;
	}
	public void setPorudzbine(List<Porudzbina> porudzbine) {
		this.porudzbine = porudzbine;
	}
	
	
	
}
