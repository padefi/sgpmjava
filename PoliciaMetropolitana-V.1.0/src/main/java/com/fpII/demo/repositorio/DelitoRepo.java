package com.fpII.demo.repositorio;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.entidad.Delincuente;

public interface DelitoRepo extends CrudRepository<Delito, Long>{
	
	Delito findByDelincuenteAndSucursalAndFechadelito(Delincuente delincuente, Sucursal sucursal,Date f_contrato);
	@Query(nativeQuery = true, value="SELECT * FROM delitos WHERE id NOT IN (SELECT id_delito FROM sentencias)")
	List<Delito> buscarDelitosSinSentencia();
	@Query(nativeQuery = true, value="SELECT * FROM delitos WHERE id_delincuente = ?1 AND sentencia = 0")
	List<Delito> buscarDelitosPorDelincuente(Long id_delincuente);
	Delito findFirstByDelincuente(Delincuente delincuente);
}