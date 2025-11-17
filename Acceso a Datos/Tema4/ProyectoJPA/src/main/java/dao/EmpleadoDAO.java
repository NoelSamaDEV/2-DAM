package dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entidad.Empleado;
import modelo.HibernateUtil;

//Data Access Object
public class EmpleadoDAO {

	/**Guarda un empleado en la base de datos*/
	public void guardar(Empleado empleado) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.saveOrUpdate(empleado);
		sesion.getTransaction().commit();
	}

	/**Edita un empleado en la base de datos*/
	public void editar(Empleado empleado) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.saveOrUpdate(empleado);
		sesion.getTransaction().commit();
	}

	/**Busca un empleado en la base de datos a partir del
	 * identificador*/
	public Empleado buscar(int id) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		Empleado empleado = 
				sesion.get(Empleado.class, id);
		sesion.close(); // no hice cambios, no necesito commit
		return empleado;
	}

	/**Elimina un empleado en la base de datos a partir del
	 * identificador*/
	public void eliminar(int id) {
		//se busca el empleado
		Empleado empleado = buscar(id);
		// si no es null, se borra
		if (empleado==null) {
			return;
		}
		Session sesion = HibernateUtil.getCurrentSession();
		// arranco la transacción
		sesion.beginTransaction();
		sesion.delete(empleado);
		sesion.getTransaction().commit();
	}

	/**Extrae la lista de empleados de la base de datos*/
	public List<Empleado> obtenerEmpleados() {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Query<Empleado> query = 
				HibernateUtil
				.getCurrentSession()
				.createQuery("FROM Empleado", Empleado.class);
		List<Empleado> lista = query.list();
		session.close();
		return lista;
	}
	
	public Empleado buscar(String nif) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		Query<Empleado> query = 
				HibernateUtil
				.getCurrentSession()
				.createQuery("FROM Empleado e WHERE e.nif= :nif"
						, Empleado.class);
		query.setParameter("nif", nif);
		Empleado e= query.uniqueResult(); // un resultado
		sesion.close(); 
		return e;
	}

}
