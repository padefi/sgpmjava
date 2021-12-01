package com.fpII.demo.repositorio;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.entidad.Vigilante;

public interface ContratoRepo extends CrudRepository<Contrato, Long> {
	
	Contrato findFirstByVigilante(Vigilante vigilante);
	Contrato findByVigilanteAndFechacontrato(Vigilante vigilante, Date f_contrato);
	Contrato findFirstBySucursal(Sucursal sucursal);
}