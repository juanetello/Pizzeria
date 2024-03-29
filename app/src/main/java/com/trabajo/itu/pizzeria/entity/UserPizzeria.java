package com.trabajo.itu.pizzeria.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserPizzeria {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String name;
	private String surname;
	private String password;
	@Email
	private String mail;
	
//	private String address;
//	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	/**
	 * @return the address
	 */
//	public String getAddress() {
//		return address;
//	}
	/**
	 * @param address the address to set
	 */
//	public void setAddress(String address) {
//		this.address = address;
//	}
	/**
	 * @return the phone
	 */
//	public String getPhone() {
//		return phone;
//	}
	/**
	 * @param phone the phone to set
	 */
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
		

}
