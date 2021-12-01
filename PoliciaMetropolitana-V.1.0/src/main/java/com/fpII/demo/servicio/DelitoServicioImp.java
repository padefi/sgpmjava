package com.fpII.demo.servicio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.repositorio.DelitoRepo;

@Service
public class DelitoServicioImp implements DelitoServicio {

	@Autowired
	DelitoRepo delitoRepo;
	
	@Override
	public List<Delito> listarDelitos() {
		return (List<Delito>) delitoRepo.findAll();
	}
	
	@Override
	public List<Delito> buscarDelitosPorIdDelincuente(Long id_delincuente) {
		return (List<Delito>) delitoRepo.buscarDelitosPorDelincuente(id_delincuente);
	}
	
	@Override
	public List<Delito> listarDelitosSinSentencia() {
		return (List<Delito>) delitoRepo.buscarDelitosSinSentencia();
	}

	@Override
	public Delito buscarPorId(Long id) {
		return delitoRepo.findById(id).orElse(null);
	}
	
	@Override
	public Delito buscarPorDelincuenteSucursalYFecha(Delincuente delincuente, Sucursal sucursal,
			Date f_contrato) {
		return delitoRepo.findByDelincuenteAndSucursalAndFechadelito(delincuente, sucursal, f_contrato);
	}
	
	@Override
	public Delito buscarPorDelincuente(Delincuente delincuente) {
		return delitoRepo.findFirstByDelincuente(delincuente);
	}

	@Override
	public void guardar(Delito delito) {
		delitoRepo.save(delito);
	}

	@Override
	public void eliminar(Long id) {
		delitoRepo.deleteById(id);
	}
}
