package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.repositorio.EntidadBancariaRepo;

@Service
public class EntidadBancariaServicioImp implements EntidadBancariaServicio {

	@Autowired
	EntidadBancariaRepo entidadBancariaRepo;
	
	@Override
	public List<EntidadBancaria> listarEntidades() {
		return (List<EntidadBancaria>) entidadBancariaRepo.findAll();
	}

	@Override
	public EntidadBancaria buscarPorId(Long id) {
		return entidadBancariaRepo.findById(id).orElse(null);
	}

	@Override
	public EntidadBancaria buscarPorNombre(String entidad) {
		return entidadBancariaRepo.findByDescripcion(entidad);
	}

	@Override
	public void guardar(EntidadBancaria entidad) {
		entidadBancariaRepo.save(entidad);
	}

	@Override
	public void eliminar(Long id) {
		entidadBancariaRepo.deleteById(id);
	}

}
