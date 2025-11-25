package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import entidad.Propietario;
import modelo.HibernateUtil;

public class PropietarioDAO {
	
	/**
	 * Guarda un propietario en la base de datos
	 */
	public void create(Propietario p) {
		Session session= HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}
	
	/**
	 * Busca el propietario por su dni o null si no lo encuentra
	 */
	public Propietario read(String dni) {
		//Saco sesion
		Session session= HibernateUtil.getCurrentSession();
		session.beginTransaction();
		//metodo get de la sesion para buscar
		Propietario p=session.get(Propietario.class, dni);
		session.getTransaction().commit();
		return p;
	}
	/**
	 * Actualiza un propietario en la base de datos
	 */
	public void update(Propietario p) {
		Session session= HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
	}
	/**
	 * Elimina un propietario de la base de datos
	 */
	public void delete(Propietario p) {
		Session session= HibernateUtil.getCurrentSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
	}
	/**
	 * Devuelve una lista con todos los propietarios de la base de datos
	 */
	public List<Propietario> readAll(){
		Session session= HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Query<Propietario> query=session.createQuery("from Propietario",Propietario.class);
		List <Propietario> lista = query.getResultList();
		session.getTransaction().commit();
		return lista; 
		}
}
