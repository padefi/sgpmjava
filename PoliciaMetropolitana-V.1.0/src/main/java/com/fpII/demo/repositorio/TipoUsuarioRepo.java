package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.TipoUsuario;

public interface TipoUsuarioRepo extends CrudRepository<TipoUsuario, Long> {

}
