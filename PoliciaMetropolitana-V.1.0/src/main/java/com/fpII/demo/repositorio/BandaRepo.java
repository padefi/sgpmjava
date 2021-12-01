package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Banda;

public interface BandaRepo extends CrudRepository<Banda, Long> {

	Banda findByDescripcion(String banda);
}
