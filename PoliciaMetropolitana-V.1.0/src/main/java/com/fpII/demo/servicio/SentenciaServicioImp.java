package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Sentencia;
import com.fpII.demo.repositorio.DelitoRepo;
import com.fpII.demo.repositorio.SentenciaRepo;

@Service
public class SentenciaServicioImp implements SentenciaServicio {

	@Autowired
	SentenciaRepo servicioRepo;
	
	@Autowired
	DelitoRepo DelitoRepo;
	
	@Override
	public List<Sentencia> listarSentencias() {
		return (List<Sentencia>) servicioRepo.findAll();
	}
	
	@Override
	public Sentencia buscarPorId(Long id) {
		return servicioRepo.findById(id).orElse(null);
	}

	@Override
	public Sentencia buscarPorDelito(Delito delito) {
		return servicioRepo.findByDelito(delito);
	}
	
	@Override
	public Sentencia buscarPorJuez(Juez juez) {
		return servicioRepo.findFirstByJuez(juez);
	}

	@Override
	public void guardar(Sentencia sentencia) {
		servicioRepo.save(sentencia);
	}

	@Override
	public void eliminar(Long id) {
		servicioRepo.deleteById(id);
	}
}
