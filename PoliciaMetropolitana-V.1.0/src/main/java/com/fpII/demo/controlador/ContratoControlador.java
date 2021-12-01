package com.fpII.demo.controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpII.demo.entidad.Contrato;
import com.fpII.demo.entidad.EntidadBancaria;
import com.fpII.demo.entidad.Sucursal;
import com.fpII.demo.entidad.Vigilante;
import com.fpII.demo.servicio.ContratoServicio;
import com.fpII.demo.servicio.EntidadBancariaServicio;
import com.fpII.demo.servicio.SucursalServicio;
import com.fpII.demo.servicio.VigilanteServicio;

@Controller
@RequestMapping("/menu_bancos")
public class ContratoControlador {

	@Autowired
	private ContratoServicio contratoServ;
	
	@Autowired
	private VigilanteServicio vigilanteServ;
	
	@Autowired
	private EntidadBancariaServicio entidadServ;
	
	@Autowired
	private SucursalServicio sucursalServ;	
	
	@GetMapping("/contratos/contratos")
	public String listarContratos(ModelMap model) {
		
		List<Contrato> listadoContratos = contratoServ.listarContratos();
		
		for(Contrato str : listadoContratos){
			Date f_nacimiento = str.getVigilante().getFecha_nacimiento();
			int edad = calcularEdad(f_nacimiento);
			str.getVigilante().setEdad(edad);
	    }
		
		model.addAttribute("contratos", listadoContratos);
		
		return "menu/menu_bancos/contratos/contratos";
	}
	
	@GetMapping("/contratos/agregar")
	public String agregar(ModelMap model) {
				
		List<Vigilante> listadoVigilantes = vigilanteServ.listarVigilantes();
		List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
		model.addAttribute("vigilantes", listadoVigilantes);
		model.addAttribute("entidades", listadoEntidades);
		
		return "menu/menu_bancos/contratos/agregar_contrato";
	}
	
	@PostMapping("/contratos/agregar/guardar_contrato")
	public String crearContrato(@RequestParam(name = "vigilante") Long id_vigilante, @RequestParam(name = "sucursal") Long id_sucursal, @RequestParam(name = "fecha_contrato") Date f_contrato, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Vigilante vigilante = vigilanteServ.buscarPorId(id_vigilante);
		Contrato contrato_existente = contratoServ.buscarPorVigilanteYFecha(vigilante,f_contrato);
		String apeynom = vigilante.getUsuario().getPersona().getApellido() + ", " + vigilante.getUsuario().getPersona().getNombre();
		
		if(contrato_existente != null) {
			redirectAttrs.addFlashAttribute("mensaje", "El vigilante " + apeynom + " ya tiene un contrato el día " + f_contrato);
			
			List<Vigilante> listadoVigilantes = vigilanteServ.listarVigilantes();
			List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
			List<Sucursal> listadoSucursales = sucursalServ.listarSucursales();
			model.addAttribute("vigilantes", listadoVigilantes);
			model.addAttribute("entidades", listadoEntidades);
			model.addAttribute("sucursales", listadoSucursales);
			
			return "redirect:/menu_bancos/contratos/agregar";
			
		}else{
			
			Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
			
			Contrato contrato_nuevo = new Contrato();
			
			contrato_nuevo.setVigilante(vigilante);
			contrato_nuevo.setSucursal(sucursal);
			contrato_nuevo.setFechacontrato(f_contrato);
			contratoServ.guardar(contrato_nuevo);
					
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			redirectAttrs.addFlashAttribute("mensaje", "Contrato creado con exito");
			
			return "redirect:/menu_bancos/contratos/contratos";
		}
	}
	
	@GetMapping("/contratos/eliminar/{id}")
	public String eliminarContrato(@PathVariable("id") Long id_contrato, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Calendar fecha = new GregorianCalendar();
		
		Contrato contrato = contratoServ.buscarPorId(id_contrato);	
		Date f_contrato = contrato.getFechacontrato();
		
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		LocalDate fecha_actual = LocalDate.of(anio,mes,dia);	
		
		fecha.setTime(f_contrato);
		int f_contrato_anio = fecha.get(Calendar.YEAR);
        int f_contrato_mes = fecha.get(Calendar.MONTH)+1;
        int f_contrato_dia = fecha.get(Calendar.DAY_OF_MONTH);
        LocalDate fecha_contrato = LocalDate.of(f_contrato_anio,f_contrato_mes,f_contrato_dia);
		
		if(fecha_contrato.compareTo(fecha_actual) < 0 || fecha_contrato.compareTo(fecha_actual) == 0) {
			
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede eliminar un contrato que es de una fecha menor/igual a la actual.");
			
			return "redirect:/menu_bancos/contratos/contratos";
			
		}else {
			
			contratoServ.eliminar(id_contrato);
			
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			redirectAttrs.addFlashAttribute("mensaje", "Contrato eliminado con exito");
			
			return "redirect:/menu_bancos/contratos/contratos";
		}
	}
	
