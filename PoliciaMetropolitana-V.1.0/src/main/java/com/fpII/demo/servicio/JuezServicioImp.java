package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Persona;
import com.fpII.demo.repositorio.JuezRepo;

@Service
public class JuezServicioImp implements JuezServicio {

	@Autowired
	JuezRepo juezRepo;
	
	@Override
	public void guardar(Juez juez) {
		juezRepo.save(juez);
	}

	@Override
	public void eliminar(Long id) {
		juezRepo.deleteById(id);
	}

	@Override
	public List<Juez> listarJueces() {
		return (List<Juez>) juezRepo.findAll();
	}

	@Override
	public Juez buscarPorPersona(Persona juez) {
		return juezRepo.findByPersona(juez);
	}

	@Override
	public Juez buscarPorId(Long id) {
		return juezRepo.findById(id).orElse(null);
	}

}
