import dao.CocheDAO;
import dao.PropietarioDAO;
import entidad.Coche;
import entidad.Propietario;


public class Main {
	
	public static void main(String[] args) {
		
		CocheDAO cocheDAO = new CocheDAO();
		PropietarioDAO propietarioDAO = new PropietarioDAO();
		
		System.out.println(new CocheDAO().read("CA-5555"));
		
		System.out.println(new PropietarioDAO().read("X25"));
		
		//Coche c = new Coche("misco",null,0,null);
		//cocheDAO.create(c);
		//System.out.println(cocheDAO.read("misco"));
		for(Coche c : cocheDAO.readAll()) {
			System.out.println(c);
		}
		for(Propietario p : propietarioDAO.readAll()) {
			System.out.println(p);
		}
		
		
	}

}
