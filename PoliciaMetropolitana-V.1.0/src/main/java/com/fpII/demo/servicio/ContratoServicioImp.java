package com.fpII.demo.servicio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.entidad.Vigilante;
import com.fpII.demo.repositorio.ContratoRepo;

@Service
public class ContratoServicioImp implements ContratoServicio {

	@Autowired
	ContratoRepo contratoRepo;
	
	@Override
	public List<Contrato> listarContratos() {
		return (List<Contrato>) contratoRepo.findAll();
	}

	@Override
	public Contrato buscarPorId(Long id) {
		return contratoRepo.findById(id).orElse(null);
	}
	
	@Override
	public Contrato buscarPorVigilante(Vigilante vigilante) {
		return contratoRepo.findFirstByVigilante(vigilante);
	}
	
	@Override
	public Contrato buscarPorVigilanteYFecha(Vigilante vigilante, Date f_contrato) {
		return contratoRepo.findByVigilanteAndFechacontrato(vigilante, f_contrato);
	}
	
	@Override
	public Contrato buscarPorSucursal(Sucursal sucursal) {
		return contratoRepo.findFirstBySucursal(sucursal);
	}

	@Override
	public void guardar(Contrato contrato) {
		contratoRepo.save(contrato);
	}

	@Override
	public void eliminar(Long id) {
		contratoRepo.deleteById(id);
	}
}
