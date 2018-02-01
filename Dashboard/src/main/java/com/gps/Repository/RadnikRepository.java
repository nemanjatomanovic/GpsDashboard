package com.gps.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gps.Radnik.Radnik;

public interface RadnikRepository extends JpaRepository<Radnik, Long>{

	List<Radnik> findByKorisnickoimeAndLozinka(String korisnickoime, String lozinka);
	Radnik findOne(Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Radnik r SET r.lozinka=?1 WHERE r.id=?2")
	int setLozinka(String lozinka, Long id);
}
