package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import entidad.Coche;
import modelo.HibernateUtil;

public class CocheDAO {
	/**
	 * Guarda un coche en la base de datos
	 */
	public void create(Coche c) {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.save(c);
		session.getTransaction().commit();
	}

	/**
	 * Busca el coche por su matricula o null si no lo encuentra
	 */
	public Coche read(String matricula) {
		// Saco sesion
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		// metodo get de la sesion para buscar
		Coche c = session.get(Coche.class, matricula);
		session.getTransaction().commit();
		return c;
	}

	/**
	 * Actualiza un coche en la base de datos
	 */
	public void update(Coche c) {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.update(c);
		session.getTransaction().commit();
	}

	/**
	 * Elimina un coche de la base de datos
	 */
	public void delete(Coche c) {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.delete(c);
		session.getTransaction().commit();
	}

	/**
	 * Devuelve una lista con todos los coches de la base de datos
	 */
	public List<Coche> readAll() {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Query<Coche> query = session.createQuery("from Coche", Coche.class);
		List<Coche> lista = query.getResultList();
		session.getTransaction().commit();
		return lista;
	}

	public void modificarCoche(String matricula, int precioNuevo) {
		Coche c = read(matricula);
		if (c != null) {
			c.setPrecio(precioNuevo);
			CocheDAO cocheDAO = new CocheDAO();
			cocheDAO.update(c);
		}
	}
}
