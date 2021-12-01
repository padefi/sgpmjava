package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Sentencia;

public interface SentenciaServicio {

	public List<Sentencia> listarSentencias();
	public Sentencia buscarPorId(Long id);
	public Sentencia buscarPorDelito(Delito delito);
	public Sentencia buscarPorJuez(Juez juez);
	public void guardar(Sentencia sentencia);
	public void eliminar(Long id);
}
