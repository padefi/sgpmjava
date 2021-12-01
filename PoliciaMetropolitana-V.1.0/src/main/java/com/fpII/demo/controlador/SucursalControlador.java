package com.fpII.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.servicio.ContratoServicio;
import com.fpII.demo.servicio.EntidadBancariaServicio;
import com.fpII.demo.servicio.SucursalServicio;

@Controller
@RequestMapping("/menu_bancos")
public class SucursalControlador {

	@Autowired
	private SucursalServicio sucursalServ;
	
	@Autowired
	private EntidadBancariaServicio entidadBancariaServ;
	
	@Autowired
	private ContratoServicio contratoServ;
	
	@GetMapping("/sucursales/sucursales")
	public String listarSucursales(ModelMap model) {
		
		List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
		model.addAttribute("sucursales", listadoSucursales);
		
		return "menu/menu_bancos/sucursales/sucursales";
	}
	
	@GetMapping("/sucursales/agregar")
	public String agregar(ModelMap model) {
		
		List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
		model.addAttribute("entidades", listadoEntidades);
				
		return "menu/menu_bancos/sucursales/agregar_sucursal";
	}
	
	@PostMapping("/sucursales/agregar/guardar_sucursal")
	public String crearSucursal(@RequestParam(name = "sucursal") String sucursal, @RequestParam(name = "domicilio") String domicilio, @RequestParam(name = "cant_empleados") int cant_empleados, @RequestParam(name = "entidad") Long id_entidad, ModelMap model, RedirectAttributes redirectAttrs) {
		
		EntidadBancaria entidad = entidadBancariaServ.buscarPorId(id_entidad);
		Sucursal sucursal_existente = sucursalServ.buscarPorNombreYEntidad(sucursal,entidad);
		
		if(sucursal_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "La sucursal " + sucursal + " ya existe en la entidad bancaria " + sucursal_existente.getEntidad().getDescripcion() + ".");
			redirectAttrs.addFlashAttribute("sucursal", sucursal);
			redirectAttrs.addFlashAttribute("domicilio", domicilio);
			redirectAttrs.addFlashAttribute("cant_empleados", cant_empleados);
			return "redirect:/menu_bancos/sucursales/agregar";
			
		}else{
				
			Sucursal sucursal_nueva = new Sucursal();
			
			sucursal_nueva.setDescripcion(sucursal);
			sucursal_nueva.setDomicilio(domicilio);
			sucursal_nueva.setCant_empleados(cant_empleados);
			sucursal_nueva.setEntidad(entidad);
			sucursalServ.guardar(sucursal_nueva);
					
			List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
			model.addAttribute("sucursales", listadoSucursales);
			redirectAttrs.addFlashAttribute("mensaje", "Sucursal creada con exito");
			
			return "redirect:/menu_bancos/sucursales/sucursales";
		}
	}
	
	@GetMapping("/sucursales/eliminar/{id}")
	public String eliminarSucursal(@PathVariable("id") Long id_sucursal, ModelMap model, RedirectAttributes redirectAttrs) {
					
		Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
		Contrato contrato = contratoServ.buscarPorSucursal(sucursal);
		
		if(contrato != null) {
			
			List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
			model.addAttribute("sucursales", listadoSucursales);
			redirectAttrs.addFlashAttribute("mensaje", "La sucursal " + sucursal.getDescripcion() + " no puede ser eliminada debido a que tiene/tuvo un contrato.");			
		}else{
		
		sucursalServ.eliminar(id_sucursal);
		
		List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
		model.addAttribute("sucursales", listadoSucursales);
		redirectAttrs.addFlashAttribute("mensaje", "Sucursal eliminada con exito");
		}
		
		return "redirect:/menu_bancos/sucursales/sucursales";
	}
	
	@GetMapping("/sucursales/editar/{id}")
	public String editar(@PathVariable("id") Long id_sucursal, ModelMap model, RedirectAttributes redirectAttrs) {
			
		Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
		
		List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
		model.addAttribute("entidades", listadoEntidades);
		model.addAttribute("sucursal", sucursal);
		
		return "menu/menu_bancos/sucursales/editar_sucursal";
	}
	
	@PostMapping("/sucursales/editar/guardar_sucursal")
	public String editarSucursal(@RequestParam(name = "id_sucursal") Long id_sucursal, @RequestParam("sucursal") String sucursal, @RequestParam(name = "domicilio") String domicilio, @RequestParam(name = "cant_empleados") int cant_empleados, @RequestParam(name = "entidad") Long id_entidad, ModelMap model, RedirectAttributes redirectAttrs) {
		
		EntidadBancaria entidad = entidadBancariaServ.buscarPorId(id_entidad);
		Sucursal sucursal_existente = sucursalServ.buscarPorNombreYEntidad(sucursal,entidad);
		Sucursal sucursal_modificar = sucursalServ.buscarPorId(id_sucursal);
		
		if(sucursal_existente != null && sucursal_existente.getDescripcion() != sucursal_modificar.getDescripcion()) {
			redirectAttrs.addFlashAttribute("mensaje", "La sucursal " + sucursal + " ya existe en la entidad bancaria " + sucursal_existente.getEntidad().getDescripcion() + ".");
			return "redirect:/menu_bancos/sucursales/sucursales";
			
		}else{
										
			sucursal_modificar.setDescripcion(sucursal);
			sucursal_modificar.setDomicilio(domicilio);
			sucursal_modificar.setCant_empleados(cant_empleados);
			sucursal_modificar.setEntidad(entidad);
			sucursalServ.guardar(sucursal_modificar);
					
			List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
			model.addAttribute("sucursales", listadoSucursales);
			
			return "redirect:/menu_bancos/sucursales/sucursales";
		}
	}
}
