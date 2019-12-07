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

import com.trabajo.itu.pizzeria.entity.DimensionesPizzas;
import com.trabajo.itu.pizzeria.service.DimensionesPizzasService;

@Controller
@RequestMapping("/administracion/dimensiones")
public class DimensionesPizzasController {

	@Autowired
	private DimensionesPizzasService dimensionesPizzasService;

	@GetMapping("/listado")
	public String inicio(ModelMap modelMap, @RequestParam(required = false) String search) {

		List<DimensionesPizzas> dimensionesPizzas = new ArrayList<DimensionesPizzas>();

		if (search != null && !search.isEmpty()) {
			dimensionesPizzas = dimensionesPizzasService.listar(search);
		} else {
			dimensionesPizzas = dimensionesPizzasService.listar();
		}

		modelMap.put("search", search);
		modelMap.put("listado", dimensionesPizzas);

		return "dimensiones_list.html";
	}

	@PostMapping("/actualizar")
	public String actualizar(ModelMap modelMap, @RequestParam String id, @RequestParam String codigo,
			@RequestParam Long porciones, @RequestParam String nombre) {

		DimensionesPizzas dimensionesPizzas = new DimensionesPizzas();

		if (id != null && !id.isEmpty()) {
			dimensionesPizzas = dimensionesPizzasService.findById(id);
		}

		dimensionesPizzas.setCodigo(codigo);
		dimensionesPizzas.setPorciones(porciones);
		dimensionesPizzas.setNombre(nombre);

		try {
			dimensionesPizzasService.update(dimensionesPizzas);
			return "redirect:/administracion/dimensiones/listado";
		} catch (Exception e) {
			modelMap.put("error", e.getMessage());
			modelMap.put("dimensionesPizzas", dimensionesPizzas);

			return "dimensiones_form";
		}

	}

	@GetMapping("/modificar")
	public String crear(ModelMap modelMap, @RequestParam String id) {
		
		DimensionesPizzas dimensionesPizzas = dimensionesPizzasService.findById(id);
		modelMap.put("dimensionesPizzas", dimensionesPizzas);

		return "dimensiones_form.html";
	}

	@GetMapping("/crear")
	public String crear(ModelMap modelMap) {
		modelMap.put("dimensionesPizzas", new DimensionesPizzas());

		return "dimensiones_form.html";
	}

	@GetMapping("/eliminar")
	public String eliminar(ModelMap modelMap, @RequestParam String id) {
		dimensionesPizzasService.delete(id);
		return "redirect:/administracion/dimensiones/listado";
	}

}
