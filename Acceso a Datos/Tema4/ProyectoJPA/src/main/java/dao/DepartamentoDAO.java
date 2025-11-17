package dao;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entidad.Departamento;
import modelo.HibernateUtil;

//Data Access Object
public class DepartamentoDAO {

	/**Guarda un departamento en la base de datos*/
	public void guardar(Departamento departamento) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.saveOrUpdate(departamento);
		sesion.getTransaction().commit();
	}

	/**Edita un departamento en la base de datos*/
	public void editar(Departamento departamento) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.saveOrUpdate(departamento);
		sesion.getTransaction().commit();
	}

	/**Busca un departamento en la base de datos a partir del
	 * identificador*/
	public Departamento buscar(Long id) {
		Session sesion = HibernateUtil.getCurrentSession();
		Departamento departamento = 
				sesion.get(Departamento.class, id);
		return departamento;
	}

	/**Elimina un departamento en la base de datos a partir del
	 * identificador*/
	public void eliminar(Long id) {
		Session sesion = HibernateUtil.getCurrentSession();
		// se busca el departamento
		Departamento departamento = 
				sesion.get(Departamento.class, id);
		// si no es null, se borra
		if (departamento==null) {
			return;
		}
		sesion.beginTransaction();
		sesion.delete(departamento);
		sesion.getTransaction().commit();
	}

	/**Extrae la lista de departamentos de la base de datos*/
	public List<Departamento> obtenerDepartamentos() {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Query<Departamento> query = 
				HibernateUtil
				.getCurrentSession()
				.createQuery("FROM Departamento", Departamento.class);
		List<Departamento> lista = query.list();
		session.close();
		return lista;
	}

}
