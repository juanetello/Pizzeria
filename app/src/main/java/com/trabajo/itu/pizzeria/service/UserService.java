package com.trabajo.itu.pizzeria.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trabajo.itu.pizzeria.entity.UserPizzeria;

/**
 * 
 * @author juantello
 *
 */

@Service
public class UserService implements UserDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public UserPizzeria updateUser(String id, String name, String surname, String mail, String password1,
			String password2) throws Exception {

		validate(name, surname, mail, password1, password2);

		List<UserPizzeria> existingUser = findUserByMail(mail);

		if (!existingUser.isEmpty()) {
			for (UserPizzeria userPizzeria : existingUser) {
				if (!userPizzeria.getId().equals(id)) {
					throw new Exception("Ya existe un usuario registrado con ese mail.");
				}
			}
		}

		UserPizzeria userPizzeria = findUserById(id);
		
		String encodePassword = new BCryptPasswordEncoder().encode(password1);
		
		userPizzeria.setPassword(encodePassword);
		userPizzeria.setName(name);
		userPizzeria.setSurname(surname);
		userPizzeria.setMail(mail);

		return entityManager.merge(userPizzeria);
	}

	@Transactional
	public UserPizzeria register(String name, String surname, String mail, String password1, String password2)
			throws Exception {

		validate(name, surname, mail, password1, password2);

		List<UserPizzeria> existingUser = findUserByMail(mail);
		if (!existingUser.isEmpty()) {
			throw new Exception("Ya existe un usuario registrado con ese mail.");
		}

		UserPizzeria userPizzeria = new UserPizzeria();
		
		String encodePassword = new BCryptPasswordEncoder().encode(password1);
		
		userPizzeria.setName(name);
		userPizzeria.setSurname(surname);
		userPizzeria.setPassword(encodePassword);
		userPizzeria.setMail(mail);

		entityManager.persist(userPizzeria);

		return userPizzeria;

	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		// UserDetails es un objeto que lleva permisos y datos del user.
		List<UserPizzeria> users = findUserByMail(mail);

		User user = null;
		if (!users.isEmpty()) {
			UserPizzeria userPizzeria = users.get(0);
			List<GrantedAuthority> permission = new ArrayList<>();

			switch (userPizzeria.getRol()) {
			case ADMINISTRATOR:
				permission.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
				break;
			case CLIENT:
				permission.add(new SimpleGrantedAuthority("CLIENT"));
				break;
			}
			user = new User(userPizzeria.getMail(), userPizzeria.getPassword(), permission);
		}

		return user;
	}

	public void validate(String name, String surname, String mail, String password1, String password2)
			throws Exception {

		if (password1 == null || password1.isEmpty()) {
			throw new Exception("La contraseña ingresada no puede estar vacia.");
		}

		if (password2 == null || password2.isEmpty()) {
			throw new Exception("La contraseña ingresada no puede estar vacia.");
		}

		if (!password1.equals(password2)) {
			throw new Exception("Las contraseña ingresadas deben ser iguales.");
		}

		if (name == null || name.isEmpty()) {
			throw new Exception("El nombre de usuario no puede estar vacio.");
		}

		if (surname == null || surname.isEmpty()) {
			throw new Exception("El apellido no puede estar vacio.");
		}

		if (mail == null || mail.isEmpty()) {
			throw new Exception("El mail del usuario no puede estar vacio.");
		}

	}

	public UserPizzeria findUserById(String id) {
		return entityManager.find(UserPizzeria.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<UserPizzeria> findUserByMail(String mail) {
		return entityManager.createQuery("SELECT c FROM UserPizzeria c WHERE c.mail = :m").setParameter("m", mail)
				.getResultList();
	}

}
