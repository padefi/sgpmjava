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
import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Persona;
import com.fpII.demo.servicio.BandaServicio;
import com.fpII.demo.servicio.DelincuenteServicio;
import com.fpII.demo.servicio.DelitoServicio;
import com.fpII.demo.servicio.PersonaServicio;

@Controller
@RequestMapping("/menu_personas")
public class DelincuenteControlador {
	
	@Autowired
	private DelincuenteServicio delincuenteServ;
	
	@Autowired
	private BandaServicio bandaServ;
	
	@Autowired
	private PersonaServicio personaServ;
	
	@Autowired
	DelitoServicio delitoServ;
	
	@GetMapping("/delincuentes/delincuentes")
	public String listarDelincuentes(ModelMap model) {
		
		List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
		model.addAttribute("delincuentes", listadoDelincuentes);
		
		return "menu/menu_personas/delincuentes/delincuentes";
	}
	
	@GetMapping("/delincuentes/agregar")
	public String agregar(ModelMap model) {
		
		List<Banda> listadoBandas = bandaServ.listarBandas();
		model.addAttribute("bandas", listadoBandas);
		
		return "menu/menu_personas/delincuentes/agregar_delincuente";
	}
	
	@PostMapping("/delincuentes/agregar/guardar_delincuente")
	public String crearDelincuente(@RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "banda") Long id_banda, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		Delincuente delincuente_existente = null;
		Banda banda = bandaServ.buscarPorId(id_banda);
		
		if(persona_existente != null) {
		
			delincuente_existente = delincuenteServ.buscarPorPersona(persona_existente);
		}
		
		if(delincuente_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "El delincuente " + apellido + ", " + nombre + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			return "redirect:/menu_personas/delincuentes/agregar";
			
		}else if(persona_existente != null && delincuente_existente == null){
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			return "redirect:/menu_personas/delincuentes/agregar";
			
		}else{

			Persona persona_nueva = new Persona();
					persona_nueva.setApellido(apellido);
					persona_nueva.setNombre(nombre);
			
			personaServ.guardar(persona_nueva);
			
			Delincuente delincuente_nuevo = new Delincuente();
			delincuente_nuevo.setPersona(persona_nueva);
			delincuente_nuevo.setBanda(banda);
			
			delincuenteServ.guardar(delincuente_nuevo);
			
			banda.setCant_miembros(banda.getCant_miembros()+1);
			bandaServ.guardar(banda);
					
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			model.addAttribute("delincuentes", listadoDelincuentes);
			redirectAttrs.addFlashAttribute("mensaje", "Delincuente agregado con exito");
			return "redirect:/menu_personas/delincuentes/delincuentes";
		}
	}
	
	@GetMapping("/delincuentes/eliminar/{id}/{id_banda}")
	public String eliminarDelincuente(@PathVariable("id") Long id_delincuente,@PathVariable("id_banda") Long id_banda, ModelMap model, RedirectAttributes redirectAttrs) {
				
		Delincuente delincuente = delincuenteServ.buscarPorId(id_delincuente);
		Delito delito = delitoServ.buscarPorDelincuente(delincuente);
		
		if(delito != null) {
			
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			model.addAttribute("delincuentes", listadoDelincuentes);
			redirectAttrs.addFlashAttribute("mensaje", "El delincuente " + delincuente.getPersona().getApellido() + ", " + delincuente.getPersona().getNombre() + " no puede ser eliminado ya que fue apresado en un delito");
			
			return "redirect:/menu_personas/delincuentes/delincuentes";
			
		}else {
			
			Banda banda = bandaServ.buscarPorId(id_banda);

			delincuenteServ.eliminar(id_delincuente);
			personaServ.eliminar(delincuente.getPersona().getId());
			
			banda.setCant_miembros(banda.getCant_miembros()-1);
			bandaServ.guardar(banda);
			
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			model.addAttribute("delincuentes", listadoDelincuentes);
			redirectAttrs.addFlashAttribute("mensaje", "Delincuente eliminado con exito");
			
			return "redirect:/menu_personas/delincuentes/delincuentes";
			
		}	
	}
	
	@GetMapping("/delincuentes/editar/{id}")
	public String editar(@PathVariable("id") Long id_delincuente, ModelMap model, RedirectAttributes redirectAttrs) {
			
		Delincuente delincuente = delincuenteServ.buscarPorId(id_delincuente);
		List<Banda> listadoBandas = bandaServ.listarBandas();
		
		model.addAttribute("bandas", listadoBandas);
		model.addAttribute("delincuente", delincuente);
		
		return "menu/menu_personas/delincuentes/editar_delincuente";
	}
	
	@PostMapping("/delincuentes/editar/guardar_delincuente")
	public String editarDelincuente(@RequestParam(name = "id_delincuente") Long id_delincuente, @RequestParam("banda") Long id_banda, @RequestParam(name = "id_persona") Long id_persona, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		
		if(persona_existente != null && persona_existente.getId() != id_persona) {
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			return "redirect:/menu_personas/jueces/jueces";
			
		}else{
						
			Persona persona_modificar = personaServ.buscarPorId(id_persona);
			Delincuente delincuente_modificar = delincuenteServ.buscarPorId(id_delincuente);
			Banda banda = bandaServ.buscarPorId(id_banda);
			
			if(id_banda != delincuente_modificar.getBanda().getId()) {
				banda.setCant_miembros(banda.getCant_miembros()+1);
				bandaServ.guardar(banda);
				Banda banda_anterior = bandaServ.buscarPorId(delincuente_modificar.getBanda().getId());
				banda_anterior.setCant_miembros(banda_anterior.getCant_miembros()-1);
				bandaServ.guardar(banda_anterior);
			}
			
			persona_modificar.setApellido(apellido);
			persona_modificar.setNombre(nombre);
			personaServ.guardar(persona_modificar);
			
			delincuente_modificar.setBanda(banda);
			delincuenteServ.guardar(delincuente_modificar);
					
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			model.addAttribute("delincuentes", listadoDelincuentes);
			redirectAttrs.addFlashAttribute("mensaje", "Delincuente modificado con exito");
			return "redirect:/menu_personas/delincuentes/delincuentes";
		}
	}
}
