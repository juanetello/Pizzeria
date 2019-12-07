package com.trabajo.itu.pizzeria.controlle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trabajo.itu.pizzeria.entity.Ingrediente;
import com.trabajo.itu.pizzeria.service.IngredienteService;

/**
 * 
 * @author juantello
 *
 */

@Controller
@RequestMapping("/administracion/ingredientes")
public class IngredientesController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	
	@GetMapping("/listado")
	public String inicio(ModelMap modelMap, @RequestParam(required = false) String search) {
		
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

		if (search != null && !search.isEmpty()) {
			ingredientes = ingredienteService.list(search);
		} else {
			ingredientes = ingredienteService.list();
		}

		modelMap.put("search", search);
		modelMap.put("listado", ingredientes);

		return "ingredientes_list.html";
	}
	
	
	@PostMapping("/actualizar")
	public String actualizar(ModelMap modelMap, @RequestParam String id, @RequestParam String codigo,
			@RequestParam String nombre, @RequestParam String descripcion, @RequestParam String stock) {

		Ingrediente ingrediente = new Ingrediente();

		if (id != null && !id.isEmpty()) {
			ingrediente = ingredienteService.findById(id);
		}
		
		ingrediente.setCodigo(codigo);
		ingrediente.setNombre(nombre);
		ingrediente.setDescripcion(descripcion);
		ingrediente.setStock(stock);
		
		try {
			ingredienteService.updateIngredient(ingrediente);
			return "redirect:/administracion/ingredientes/listado";
		} catch(Exception e) {
			modelMap.put("error", e.getMessage());
			modelMap.put("ingrediente", ingrediente);
			
			return "ingredientes_form"; 
		}
		
	}
	
	@GetMapping("/updateIngredient") // modificar
	public String updateIngredient(ModelMap modelMap, @RequestParam String id) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		modelMap.put("ingrediente", ingrediente);
		return "ingredientes_form.html";
	}

	@GetMapping("/create")
	public String create(ModelMap modelMap) {
		modelMap.put("ingrediente", new Ingrediente());
		return "ingredientes_form.html";
	}
	
	@GetMapping("/eliminar")
	public String delete(ModelMap modelMap, @RequestParam String id) {
		ingredienteService.deleteIngredient(id);
		return "redirect:/administracion/ingredientes/listado";
	}
	

}

















