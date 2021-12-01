package com.fpII.demo.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Usuario;

public interface UsuarioRepo extends CrudRepository<Usuario, Long> {

	Usuario findByUsuario(String usuario);
}
