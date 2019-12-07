package com.trabajo.itu.pizzeria.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trabajo.itu.pizzeria.entity.DimensionesPizzas;

@Service
public class DimensionesPizzasService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void update(DimensionesPizzas dimensionesPizzas) throws Exception {
		if (dimensionesPizzas.getCodigo() == null || dimensionesPizzas.getCodigo().isEmpty()) {
			throw new Exception("El codigo no puede ser nulo o vacío.");
		}

		if (dimensionesPizzas.getNombre() == null || dimensionesPizzas.getNombre().isEmpty()) {
			throw new Exception("El nombre no puede ser vacío o nulo.");
		}

		if (dimensionesPizzas.getPorciones() == null || dimensionesPizzas.getPorciones() <= 0) {
			throw new Exception("El valor de la porción no puede ser negativo o nulo.");
		}

		List<DimensionesPizzas> otrosCodigos = findByCode(dimensionesPizzas.getCodigo());
		for (DimensionesPizzas otroCodigo : otrosCodigos) {
			if (!otroCodigo.getId().equals(dimensionesPizzas.getId())) {
				throw new Exception("El código ya esta siendo usado por otro producto.");
			}
		}

		entityManager.merge(dimensionesPizzas);

	}

	@Transactional
	public void delete(String id) {
		DimensionesPizzas dimensionesPizzas = entityManager.find(DimensionesPizzas.class, id);
		dimensionesPizzas.setEliminacion(new Date());
		entityManager.merge(dimensionesPizzas);
	}

	public DimensionesPizzas findById(String id) {
		return entityManager.find(DimensionesPizzas.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<DimensionesPizzas> findByCode(String codigo) {
		return entityManager.createQuery("SELECT c FROM DimensionesPizzas c WHERE c.codigo = :codigo")
				.setParameter("codigo", codigo).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DimensionesPizzas> listar(String search) {
		return entityManager.createQuery(
				"SELECT c FROM DimensionesPizzas c WHERE (c.codigo LIKE :search OR c.nombre LIKE :search ) AND c.eliminacion IS NULL ORDER BY c.codigo")
				.setParameter("search", "%" + search + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DimensionesPizzas> listar() {
		return entityManager
				.createQuery("SELECT c FROM DimensionesPizzas c WHERE c.eliminacion IS NULL ORDER BY c.codigo")
				.getResultList();
	}

}
