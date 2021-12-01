package com.fpII.demo.controlador;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

import com.fpII.demo.entidad.Delincuente;
import com.fpII.demo.entidad.Delito;
import com.fpII.demo.entidad.Juez;
import com.fpII.demo.entidad.Sentencia;
import com.fpII.demo.servicio.DelincuenteServicio;
import com.fpII.demo.servicio.DelitoServicio;
import com.fpII.demo.servicio.JuezServicio;
import com.fpII.demo.servicio.SentenciaServicio;

@Controller
@RequestMapping("/menu_delincuencia")
public class SentenciaControlador{

	@Autowired
	SentenciaServicio sentenciaServ;
	
	@Autowired
	DelitoServicio delitoServ;
	
	@Autowired
	DelincuenteServicio delincuenteServ;
	
	@Autowired
	JuezServicio juezServ;
	
	@GetMapping("/sentencias/sentencias")
	public String listarSentencias(ModelMap model) {
		
		List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
		model.addAttribute("sentencias", listadoSentencia);
		
		return "menu/menu_delincuencia/sentencias/sentencias";
	}
	
	@GetMapping("/sentencias/agregar")
	public String agregar(ModelMap model) {
				
		List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();	
		List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
		List<Juez> listadoJueces = juezServ.listarJueces();
		model.addAttribute("sentencias", listadoSentencia);
		model.addAttribute("delincuentes", listadoDelincuentes);
		model.addAttribute("jueces", listadoJueces);
		
		return "menu/menu_delincuencia/sentencias/agregar_sentencia";
	}
	
	@PostMapping("/sentencias/agregar/guardar_sentencia")
	public String crearContrato(@RequestParam(name = "delito") Long id_delito, @RequestParam(name = "juez") Long id_juez, @RequestParam(name = "fecha_inicio") Date f_inicio, @RequestParam(name = "fecha_fin") Date f_fin, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Delito delito = delitoServ.buscarPorId(id_delito);
		Juez Juez = juezServ.buscarPorId(id_juez);
			
		Sentencia sentencia_nueva = new Sentencia();
			
		sentencia_nueva.setDelito(delito);
		sentencia_nueva.setJuez(Juez);
		sentencia_nueva.setFechainicio(f_inicio);
		sentencia_nueva.setFechafin(f_fin);
		sentenciaServ.guardar(sentencia_nueva);
		
		delito.setSentencia(1);
		delitoServ.guardar(delito);
					
		List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
		model.addAttribute("sentencias", listadoSentencia);
		redirectAttrs.addFlashAttribute("mensaje", "Sentencia creada con exito");
			
		return "redirect:/menu_delincuencia/sentencias/sentencias";
	}
	
	@GetMapping("/sentencias/eliminar/{id}")
	public String eliminarSucursal(@PathVariable("id") Long id_sentencia, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Calendar fecha = new GregorianCalendar();
		Sentencia sentencia = sentenciaServ.buscarPorId(id_sentencia);	
		Date f_inicio = sentencia.getFechainicio();
		
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		LocalDate fecha_actual = LocalDate.of(anio,mes,dia);	
		
		fecha.setTime(f_inicio);
		int f_inicio_anio = fecha.get(Calendar.YEAR);
        int f_inicio_mes = fecha.get(Calendar.MONTH)+1;
        int f_inicio_dia = fecha.get(Calendar.DAY_OF_MONTH);
        LocalDate fecha_inicio = LocalDate.of(f_inicio_anio,f_inicio_mes,f_inicio_dia);
        
        if(fecha_inicio.compareTo(fecha_actual) < 0) {
			
        	List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
    		model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede eliminar una sentencia que tiene una fecha de inicio menor a la actual.");
			
			return "redirect:/menu_delincuencia/sentencias/sentencias";
			
		}else {
			
			Delito delito = delitoServ.buscarPorId(sentencia.getDelito().getId());	
			
			delito.setSentencia(0);
			delitoServ.guardar(delito);
			sentenciaServ.eliminar(id_sentencia);
			redirectAttrs.addFlashAttribute("mensaje", "Sentencia eliminada con exito");
			
			return "redirect:/menu_delincuencia/sentencias/sentencias";
		}
	}
	