	@GetMapping("/contratos/editar/{id}")
	public String editar(@PathVariable("id") Long id_contrato, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Calendar fecha = new GregorianCalendar();
		
		Contrato contrato = contratoServ.buscarPorId(id_contrato);	
		Date f_contrato = contrato.getFechacontrato();
		
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		LocalDate fecha_actual = LocalDate.of(anio,mes,dia);	
		
		fecha.setTime(f_contrato);
		int f_contrato_anio = fecha.get(Calendar.YEAR);
        int f_contrato_mes = fecha.get(Calendar.MONTH)+1;
        int f_contrato_dia = fecha.get(Calendar.DAY_OF_MONTH);
        LocalDate fecha_contrato = LocalDate.of(f_contrato_anio,f_contrato_mes,f_contrato_dia);
		
		if(fecha_contrato.compareTo(fecha_actual) < 0 || fecha_contrato.compareTo(fecha_actual) == 0) {
			
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede editar un contrato que es de una fecha menor/igual a la actual.");
			
			return "redirect:/menu_bancos/contratos/contratos";
			
		}else {
			
			List<Vigilante> listadoVigilantes = vigilanteServ.listarVigilantes();
			List<EntidadBancaria> listadoEntidades = entidadServ.listarEntidades();
			List<Sucursal> listadoSucursales = sucursalServ.buscarPorIdEntidad(contrato.getSucursal().getEntidad().getId());		
			model.addAttribute("contrato", contrato);
			model.addAttribute("vigilantes", listadoVigilantes);
			model.addAttribute("entidades", listadoEntidades);
			model.addAttribute("sucursales", listadoSucursales);
			
			return "menu/menu_bancos/contratos/editar_contrato";
		
		}
	}
	
	@PostMapping("/contratos/editar/guardar_contrato")
	public String editarContrato(@RequestParam(name = "id_contrato") Long id_contrato,@RequestParam(name = "vigilante") Long id_vigilante, @RequestParam(name = "sucursal") Long id_sucursal, @RequestParam(name = "fecha_contrato") Date f_contrato, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Vigilante vigilante = vigilanteServ.buscarPorId(id_vigilante);
		Contrato contrato_existente = contratoServ.buscarPorId(id_contrato);
		Contrato contrato_existente_vigilante = contratoServ.buscarPorVigilanteYFecha(vigilante,f_contrato);
		String apeynom = vigilante.getUsuario().getPersona().getApellido() + ", " + vigilante.getUsuario().getPersona().getNombre();
		
		if(contrato_existente_vigilante != null && !f_contrato.equals(contrato_existente.getFechacontrato())) {
			redirectAttrs.addFlashAttribute("mensaje", "El vigilante " + apeynom + " ya tiene un contrato el día " + f_contrato);
			
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			
			return "redirect:/menu_bancos/contratos/contratos";
			
		}else{
			
			Sucursal sucursal = sucursalServ.buscarPorId(id_sucursal);
			
			contrato_existente.setVigilante(vigilante);
			contrato_existente.setSucursal(sucursal);
			contrato_existente.setFechacontrato(f_contrato);
			contratoServ.guardar(contrato_existente);
					
			List<Contrato> listadoContratos = contratoServ.listarContratos();
			model.addAttribute("contratos", listadoContratos);
			redirectAttrs.addFlashAttribute("mensaje", "Contrato modificado con exito");
			
			return "redirect:/menu_bancos/contratos/contratos";
		}
	}
	
	@RequestMapping(value = "/contratos/buscar_sucursal", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> buscar_sucursal(@RequestParam("entidad") Long id_entidad) {

		List<Sucursal> sucursal = sucursalServ.buscarPorIdEntidad(id_entidad);
		
		ArrayList<String> nombreArray = new ArrayList<String>();
		
		if(sucursal != null){   
			for(Sucursal str : sucursal){
				nombreArray.add(String.valueOf(str.getId()));      
				nombreArray.add(str.getDescripcion()); 
		    }

		}else{
			nombreArray.add(String.valueOf(0));
		}
		
		return nombreArray;
	}
	
	private int calcularEdad(Date f_nacimiento) {
        Calendar hoy = Calendar.getInstance();
        Calendar fecha = new GregorianCalendar();
        fecha.setTime(f_nacimiento);
        
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