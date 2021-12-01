package com.fpII.demo.controlador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sentencia;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.servicio.DelincuenteServicio;
import com.fpII.demo.servicio.DelitoServicio;
import com.fpII.demo.servicio.EntidadBancariaServicio;
import com.fpII.demo.servicio.SentenciaServicio;
import com.fpII.demo.servicio.SucursalServicio;

@Controller
@RequestMapping("/menu_delincuencia")
public class DelitoControlador {

	@Autowired
	DelitoServicio delitoServ;
	
	@Autowired
	DelincuenteServicio delincuenteServ;
	
	@Autowired
	EntidadBancariaServicio entidadServ;
	
	@Autowired
	SentenciaServicio sentenciaServ;
	
	@Autowired
	SucursalServicio sucursalServ;
	
	@GetMapping("/delitos/delitos")
	public String listarDelitos(ModelMap model) {
		
		List<Delito> listadoDelitos = delitoServ.listarDelitos();
		model.addAttribute("delitos", listadoDelitos);
		
		return "menu/menu_delincuencia/delitos/delitos";
	}
	
	@GetMapping("/delitos/agregar")
	public String agregar(ModelMap model) {
				
		List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
		List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
		List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
		model.addAttribute("delincuentes", listadoDelincuentes);
		model.addAttribute("entidades", listadoEntidades);
		model.addAttribute("sucursales", listadoSucursales);
		
		return "menu/menu_delincuencia/delitos/agregar_delito";
	}
	
	@PostMapping("/delitos/agregar/guardar_delitos")
	public String crearContrato(@RequestParam(name = "delincuente") Long id_delincuente, @RequestParam(name = "sucursal") Long id_sucursal, @RequestParam(name = "fecha_delito") Date f_delito, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Delincuente delincuente = delincuenteServ.buscarPorId(id_delincuente);
		Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
		Delito delito_existente = delitoServ.buscarPorDelincuenteSucursalYFecha(delincuente,sucursal,f_delito);
		String apeynom = delincuente.getPersona().getApellido() + ", " + delincuente.getPersona().getNombre();
		
		if(delito_existente != null) {
					
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
			List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
			model.addAttribute("delincuentes", listadoDelincuentes);
			model.addAttribute("entidades", listadoEntidades);
			model.addAttribute("sucursales", listadoSucursales);
			redirectAttrs.addFlashAttribute("mensaje", "El delincuente " + apeynom + " ya tiene tiene un delito ingresado por asaltar la sucursal " + sucursal.getDescripcion() + " el día " + f_delito);
			
			return "redirect:/menu_delincuencia/delitos/agregar";
			
		}else{
			
			Delito delito_nuevo = new Delito();
			
			delito_nuevo.setDelincuente(delincuente);
			delito_nuevo.setSucursal(sucursal);
			delito_nuevo.setFechadelito(f_delito);
			delitoServ.guardar(delito_nuevo);
					
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			model.addAttribute("delitos", listadoDelitos);
			redirectAttrs.addFlashAttribute("mensaje", "Delito creado con exito");
			
			return "redirect:/menu_delincuencia/delitos/delitos";
		}
	}
	
	@GetMapping("/delitos/eliminar/{id}")
	public String eliminarDelito(@PathVariable("id") Long id_delito, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Delito delito = delitoServ.buscarPorId(id_delito);
		Sentencia sentencia = sentenciaServ.buscarPorDelito(delito);
		
		if(sentencia != null) {
			
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
			model.addAttribute("delitos", listadoDelitos);
			model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede eliminar un delito que cuenta con sentencia.");
			
			return "redirect:/menu_delincuencia/delitos/delitos";
			
		}else {
			
			delitoServ.eliminar(id_delito);
			
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
			model.addAttribute("delitos", listadoDelitos);
			model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "Delito eliminado con exito");
			
			return "redirect:/menu_delincuencia/delitos/delitos";
		}
	}
	
	@GetMapping("/delitos/editar/{id}")
	public String editar(@PathVariable("id") Long id_delito, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Delito delito = delitoServ.buscarPorId(id_delito);
		Sentencia sentencia = sentenciaServ.buscarPorDelito(delito);
		
		if(sentencia != null) {
			
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
			model.addAttribute("delitos", listadoDelitos);
			model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede editar un delito que cuenta con sentencia.");
			
			return "redirect:/menu_delincuencia/delitos/delitos";
			
		}else {
			
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
			List<Sucursal> listadoSucursales = sucursalServ.buscarPorIdEntidad(delito.getSucursal().getEntidad().getId());
			model.addAttribute("delito", delito);
			model.addAttribute("delincuentes", listadoDelincuentes);
			model.addAttribute("entidades", listadoEntidades);
			model.addAttribute("sucursales", listadoSucursales);
			
			return "menu/menu_delincuencia/delitos/editar_delito";
		}
	}
	
	@PostMapping("/delitos/editar/guardar_delito")
	public String editarContrato(@RequestParam(name = "id_delito") Long id_delito,@RequestParam(name = "delincuente") Long id_delincuente, @RequestParam(name = "sucursal") Long id_sucursal, @RequestParam(name = "fecha_delito") Date f_delito, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Delito delito_actual = delitoServ.buscarPorId(id_delito);
		Delincuente delincuente = delincuenteServ.buscarPorId(id_delincuente);
		Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
		Delito delito_existente = delitoServ.buscarPorDelincuenteSucursalYFecha(delincuente,sucursal,f_delito);
		String apeynom = delincuente.getPersona().getApellido() + ", " + delincuente.getPersona().getNombre();
		
		if(delito_actual == delito_existente) {
			
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			model.addAttribute("delitos", listadoDelitos);
			
			return "redirect:/menu_delincuencia/delitos/delitos";
			
		}else if(delito_existente != null) {
			
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
			model.addAttribute("delitos", listadoDelitos);
			model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "El delincuente " + apeynom + " ya tiene tiene un delito ingresado por asaltar la sucursal " + sucursal.getDescripcion() + " el día " + f_delito);
			
			return "redirect:/menu_delincuencia/delitos/delitos";
			
		}else{
			
			delito_actual.setDelincuente(delincuente);
			delito_actual.setSucursal(sucursal);
			delito_actual.setFechadelito(f_delito);
			delitoServ.guardar(delito_actual);
					
			List<Delito> listadoDelitos = delitoServ.listarDelitos();
			model.addAttribute("delitos", listadoDelitos);
			redirectAttrs.addFlashAttribute("mensaje", "Delito modificado con exito");
			
			return "redirect:/menu_delincuencia/delitos/delitos";
		}
	}
	
	@RequestMapping(value = "/delitos/buscar_sucursal", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> buscar_sucursal(@RequestParam("entidad") Long id_entidad) {

		List<Sucursal> sucursal = sucursalServ.buscarPorIdEntidad(id_entidad);
		
		ArrayList<String> nombreArray = new ArrayList<String>();
		
		if(sucursal != null){   
			for(Sucursal str : sucursal)
			{
				nombreArray.add(String.valueOf(str.getId()));      
				nombreArray.add(str.getDescripcion()); 
		    }

		}else{
			nombreArray.add(String.valueOf(0));
		}
		
		return nombreArray;
	}
}