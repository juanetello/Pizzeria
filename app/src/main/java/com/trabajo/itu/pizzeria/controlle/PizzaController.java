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
import com.trabajo.itu.pizzeria.entity.Pizza;
import com.trabajo.itu.pizzeria.service.IngredienteService;
import com.trabajo.itu.pizzeria.service.PizzaService;

@Controller
@RequestMapping("/administracion/pizzas")
public class PizzaController {
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping("/listado")
	public String inicio (ModelMap modelMap, @RequestParam(required=false) String search) {
		
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		if (search != null && !search.isEmpty()) {
			pizzas = pizzaService.listar(search);
		} else {
			pizzas = pizzaService.listar();
		}
		
		modelMap.put("search", search);
		modelMap.put("listado", pizzas);
		
		return "pizza_list.html";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(ModelMap modelMap, @RequestParam String id, @RequestParam String codigo,
			@RequestParam String nombre, @RequestParam String descripcion, @RequestParam Double precio,
			@RequestParam String[] ingredientes) {
		
		Pizza pizza = new Pizza();
		
		if (id != null && !id.isEmpty()) {
			pizza = pizzaService.findById(id);
		}
		
		pizza.setPrecio(precio);
		pizza.setCodigo(codigo);
		pizza.setNombre(nombre);
		pizza.setDescripcion(descripcion);
		
		try {
			pizzaService.update(pizza, ingredientes);
			return "redirect:/administracion/pizzas/listado";
		} catch (Exception e) {
			List<Ingrediente> ingr = ingredienteService.list();
			modelMap.put("ingredientes", ingr);
			modelMap.put("error", e.getMessage());
			modelMap.put("pizza", pizza);
			
			return "pizza_form.html";
		}
		
	}
	
	@GetMapping("/modificar")
	public String crear(ModelMap modelMap, @RequestParam String id) {
		Pizza pizza = pizzaService.findById(id);
		modelMap.put("pizza", pizza);
		modelMap.put("seleccionados", pizza.getIngredientes());
		
		List<Ingrediente> ingredientes = ingredienteService.list();
		ingredientes.removeAll(pizza.getIngredientes());
		
		modelMap.put("ingrediente", ingredientes);
		
		return "pizza_form.html";
	}
	
	@GetMapping("/crear")
	public String crear(ModelMap modelMap) {
		List<Ingrediente> ingredientes = ingredienteService.list();
		modelMap.put("ingredientes", ingredientes);
		modelMap.put("pizza", new Pizza());
		
		return "pizza_form.html";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(ModelMap modelMap, @RequestParam String id) {
		pizzaService.delete(id);
		return "redirect:/administracion/pizzas/listado";
	}
			

}
