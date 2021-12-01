package com.fpII.demo.servicio;

import java.sql.Date;
import java.util.List;


import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.entidad.Vigilante;

public interface ContratoServicio {

	public List<Contrato> listarContratos();
	public Contrato buscarPorId(Long id);
	public Contrato buscarPorVigilante(Vigilante vigilante);
	public Contrato buscarPorVigilanteYFecha(Vigilante vigilante,Date f_contrato);
	public Contrato buscarPorSucursal(Sucursal sucursal);
	public void guardar(Contrato contrato);
	public void eliminar(Long id);
}
