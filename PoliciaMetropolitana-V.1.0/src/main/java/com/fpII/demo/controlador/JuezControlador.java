package com.fpII.demo.controlador;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Persona;
import com.fpII.demo.entidad.Sentencia;
import com.fpII.demo.servicio.JuezServicio;
import com.fpII.demo.servicio.PersonaServicio;
import com.fpII.demo.servicio.SentenciaServicio;

@Controller
@RequestMapping("/menu_personas")
public class JuezControlador {
	
	@Autowired
	private JuezServicio juezServ;
	
	@Autowired
	private PersonaServicio personaServ;
	
	@Autowired
	SentenciaServicio sentenciaServ;
	
	@GetMapping("/jueces/jueces")
	public String listarJueces(ModelMap model) {
		
		List<Juez> listadoJueces = juezServ.listarJueces();
		
		for(Juez str : listadoJueces){
			Date f_inicio_actividad = str.getInicio_actividad();
			int anios = calcularAnioServicio(f_inicio_actividad);
			str.setAnios(anios);
	    }
		
		model.addAttribute("jueces", listadoJueces);
		
		return "menu/menu_personas/jueces/jueces";
	}
	
	@GetMapping("/jueces/agregar")
	public String agregar(ModelMap model) {
		
		return "menu/menu_personas/jueces/agregar_juez";
	}
	
	@PostMapping("/jueces/agregar/guardar_juez")
	public String crearJuez(@RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "inicio_actividad") Date inicio_actividad, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		Juez juez_existente = null;
		
		if(persona_existente != null) {
		
			juez_existente = juezServ.buscarPorPersona(persona_existente);
		}
		
		if(juez_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "El juez " + apellido + ", " + nombre + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			redirectAttrs.addFlashAttribute("inicio_actividad", inicio_actividad);
			return "redirect:/menu_personas/jueces/agregar";
			
		}else if(persona_existente != null && juez_existente == null){
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			redirectAttrs.addFlashAttribute("inicio_actividad", inicio_actividad);
			return "redirect:/menu_personas/jueces/agregar";
			
		}else{

			Persona persona_nueva = new Persona();
					persona_nueva.setApellido(apellido);
					persona_nueva.setNombre(nombre);
			
			personaServ.guardar(persona_nueva);
			
			
			Juez juez_nuevo = new Juez();
			juez_nuevo.setPersona(persona_nueva);
			juez_nuevo.setInicio_actividad(inicio_actividad);
			
			juezServ.guardar(juez_nuevo);
					
			List<Juez> listadoJueces = juezServ.listarJueces();
			model.addAttribute("jueces", listadoJueces);
			redirectAttrs.addFlashAttribute("mensaje", "Juez agregado con exito");
			return "redirect:/menu_personas/jueces/jueces";
		}
	}
	
	@GetMapping("/jueces/eliminar/{id}")
	public String eliminarJuez(@PathVariable("id") Long id_juez, ModelMap model, RedirectAttributes redirectAttrs) {
				
		Juez juez = juezServ.buscarPorId(id_juez);
		Sentencia sentencia = sentenciaServ.buscarPorJuez(juez);
		
		if(sentencia != null) {
			
			List<Juez> listadoJuces = juezServ.listarJueces();
			model.addAttribute("usuarios", listadoJuces);
			redirectAttrs.addFlashAttribute("mensaje", "El juez " + juez.getPersona().getApellido() + ", " + juez.getPersona().getNombre()  + " no puede ser eliminado ya que ha aplicado alguna sentencia firme.");
			
			return "redirect:/menu_personas/jueces/jueces";
			
		}else {
			
			juezServ.eliminar(id_juez);
			personaServ.eliminar(juez.getPersona().getId());
			
			List<Juez> listadoJuces = juezServ.listarJueces();
			model.addAttribute("usuarios", listadoJuces);
			redirectAttrs.addFlashAttribute("mensaje", "Juez eliminado con exito");
			
			return "redirect:/menu_personas/jueces/jueces";
		}
	}
	
	@GetMapping("/jueces/editar/{id}")
	public String editar(@PathVariable("id") Long id_juez, ModelMap model, RedirectAttributes redirectAttrs) {
				
		Juez juez = juezServ.buscarPorId(id_juez);
		model.addAttribute("juez", juez);
		
		return "menu/menu_personas/jueces/editar_juez";
	}
	
	@PostMapping("/jueces/editar/guardar_juez")
	public String editarJuez(@RequestParam(name = "id_juez") Long id_juez, @RequestParam(name = "id_persona") Long id_persona, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "inicio_actividad") Date inicio_actividad, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		
		if(persona_existente != null && persona_existente.getId() != id_persona) {
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			return "redirect:/menu_personas/jueces/jueces";
			
		}else{
						
			Persona persona_modificar = personaServ.buscarPorId(id_persona);
			Juez juez_modificar = juezServ.buscarPorId(id_juez);
			
			persona_modificar.setApellido(apellido);
			persona_modificar.setNombre(nombre);
			
			personaServ.guardar(persona_modificar);
			
			juez_modificar.setInicio_actividad(inicio_actividad);
			
			juezServ.guardar(juez_modificar);
					
			List<Juez> listadoJuces = juezServ.listarJueces();
			model.addAttribute("usuarios", listadoJuces);
			redirectAttrs.addFlashAttribute("mensaje", "Juez modificado con exito");
			return "redirect:/menu_personas/jueces/jueces";
		}
	}
	
	private int calcularAnioServicio(Date f_inicio) {
        Calendar hoy = Calendar.getInstance();
        Calendar fecha = new GregorianCalendar();
        fecha.setTime(f_inicio);
        
        int anio = hoy.get(Calendar.YEAR) -  fecha.get(Calendar.YEAR);
        int mes = hoy.get(Calendar.MONTH) - fecha.get(Calendar.MONTH);
        int dia = hoy.get(Calendar.DAY_OF_MONTH) - fecha.get(Calendar.DAY_OF_MONTH);

        //Si está en ese año pero todavía no los ha cumplido
        if (mes < 0 || (mes == 0 && dia < 0)) {
        	anio = anio - 1;
        }
        return anio;
    }
}
