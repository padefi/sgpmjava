package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Banda;
import com.fpII.demo.repositorio.BandaRepo;

@Service
public class BandaServicioImp implements BandaServicio {
	
	@Autowired
	BandaRepo bandaRepo;

	@Override
	public List<Banda> listarBandas() {
		return (List<Banda>) bandaRepo.findAll();
	}

	@Override
	public Banda buscarPorId(Long id) {
		return bandaRepo.findById(id).orElse(null);
	}

	@Override
	public void guardar(Banda banda) {
		bandaRepo.save(banda);
	}

	@Override
	public void eliminar(Long id) {
		bandaRepo.deleteById(id);
	}

	@Override
	public Banda buscarPorNombre(String banda) {
		return bandaRepo.findByDescripcion(banda);
	}
}
