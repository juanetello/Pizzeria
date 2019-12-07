package com.trabajo.itu.pizzeria.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class DimensionesPizzas {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String codigo;
	private Long porciones;
	private String nombre;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date eliminacion;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the porciones
	 */
	public Long getPorciones() {
		return porciones;
	}

	/**
	 * @param porciones the porciones to set
	 */
	public void setPorciones(Long porciones) {
		this.porciones = porciones;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the eliminacion
	 */
	public Date getEliminacion() {
		return eliminacion;
	}

	/**
	 * @param eliminacion the eliminacion to set
	 */
	public void setEliminacion(Date eliminacion) {
		this.eliminacion = eliminacion;
	}
	
	

}
