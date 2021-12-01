package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Usuario;

public interface UsuarioServicio {
	
	public List<Usuario> listarUsuarios();
	public void guardar(Usuario usuario);
	public Usuario buscarPorId(Long id);
	public Usuario buscarPorUsuario(String usuario);
	public void eliminar(Long id);
}
