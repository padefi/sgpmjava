package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Usuario;
import com.fpII.demo.repositorio.UsuarioRepo;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Override
	public List<Usuario> listarUsuarios() {
		return (List<Usuario>) usuarioRepo.findAll(); 
	}

	@Override
	public void guardar(Usuario usuario) {
		usuarioRepo.save(usuario);
	}

	@Override
	public Usuario buscarPorId(Long id) {
		return usuarioRepo.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		usuarioRepo.deleteById(id);
	}

	@Override
	public Usuario buscarPorUsuario(String usuario) {
		return usuarioRepo.findByUsuario(usuario);
	}
}
