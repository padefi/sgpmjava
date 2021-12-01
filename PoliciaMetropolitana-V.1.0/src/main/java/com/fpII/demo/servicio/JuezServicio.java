package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Persona;

public interface JuezServicio {
	
	public List<Juez> listarJueces();
	public Juez buscarPorPersona(Persona juez);
	public Juez buscarPorId(Long id);
	public void guardar(Juez juez);
	public void eliminar(Long id);
}