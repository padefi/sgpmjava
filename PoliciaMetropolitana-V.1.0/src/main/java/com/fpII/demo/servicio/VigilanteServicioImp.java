package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Usuario;
import com.fpII.demo.entidad.Vigilante;
import com.fpII.demo.repositorio.VigilanteRepo;

@Service
public class VigilanteServicioImp implements VigilanteServicio {

	@Autowired
	private VigilanteRepo vigilanteRepo;
	
	@Override
	public List<Vigilante> listarVigilantes() {
		return (List<Vigilante>) vigilanteRepo.findAll();
	}
	
	@Override
	public Vigilante buscarPorId(Long id) {
		return vigilanteRepo.findById(id).orElse(null);
	}

	@Override
	public Vigilante buscarPorUsuario(Usuario usuario) {
		return vigilanteRepo.findByUsuario(usuario);
	}
	
	@Override
	public void guardar(Vigilante vigilante) {
		vigilanteRepo.save(vigilante);
	}

	@Override
	public void eliminar(Long id) {
		vigilanteRepo.deleteById(id);
	}
}
