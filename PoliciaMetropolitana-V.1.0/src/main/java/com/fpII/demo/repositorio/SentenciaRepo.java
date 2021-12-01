package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Sentencia;

public interface SentenciaRepo extends CrudRepository<Sentencia, Long> {
	
	Sentencia findByDelito(Delito delito);
	Sentencia findFirstByJuez(Juez juez);
}
