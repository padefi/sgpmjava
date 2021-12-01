package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Banda;
import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Persona;

public interface DelincuenteServicio {

	public List<Delincuente> listarDelincuentes();
	public Delincuente buscarPorPersona(Persona delincuente);
	public Delincuente buscarPorId(Long id);
	public Delincuente buscarPorBanda(Banda banda);
	public void guardar(Delincuente delincuente);
	public void eliminar(Long id);
}
