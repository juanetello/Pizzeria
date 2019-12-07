package com.trabajo.itu.pizzeria.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trabajo.itu.pizzeria.entity.Ingrediente;
import com.trabajo.itu.pizzeria.entity.Pizza;

@Service
public class PizzaService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void update (Pizza pizza, String[] ingredientes) throws Exception{
		if (pizza.getCodigo() == null || pizza.getCodigo().isEmpty()) {
			throw new Exception("El código no puede ser vacío o nulo.");
		}
		
		if (pizza.getNombre() == null || pizza.getNombre().isEmpty()) {
			throw new Exception("El nombre no puede ser vacío o nulo.");
		}
		
		if (pizza.getPrecio() == null || pizza.getPrecio() <= 0) {
			throw new Exception("El precio no puede ser negativo o nulo.");
		}
		
		if (ingredientes.length == 0) {
			throw new Exception("La pizza debe tener al menos un ingrediente.");
		}
		
		List<Pizza> otrosCodigos = findByCode(pizza.getCodigo());
		for (Pizza otroCodigo : otrosCodigos) {
			if (!otroCodigo.getId().equals(pizza.getId())) {
				throw new Exception("El código ya esta siendo usado por otro producto.");
			}
		}
		
		List<Ingrediente> seleccionados = new ArrayList<>();
		for (String idIngrediente : ingredientes) {
			Ingrediente ingrediente = entityManager.find(Ingrediente.class, idIngrediente);
			seleccionados.add(ingrediente);
		}
		
		pizza.setIngredientes(seleccionados);
		
		entityManager.merge(pizza);
	}

	@Transactional
	public void delete(String id) {
		Pizza pizza = entityManager.find(Pizza.class, id);
		pizza.setEliminacion(new Date());
		entityManager.merge(pizza);
	}

	public Pizza findById(String id) {
		return entityManager.find(Pizza.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Pizza> findByCode(String codigo) {
		return entityManager.createQuery("SELECT c FROM Pizza c WHERE c.codigo = :codigo")
				.setParameter("codigo", codigo).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Pizza> listar(String search) {
		return entityManager.createQuery(
				"SELECT c FROM Pizza c WHERE (c.codigo LIKE :search OR c.nombre LIKE :search OR c.descripcion LIKE :search) AND c.eliminacion IS NULL ORDER BY c.codigo")
				.setParameter("search", "%" + search + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Pizza> listar() {
		return entityManager.createQuery("SELECT c FROM Pizza c WHERE c.eliminacion IS NULL ORDER BY c.codigo")
				.getResultList();
	}

}
