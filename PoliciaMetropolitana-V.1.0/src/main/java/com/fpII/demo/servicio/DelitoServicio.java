package com.fpII.demo.servicio;

import java.sql.Date;
import java.util.List;

import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Sucursal;

public interface DelitoServicio {
	
	public List<Delito> listarDelitos();
	public List<Delito> listarDelitosSinSentencia();
	public List<Delito> buscarDelitosPorIdDelincuente(Long id_delincuente);
	public Delito buscarPorId(Long id);
	public Delito buscarPorDelincuenteSucursalYFecha(Delincuente delincuente, Sucursal sucursal,Date f_contrato);
	public Delito buscarPorDelincuente(Delincuente delincuente);
	public void guardar(Delito delito);
	public void eliminar(Long id);
}
