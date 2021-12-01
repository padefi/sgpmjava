package com.fpII.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.repositorio.SucursalRepo;

@Service
public class SucursalServicioImp implements SucursalServicio {

	@Autowired
	SucursalRepo sucursalRepo;
	
	@Override
	public List<Sucursal> listarSucursales() {
		return (List<Sucursal>)sucursalRepo.findAll();
	}

	@Override
	public Sucursal buscarPorId(Long id) {
		return sucursalRepo.findById(id).orElse(null);
	}

	@Override
	public Sucursal buscarPorNombre(String sucursal) {
		return sucursalRepo.findByDescripcion(sucursal);
	}

	@Override
	public void guardar(Sucursal sucursal) {
		sucursalRepo.save(sucursal);
	}
	
	@Override
	public Sucursal buscarPorEntidad(EntidadBancaria entidad) {
		return sucursalRepo.findFirstByEntidad(entidad);
	}

	@Override
	public void eliminar(Long id) {
		sucursalRepo.deleteById(id);
	}

	@Override
	public Sucursal buscarPorNombreYEntidad(String sucursal, EntidadBancaria entidad) {
		return sucursalRepo.findByDescripcionAndEntidad(sucursal, entidad);
	}

	@Override
	public List<Sucursal> buscarPorIdEntidad(Long id_entidad) {
		return (List<Sucursal>)sucursalRepo.buscarSentenciaPorIdEntidad(id_entidad);
	}
}
