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

import com.fpII.demo.entidad.Banda;
import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.servicio.BandaServicio;
import com.fpII.demo.servicio.DelincuenteServicio;

@Controller
@RequestMapping("/menu_delincuencia")
public class BandaControlador {

	@Autowired
	private BandaServicio bandaServ;
	
	@Autowired
	private DelincuenteServicio delincuenteServ;
	
	@GetMapping("/bandas/bandas")
	public String listarBandas(ModelMap model) {
		
		List<Banda> listadoBandas = bandaServ.listarBandas();
		model.addAttribute("bandas", listadoBandas);
		
		return "menu/menu_delincuencia/bandas/bandas";
	}
	
	@GetMapping("/bandas/agregar")
	public String agregar(ModelMap model) {
				
		return "menu/menu_delincuencia/bandas/agregar_banda";
	}
	
	@PostMapping("/bandas/agregar/guardar_banda")
	public String crearBanda(@RequestParam(name = "banda") String banda, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Banda banda_existente = bandaServ.buscarPorNombre(banda);
		
		if(banda_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "La banda " + banda + " ya existe.");
			redirectAttrs.addFlashAttribute("banda", banda);
			return "redirect:/menu_delincuencia/bandas/agregar";
			
		}else{

			Banda banda_nueva = new Banda();
			
			banda_nueva.setDescripcion(banda);
			banda_nueva.setCant_miembros(0);
			bandaServ.guardar(banda_nueva);
					
			List<Banda> listadoBandas = bandaServ.listarBandas();
			model.addAttribute("bandas", listadoBandas);
			redirectAttrs.addFlashAttribute("mensaje", "Banda creada con exito");
			
			return "redirect:/menu_delincuencia/bandas/bandas";
		}
	}
	
	@GetMapping("/bandas/eliminar/{id}")
	public String eliminarBanda(@PathVariable("id") Long id_banda, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Banda banda = bandaServ.buscarPorId(id_banda);
		Delincuente delincuente = delincuenteServ.buscarPorBanda(banda);
		
		if(delincuente != null) {
			
			List<Banda> listadoBandas = bandaServ.listarBandas();
			model.addAttribute("bandas", listadoBandas);
			redirectAttrs.addFlashAttribute("mensaje", "La banda " + banda.getDescripcion() + " no puede eliminarse ya que cuenta con miembros.");
			
		}else {
		
		bandaServ.eliminar(id_banda);
		
		List<Banda> listadoBandas = bandaServ.listarBandas();
		model.addAttribute("bandas", listadoBandas);
		redirectAttrs.addFlashAttribute("mensaje", "Banda eliminada con exito");
		}
		
		return "redirect:/menu_delincuencia/bandas/bandas";
	}
	
	@GetMapping("/bandas/editar/{id}")
	public String editar(@PathVariable("id") Long id_banda, ModelMap model, RedirectAttributes redirectAttrs) {
			
		Banda banda = bandaServ.buscarPorId(id_banda);
		
		model.addAttribute("banda", banda);
		
		return "menu/menu_delincuencia/bandas/editar_bandas";
	}
	
	@PostMapping("/bandas/editar/guardar_banda")
	public String editarBanda(@RequestParam(name = "id_banda") Long id_banda, @RequestParam("banda") String banda, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Banda banda_existente = bandaServ.buscarPorNombre(banda);
		Banda banda_modificar = bandaServ.buscarPorId(id_banda);
		
		if(banda_existente != null && banda_existente.getDescripcion() != banda_modificar.getDescripcion()) {
			redirectAttrs.addFlashAttribute("mensaje", "La banda " + banda + " ya existe.");
			return "redirect:/menu_delincuencia/bandas/bandas";
			
		}else{
										
			banda_modificar.setDescripcion(banda);
			bandaServ.guardar(banda_modificar);
					
			List<Banda> listadoBandas = bandaServ.listarBandas();
			model.addAttribute("bandas", listadoBandas);
			
			return "redirect:/menu_delincuencia/bandas/bandas";
		}
	}
}
