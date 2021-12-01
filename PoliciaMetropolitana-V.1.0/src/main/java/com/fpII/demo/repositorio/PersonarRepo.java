package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Persona;

public interface PersonarRepo extends CrudRepository<Persona, Long> {
	
	Persona findByNombre(String nombre);
	Persona findByApellido(String apellido);
	Persona findByApellidoAndNombre(String apellido, String nombre);
}