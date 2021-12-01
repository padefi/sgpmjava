package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Usuario;
import com.fpII.demo.entidad.Vigilante;

public interface VigilanteServicio {
	
	public List<Vigilante> listarVigilantes();
	public Vigilante buscarPorId(Long id);
	public Vigilante buscarPorUsuario(Usuario usuario);
	public void guardar(Vigilante vigilante);
	public void eliminar(Long id);
}
