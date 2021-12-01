package com.fpII.demo.servicio;


import com.fpII.demo.entidad.Persona;

public interface PersonaServicio {

	public Persona buscarPorNombre(String nombre);
	public Persona buscarPorApellido(String apellido);
	public Persona buscarPorApellidoYNombre(String apellido, String nombre);
	public Persona buscarPorId(Long id);
	public void guardar(Persona persona);
	public void eliminar(Long id);
}