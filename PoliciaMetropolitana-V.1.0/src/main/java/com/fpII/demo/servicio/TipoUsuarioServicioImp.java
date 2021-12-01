package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.TipoUsuario;
import com.fpII.demo.repositorio.TipoUsuarioRepo;

@Service
public class TipoUsuarioServicioImp implements TipoUsuarioServicio {
	
	@Autowired
	private TipoUsuarioRepo tipoUsuarioRepo;
	
	@Override
	public List<TipoUsuario> listarTipoUsuario() {
		return (List<TipoUsuario>) tipoUsuarioRepo.findAll();
	}

	@Override
	public TipoUsuario buscarPorId(Long id) {
		return tipoUsuarioRepo.findById(id).orElse(null);
	}

}