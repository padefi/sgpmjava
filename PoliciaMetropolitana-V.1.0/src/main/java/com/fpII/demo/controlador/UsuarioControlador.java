package com.fpII.demo.controlador;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.Persona;
import com.fpII.demo.entidad.TipoUsuario;
import com.fpII.demo.entidad.Usuario;
import com.fpII.demo.entidad.Vigilante;
import com.fpII.demo.servicio.ContratoServicio;
import com.fpII.demo.servicio.PersonaServicio;
import com.fpII.demo.servicio.TipoUsuarioServicio;
import com.fpII.demo.servicio.UsuarioServicio;
import com.fpII.demo.servicio.VigilanteServicio;

@Controller
@RequestMapping("/menu_personas")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private TipoUsuarioServicio tipoUsuarioServ;
	
	@Autowired
	private PersonaServicio personaServ;
	
	@Autowired
	private VigilanteServicio vigilanteServ;
	
	@Autowired
	private ContratoServicio contratoServ;
	
	@Autowired
	private BCryptPasswordEncoder codificador;
	
	@GetMapping("/usuarios/usuarios")
	public String listarUsuarios(ModelMap model) {
		
		List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
		model.addAttribute("usuarios", listadoUsuarios);
		
		
		return "menu/menu_personas/usuarios/usuarios";
	}
	
	@GetMapping("/usuarios/agregar")
	public String agregar(ModelMap model) {
		
		List<TipoUsuario> listaTipoUsuarios = tipoUsuarioServ.listarTipoUsuario();

		model.addAttribute("tipoUsuario",listaTipoUsuarios);
		
		return "menu/menu_personas/usuarios/agregar_usuario";
	}
	
	@PostMapping("/usuarios/agregar/guardar_usuario")
	public String crearUsuario(@RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "usuario") String usuario, @RequestParam(name = "tipo_usuario") Long id_tipo, @RequestParam(name = "fecha_nacimiento") Date f_nac, @RequestParam(name = "arma", required = false)String arma, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		Usuario usuario_existente = usuarioServ.buscarPorUsuario(usuario);
		
		
		if(persona_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			redirectAttrs.addFlashAttribute("usuario", usuario);
			return "redirect:/menu_personas/usuarios/agregar";
			
		}else if(usuario_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "El usuario " + usuario + " ya existe.");
			redirectAttrs.addFlashAttribute("apellido", apellido);
			redirectAttrs.addFlashAttribute("nombre", nombre);
			redirectAttrs.addFlashAttribute("usuario", usuario);
			return "redirect:/menu_personas/usuarios/usuarios/agregar";
			
		}else{

			Persona persona_nueva = new Persona();
					persona_nueva.setApellido(apellido);
					persona_nueva.setNombre(nombre);
			
			personaServ.guardar(persona_nueva);
			
			TipoUsuario tipo_usuario_nuevo = tipoUsuarioServ.buscarPorId(id_tipo);
									
			Usuario usuario_nuevo = new Usuario();
			usuario_nuevo.setUsuario(usuario);
			usuario_nuevo.setContrasenia(codificador.encode(usuario));
			usuario_nuevo.setPersona(persona_nueva);
			usuario_nuevo.setTipo(tipo_usuario_nuevo);
			usuario_nuevo.setEstado(1);
			
			usuarioServ.guardar(usuario_nuevo);
			
			if(id_tipo == 3) {
				
				Vigilante vigilante = new Vigilante();
				vigilante.setUsuario(usuario_nuevo);
				vigilante.setFecha_nacimiento(f_nac);
				
				int armado = 0;
				
				if(arma != null){
				    armado = 1;
				}
				
				vigilante.setArma(armado);
				vigilanteServ.guardar(vigilante);				
			}
					
			List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
			model.addAttribute("usuarios", listadoUsuarios);
			redirectAttrs.addFlashAttribute("mensaje", "Usuario creado con exito");
			return "redirect:/menu_personas/usuarios/usuarios";
		}
	}
	
	@GetMapping("/usuarios/reestablecer/{id}")
	public String editarUsuario(@PathVariable("id") Long id_usuario, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Usuario usuario = usuarioServ.buscarPorId(id_usuario);
		
		usuario.setContrasenia(codificador.encode(usuario.getUsuario()));
		usuarioServ.guardar(usuario);
		
		List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
		model.addAttribute("usuarios", listadoUsuarios);
		redirectAttrs.addFlashAttribute("mensaje", "Contraseña reestablecida con exito");
		
		return "redirect:/menu_personas/usuarios/usuarios";
	}
	
	@GetMapping("/usuarios/eliminar/{id}")
	public String eliminarUsuario(@PathVariable("id") Long id_usuario, ModelMap model, RedirectAttributes redirectAttrs) {
				
		Usuario usuario = usuarioServ.buscarPorId(id_usuario);
		Vigilante vigilante = null;
		
		if(usuario.getTipo().getId() == 3) {
			vigilante = vigilanteServ.buscarPorUsuario(usuario);
		}
		
		Contrato contrato = contratoServ.buscarPorVigilante(vigilante);
		
		if(contrato != null) {
			
			List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
			model.addAttribute("usuarios", listadoUsuarios);
			redirectAttrs.addFlashAttribute("mensaje", "El usuario " + usuario.getUsuario() + " no puede ser eliminado ya que tiene/tuvo un contrato con una sucursal.");
			return "redirect:/menu_personas/usuarios/usuarios";
		}else {

			if(vigilante != null) {
				vigilanteServ.eliminar(vigilante.getId());
			}
			
			usuarioServ.eliminar(id_usuario);
			personaServ.eliminar(usuario.getPersona().getId());
			
			List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
			model.addAttribute("usuarios", listadoUsuarios);
			redirectAttrs.addFlashAttribute("mensaje", "Usuario eliminado con exito");
			
			return "redirect:/menu_personas/usuarios/usuarios";
		}
	}
	
	@GetMapping("/usuarios/editar/{id}")
	public String editar(@PathVariable("id") Long id_usuario, ModelMap model, RedirectAttributes redirectAttrs) {
				
		Usuario usuario = usuarioServ.buscarPorId(id_usuario);
		Vigilante vigilante = vigilanteServ.buscarPorUsuario(usuario);
		
		if(vigilante != null) {
			model.addAttribute("fecha_nacimiento", vigilante.getFecha_nacimiento());
			model.addAttribute("arma", vigilante.getArma());
		}else {
			model.addAttribute("fecha_nacimiento", "");
			model.addAttribute("arma", "");
		}
		
		List<TipoUsuario> listaTipoUsuarios = tipoUsuarioServ.listarTipoUsuario();
		
		model.addAttribute("tipoUsuario",listaTipoUsuarios);
		model.addAttribute("usuario", usuario);
		return "menu/menu_personas/usuarios/editar_usuario";
	}
	
	@PostMapping("/usuarios/editar/guardar_usuario")
	public String editarUsuario(@RequestParam(name = "id_usuario") Long id_usuario, @RequestParam(name = "id_persona") Long id_persona, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "usuario") String usuario, @RequestParam(name = "tipo_usuario") Long id_tipo, @RequestParam(name = "fecha_nacimiento") Date f_nac, @RequestParam(name = "arma", required = false)String arma, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Persona persona_existente = personaServ.buscarPorApellidoYNombre(apellido, nombre);
		Usuario usuario_existente = usuarioServ.buscarPorUsuario(usuario);
		
		if(persona_existente != null && persona_existente.getId() != id_persona) {
			redirectAttrs.addFlashAttribute("mensaje", "La persona " + apellido + ", " + nombre + " ya existe.");
			return "redirect:/menu_personas/usuarios/usuarios";
			
		}else if(usuario_existente != null && usuario_existente.getId() != id_usuario) {
			redirectAttrs.addFlashAttribute("mensaje", "El usuario " + usuario + " ya existe.");
			return "redirect:/menu_personas/usuarios/usuarios";
			
		}else{
						
			Persona persona_modificar = personaServ.buscarPorId(id_persona);
			Usuario usuario_modificar = usuarioServ.buscarPorId(id_usuario);
			TipoUsuario tipo_usuario_modificar = tipoUsuarioServ.buscarPorId(id_tipo);
			
			persona_modificar.setApellido(apellido);
			persona_modificar.setNombre(nombre);
			
			personaServ.guardar(persona_modificar);
			
			usuario_modificar.setUsuario(usuario);
			usuario_modificar.setTipo(tipo_usuario_modificar);
			usuarioServ.guardar(usuario_modificar);
			
			usuarioServ.guardar(usuario_modificar);
			
			if(id_tipo == 3) {
				
				Vigilante vigilante_modificar = vigilanteServ.buscarPorUsuario(usuario_modificar);
				vigilante_modificar.setUsuario(usuario_modificar);
				vigilante_modificar.setFecha_nacimiento(f_nac);
				
				int armado = 0;
				
				if(arma != null){
				    armado = 1;
				}
				
				vigilante_modificar.setArma(armado);
				vigilanteServ.guardar(vigilante_modificar);				
			}
					
			List<Usuario> listadoUsuarios = usuarioServ.listarUsuarios();
			model.addAttribute("usuarios", listadoUsuarios);
			redirectAttrs.addFlashAttribute("mensaje", "Usuario modificado con exito");
			return "redirect:/menu_personas/usuarios/usuarios";
		}
	}
}
