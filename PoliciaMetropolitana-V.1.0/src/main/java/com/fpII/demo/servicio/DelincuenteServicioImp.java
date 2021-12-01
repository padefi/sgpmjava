package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Banda;
import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Persona;
import com.fpII.demo.repositorio.DelincuenteRepo;

@Service
public class DelincuenteServicioImp implements DelincuenteServicio {

	@Autowired
	DelincuenteRepo delincuenteRepo;
	
	@Override
	public List<Delincuente> listarDelincuentes() {
		return (List<Delincuente>) delincuenteRepo.findAll();
	}

	@Override
	public Delincuente buscarPorPersona(Persona delincuente) {
		return delincuenteRepo.findByPersona(delincuente);
	}

	@Override
	public Delincuente buscarPorId(Long id) {
		return delincuenteRepo.findById(id).orElse(null);
	}

	@Override
	public Delincuente buscarPorBanda(Banda banda) {
		return delincuenteRepo.findFirstByBanda(banda);
	}
	
	@Override
	public void guardar(Delincuente delincuente) {
		delincuenteRepo.save(delincuente);
	}

	@Override
	public void eliminar(Long id) {
		delincuenteRepo.deleteById(id);
	}
}
