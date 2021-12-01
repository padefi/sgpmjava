package com.fpII.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;

public interface SucursalRepo extends CrudRepository<Sucursal, Long>{

	Sucursal findByDescripcion(String sucursal);
	Sucursal findByDescripcionAndEntidad(String sucursal,EntidadBancaria entidad);
	Sucursal findFirstByEntidad(EntidadBancaria entidad);
	@Query(nativeQuery = true, value="SELECT * FROM sucursales WHERE id_entidad = ?1")
	List<Sucursal> buscarSentenciaPorIdEntidad(Long id_entidad);
}