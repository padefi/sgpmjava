package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.EntidadBancaria;

public interface EntidadBancariaServicio {

	public List<EntidadBancaria> listarEntidades();
	public EntidadBancaria buscarPorId(Long id);
	public EntidadBancaria buscarPorNombre(String entidad);
	public void guardar(EntidadBancaria entidad);
	public void eliminar(Long id);
}