	@GetMapping("/sentencias/editar/{id}")
	public String editar(@PathVariable("id") Long id_sentencia, ModelMap model, RedirectAttributes redirectAttrs) {
		
		Calendar fecha = new GregorianCalendar();	
		Sentencia sentencia = sentenciaServ.buscarPorId(id_sentencia);	
		Date f_inicio = sentencia.getFechainicio();
		
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		LocalDate fecha_actual = LocalDate.of(anio,mes,dia);	
		
		fecha.setTime(f_inicio);
		int f_inicio_anio = fecha.get(Calendar.YEAR);
        int f_inicio_mes = fecha.get(Calendar.MONTH)+1;
        int f_inicio_dia = fecha.get(Calendar.DAY_OF_MONTH);
        LocalDate fecha_inicio = LocalDate.of(f_inicio_anio,f_inicio_mes,f_inicio_dia);
        
        if(fecha_inicio.compareTo(fecha_actual) < 0) {
			
        	List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
    		model.addAttribute("sentencias", listadoSentencia);
			redirectAttrs.addFlashAttribute("mensaje", "No se puede editar una sentencia que tiene una fecha de inicio menor a la actual.");
			
			return "redirect:/menu_delincuencia/sentencias/sentencias";
			
		}else {
        
			Long id_delincuente = sentencia.getDelito().getDelincuente().getId();
			List<Delincuente> listadoDelincuentes = delincuenteServ.listarDelincuentes();
			List<Delito> listadoDelitosPorDelincuente = delitoServ.buscarDelitosPorIdDelincuente(id_delincuente);
			List<Juez> listadoJueces = juezServ.listarJueces();
			model.addAttribute("sentencia", sentencia);
			model.addAttribute("delincuentes", listadoDelincuentes);
			model.addAttribute("delitos", listadoDelitosPorDelincuente);
			model.addAttribute("jueces", listadoJueces);
			
			return "menu/menu_delincuencia/sentencias/editar_sentencia";
		}
	}
	
	@PostMapping("/sentencias/editar/guardar_sentencia")
	public String editarSentencia(@RequestParam(name = "id_sentencia") Long id_sentencia, @RequestParam(name = "delito") Long id_delito, @RequestParam(name = "juez") Long id_juez, @RequestParam(name = "fecha_inicio") Date f_inicio, @RequestParam(name = "fecha_fin") Date f_fin, ModelMap model, RedirectAttributes redirectAttrs) {
	
		Sentencia sentencia_existente = sentenciaServ.buscarPorId(id_sentencia);
		Delito delito = delitoServ.buscarPorId(id_delito);
		Juez juez = juezServ.buscarPorId(id_juez);
		
		if(sentencia_existente.getDelito().getId() != id_delito) {
			Delito delito_existente = delitoServ.buscarPorId(sentencia_existente.getDelito().getId());
			delito_existente.setSentencia(0);
			delitoServ.guardar(delito_existente);
			
			delito.setSentencia(1);
			delitoServ.guardar(delito);
		}
		
		sentencia_existente.setDelito(delito);
		sentencia_existente.setJuez(juez);
		sentencia_existente.setFechainicio(f_inicio);
		sentencia_existente.setFechafin(f_fin);
		sentenciaServ.guardar(sentencia_existente);
		
		List<Sentencia> listadoSentencia = sentenciaServ.listarSentencias();
		model.addAttribute("sentencias", listadoSentencia);
		redirectAttrs.addFlashAttribute("mensaje", "Sentencia editada con exito");
		
		return "redirect:/menu_delincuencia/sentencias/sentencias";
	}
	
	@RequestMapping(value = "/sentencias/buscar_delitos", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> buscar_delitos(@RequestParam("delincuente") Long id_delincuente) {

		List<Delito> delito = delitoServ.buscarDelitosPorIdDelincuente(id_delincuente);
		
		ArrayList<String> nombreArray = new ArrayList<String>();
		
		if(delito != null){   
			for(Delito str : delito){
				
				String entidad = str.getSucursal().getEntidad().getDescripcion();
				String sucursal = str.getSucursal().getDescripcion();
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = formato.format(str.getFechadelito());
				
				nombreArray.add(String.valueOf(str.getId()));
				nombreArray.add(entidad + ", " + sucursal);
				nombreArray.add(fecha);
		    }
		}else{
			nombreArray.add(String.valueOf(0));
		}
		
		return nombreArray;
	}
}
