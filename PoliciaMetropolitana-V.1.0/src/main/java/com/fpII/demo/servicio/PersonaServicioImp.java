package com.fpII.demo.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Persona;
import com.fpII.demo.repositorio.PersonarRepo;

@Service
public class PersonaServicioImp implements PersonaServicio{
	
	@Autowired 
	PersonarRepo personaRepo;
	
	@Override
	public Persona buscarPorNombre(String nombre){
		return personaRepo.findByNombre(nombre);
	}

	@Override
	public Persona buscarPorApellido(String apellido) {
		return personaRepo.findByApellido(apellido);
	}

	@Override
	public void guardar(Persona persona) {
		personaRepo.save(persona);		
	}

	@Override
	public Persona buscarPorApellidoYNombre(String apellido, String nombre) {
		return personaRepo.findByApellidoAndNombre(apellido, nombre);
	}

	@Override
	public void eliminar(Long id) {
		personaRepo.deleteById(id);
	}

	@Override
	public Persona buscarPorId(Long id) {
		return personaRepo.findById(id).orElse(null);
	}
}
