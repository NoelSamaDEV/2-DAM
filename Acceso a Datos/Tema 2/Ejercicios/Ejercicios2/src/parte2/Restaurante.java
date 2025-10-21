package parte2;

public class Restaurante {
	private String nombre;
	private String address;
	private String city;
	private String state;
	private int zipCode;
	
	
	public String getNombre() {
		
		return nombre;
	}
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
	public String getAddress() {
		
		return address;
	}
	public void setAddress(String address) {
		
		this.address = address;
	}
	public String getCity() {
		
		return city;
	}
	public void setCity(String city) {
		
		this.city = city;
	}
	public String getState() {
		
		return state;
	}
	public void setState(String state) {
		
		this.state = state;
	}
	public int getZipCode() {
		
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public Restaurante(String nombre, String address, String city, String state, int zipCode) {
		super();
		this.nombre = nombre;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	/** Recibe una linea del fichero y devuelve un objeto de tipo restaurante */
	
	public static Restaurante parse(String linea) {
		// restaurant,Address,City,State,Zipcode
		String[] trozos = linea.split(",");
		String nombre = trozos[0];
		// la direccion puede tener muchos trozos
		String adress = "";
		
		for (int i=1; i<trozos.length; i++)
			adress+=trozos[i];
		
		String city = trozos[trozos.length -3];
		String state = trozos[trozos.length -2];
		String zipCodeS = trozos[trozos.length -1];
		int zipCode = Integer.parseInt(zipCodeS); 
		return new Restaurante(nombre, adress, city, state, zipCode);
		
	}
	public String aFichero() {
		//el contrario de parse
		// restaurant,Address,City,State,Zipcode
		String salida= "";
		salida+=getNombre()+",";
		salida+=getAddress()+",";
		salida+=getCity()+",";
		salida+=getState()+",";
		salida+=getZipCode();
		return salida;
		//%d para enteros %f reales %s resto
		/*String salida= String.format("%s, %s, %s, %s, %d", 
		 getNombre(), getAddress(), getCity(), getState(), getZipCode());*/
	}
}
