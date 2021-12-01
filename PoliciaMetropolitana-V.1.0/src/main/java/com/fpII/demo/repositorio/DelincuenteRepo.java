package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Banda;
import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Persona;

public interface DelincuenteRepo extends CrudRepository<Delincuente, Long> {

	Delincuente findFirstByBanda(Banda banda);
	Delincuente findByPersona(Persona delincuente);
}
