package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.TipoUsuario;

public interface TipoUsuarioServicio {

	public List<TipoUsuario> listarTipoUsuario();
	public TipoUsuario buscarPorId(Long id);
}
