package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Banda;

public interface BandaServicio {
	
	public List<Banda> listarBandas();
	public Banda buscarPorId(Long id);
	public Banda buscarPorNombre(String banda);
	public void guardar(Banda banda);
	public void eliminar(Long id);
}
