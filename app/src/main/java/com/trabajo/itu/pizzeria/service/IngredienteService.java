package com.trabajo.itu.pizzeria.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trabajo.itu.pizzeria.entity.Ingrediente;

/**
 * 
 * @author juantello
 *
 */

@Service
public class IngredienteService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void updateIngredient(Ingrediente ingrediente) throws Exception {
		if (ingrediente.getCodigo() == null || ingrediente.getCodigo().isEmpty()) {
			throw new Exception("El codigo no puede ser nulo o vacío.");
		}

		if (ingrediente.getNombre() == null || ingrediente.getNombre().isEmpty()) {
			throw new Exception("El nombre no puede ser vacío o nulo.");
		}
		
		if (ingrediente.getStock() == null || ingrediente.getStock().isEmpty()) {
			throw new Exception("El stock no puede ser vacío o nulo.");
		}

		List<Ingrediente> otrosIngredientes = findByCode(ingrediente.getCodigo());
		for (Ingrediente otroIngrediente : otrosIngredientes) {
			if (!otroIngrediente.getId().equals(ingrediente.getId())) {
				throw new Exception("El código ya esta siendo usado por otro producto.");
			}
		}

		entityManager.merge(ingrediente);
	}

	@Transactional
	public void deleteIngredient(String id) {
		Ingrediente ingrediente = entityManager.find(Ingrediente.class, id);
		ingrediente.setEliminacion(new Date());
		entityManager.merge(ingrediente);
	}

	public Ingrediente findById(String id) {
		return entityManager.find(Ingrediente.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Ingrediente> findByCode(String codigo) {
		return entityManager.createQuery("SELECT c FROM Ingrediente c WHERE c.codigo = :codigo")
				.setParameter("codigo", codigo).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Ingrediente> list(String search) {
		return entityManager.createQuery(
				"SELECT c FROM Ingrediente c WHERE (c.codigo LIKE :search OR c.nombre LIKE :search OR c.descripcion LIKE :search) "
				+ "AND c.eliminacion IS NULL ORDER BY c.codigo")
				.setParameter("search", "%" + search + "%").getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Ingrediente> list() {
		return entityManager.createQuery("SELECT c FROM Ingrediente c WHERE c.eliminacion IS NULL ORDER BY c.codigo")
				.getResultList();
	}
}
