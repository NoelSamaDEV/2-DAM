import dao.EmpleadoDAO;
import entidad.Empleado;

public class Main {
	
	public static void main(String[] args) {
		
		EmpleadoDAO edao = new EmpleadoDAO();
			
		//CRUD
		Empleado emp = new Empleado("12345A", "Gustavo", "Fring", "Garcia", null);
		//delete
		edao.eliminar(14);
		//create
		edao.guardar(emp);
		//compruebo
		for(Empleado e: edao.obtenerEmpleados()) {
			System.out.println(e);
		}
	}

}
