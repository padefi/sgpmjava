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

import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.servicio.EntidadBancariaServicio;
import com.fpII.demo.servicio.SucursalServicio;

@Controller
@RequestMapping("/menu_bancos")
public class EntidadBancariaControlador {
	
	@Autowired
	private EntidadBancariaServicio entidadBancariaServ;
	
	@Autowired
	private SucursalServicio sucursalServ;
	
	@GetMapping("/entidades/entidades")
	public String listarEntidades(ModelMap model) {
		
		List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
		model.addAttribute("entidades", listadoEntidades);
		
		return "menu/menu_bancos/entidades/entidades";
	}
	
	@GetMapping("/entidades/agregar")
	public String agregar(ModelMap model) {
				
		return "menu/menu_bancos/entidades/agregar_entidad";
	}
	
	@PostMapping("/entidades/agregar/guardar_entidad")
	public String crearEntidad(@RequestParam(name = "entidad") String entidad, @RequestParam(name = "domicilio") String domicilio, ModelMap model, RedirectAttributes redirectAttrs) {
		
		EntidadBancaria entidades_existente = entidadBancariaServ.buscarPorNombre(entidad);
		
		if(entidades_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "La entidad " + entidad + " ya existe.");
			redirectAttrs.addFlashAttribute("entidad", entidad);
			redirectAttrs.addFlashAttribute("domicilio", domicilio);
			return "redirect:/menu_bancos/entidades/agregar";
			
		}else{

			EntidadBancaria entidad_nueva = new EntidadBancaria();
			
			entidad_nueva.setDescripcion(entidad);
			entidad_nueva.setDomicilio(domicilio);
			entidadBancariaServ.guardar(entidad_nueva);
					
			List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
			model.addAttribute("entidades", listadoEntidades);
			redirectAttrs.addFlashAttribute("mensaje", "Entidad creada con exito");
			
			return "redirect:/menu_bancos/entidades/entidades";
		}
	}
	
	@GetMapping("/entidades/eliminar/{id}")
	public String eliminarEntidad(@PathVariable("id") Long id_entidad, ModelMap model, RedirectAttributes redirectAttrs) {
					
		EntidadBancaria entidad = entidadBancariaServ.buscarPorId(id_entidad);
		Sucursal sucursal = sucursalServ.buscarPorEntidad(entidad);
		
		if(sucursal != null) {
			
			List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
			model.addAttribute("entidades", listadoEntidades);
			redirectAttrs.addFlashAttribute("mensaje", "La entidad " + entidad.getDescripcion() + " no puede ser eliminada ya que cuenta con sucursales.");
		}else {
		
		entidadBancariaServ.eliminar(id_entidad);
		
		List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
		model.addAttribute("entidades", listadoEntidades);
		redirectAttrs.addFlashAttribute("mensaje", "Entidad eliminada con exito");
		}
		
		return "redirect:/menu_bancos/entidades/entidades";
	}
	
	@GetMapping("/entidades/editar/{id}")
	public String editar(@PathVariable("id") Long id_entidad, ModelMap model, RedirectAttributes redirectAttrs) {
			
		EntidadBancaria entidad = entidadBancariaServ.buscarPorId(id_entidad);
		
		model.addAttribute("entidad", entidad);
		
		return "menu/menu_bancos/entidades/editar_entidad";
	}
	
	@PostMapping("/entidades/editar/guardar_entidad")
	public String editarBanda(@RequestParam(name = "id_entidad") Long id_entidad, @RequestParam("entidad") String entidad, @RequestParam(name = "domicilio") String domicilio, ModelMap model, RedirectAttributes redirectAttrs) {
		
		EntidadBancaria entidad_existente = entidadBancariaServ.buscarPorNombre(entidad);
		EntidadBancaria entidad_modificar = entidadBancariaServ.buscarPorId(id_entidad);
		
		if(entidad_existente != null && entidad_existente.getDescripcion() != entidad_modificar.getDescripcion()) {
			redirectAttrs.addFlashAttribute("mensaje", "La entidad " + entidad + " ya existe.");
			return "redirect:/menu_bancos/entidades/entidades";
			
		}else{
										
			entidad_modificar.setDescripcion(entidad);
			entidad_modificar.setDomicilio(domicilio);
			entidadBancariaServ.guardar(entidad_modificar);
					
			List<EntidadBancaria> listadoEntidades = entidadBancariaServ.listarEntidades();
			model.addAttribute("entidades", listadoEntidades);
			
			return "redirect:/menu_bancos/entidades/entidades";
		}
	}
}
