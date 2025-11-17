package dao;

import org.hibernate.Session;

import entidad.Coche;
import modelo.HibernateUtil;

public class CocheDAO {
	
	/**
	 * Busca el coche por su matricula o null si no lo encuentra
	 */
	public Coche Buscar(String matricula) {
		//Saco sesion
		Session session= HibernateUtil.getCurrentSession();
		//metodo get de la sesion para buscar
		Coche c=session.get(Coche.class, matricula);
		return c;
	}
}
