package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Persona;

public interface JuezRepo extends CrudRepository<Juez, Long> {

	Juez findByPersona(Persona juez);
}
