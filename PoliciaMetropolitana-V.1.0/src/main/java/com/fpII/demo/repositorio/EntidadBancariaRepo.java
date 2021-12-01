package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.EntidadBancaria;

public interface EntidadBancariaRepo extends CrudRepository<EntidadBancaria, Long> {
	
	EntidadBancaria findByDescripcion(String entidad);
}
