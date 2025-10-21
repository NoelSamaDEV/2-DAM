package TiendaInformatica;

public class Articulo {
	private int codigo;
    private String nombre;
    private double precio;
    private int fabricante;
    
	public Articulo(int codigo, String nombre, double precio, int fabricante) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.fabricante = fabricante;
	}
	
	public Articulo(int codigo, String nombre, int fabricante) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = 0.0;
		this.fabricante = fabricante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getFabricante() {
		return fabricante;
	}

	public void setFabricante(int fabricante) {
		this.fabricante = fabricante;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public String toString() {
		return "Articulo [codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", fabricante=" + fabricante
				+ "]";
	}
}
