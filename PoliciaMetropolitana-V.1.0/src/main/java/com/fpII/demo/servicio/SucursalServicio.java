package com.fpII.demo.servicio;

import java.util.List;

import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;

public interface SucursalServicio {
	
	public List<Sucursal> listarSucursales();
	public Sucursal buscarPorId(Long id);
	public Sucursal buscarPorNombre(String sucursal);
	public Sucursal buscarPorNombreYEntidad(String sucursal, EntidadBancaria entidad);
	public Sucursal buscarPorEntidad(EntidadBancaria entidad);
	public List<Sucursal> buscarPorIdEntidad(Long id_entidad);
	public void guardar(Sucursal sucursal);
	public void eliminar(Long id);
}