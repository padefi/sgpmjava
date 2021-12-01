package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Usuario;
import com.fpII.demo.entidad.Vigilante;

public interface VigilanteRepo extends CrudRepository<Vigilante, Long> {
	
	Vigilante findByUsuario(Usuario usuario);
}