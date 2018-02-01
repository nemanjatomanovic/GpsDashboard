package com.gps.Radnik;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the korisniksistema database table.
 * 
 */
@Entity
@Table(name="korisniksistema")
@NamedQuery(name="Radnik.findAll", query="SELECT r FROM Radnik r")
public class Radnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName="korisniksistema_seq", name="korisniksistema_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="korisniksistema_seq")
	private Long id;

	private String ime;
	private String korisnickoime;
	private String lozinka;
	private String prezime;

	
	public Radnik(){
		
	}
	
	


	public Radnik(Long id, String ime, String korisnickoime, String lozinka, String prezime) {
		super();
		this.id = id;
		this.ime = ime;
		this.korisnickoime = korisnickoime;
		this.lozinka = lozinka;
		this.prezime = prezime;
	}

	



	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getIme() {
		return ime;
	}




	public void setIme(String ime) {
		this.ime = ime;
	}




	public String getKorisnickoime() {
		return korisnickoime;
	}




	public void setKorisnickoime(String korisnickoime) {
		this.korisnickoime = korisnickoime;
	}




	public String getLozinka() {
		return lozinka;
	}




	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}




	public String getPrezime() {
		return prezime;
	}




	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



	@Override
	public String toString(){
		return this.getId().toString() + this.getKorisnickoime() + this.getIme()+this.getLozinka() + this.getPrezime();
	}
}