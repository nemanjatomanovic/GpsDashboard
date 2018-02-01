package com.gps.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gps.Radnik.Radnik;
import com.gps.Repository.RadnikRepository;
import com.vaadin.server.VaadinSession;

@Service
public class RadnikService {

	private final RadnikRepository radnikRep;
	
	@Autowired
	public RadnikService(RadnikRepository radnikRep){
		this.radnikRep = radnikRep;
	}
	

	public Radnik findRadnik(){
		return  radnikRep.findOne(Long.valueOf(VaadinSession.getCurrent().getAttribute("Id").toString()));
	}
	
	public Radnik save(Radnik r){
		return radnikRep.saveAndFlush(r);
	}
	public Radnik findOne(Long id){
		return radnikRep.findOne(id);
	}
	
	public void delete(Radnik r){
		radnikRep.delete(r);
	}
	
	public int sacuvajSifru(String pass, Long id){
		return radnikRep.setLozinka(pass, id);
	}
    public Integer count() {
        return Math.toIntExact(radnikRep.count());
    }
}
