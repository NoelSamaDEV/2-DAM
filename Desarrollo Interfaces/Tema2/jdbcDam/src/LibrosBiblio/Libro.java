package LibrosBiblio;

public class Libro {
	private String isbn;
	private String titulo;
	private String autor;
	private double precio;
	
	public Libro(String isbn, String titulo, String autor, double precio) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
	}
	
	public Libro(String titulo, String autor, double precio) {
		this.isbn = "";
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public String toString() {
		return "ISBN: " + isbn + ", Titulo: " + titulo + ", Autor: " + autor + ", Precio: " + precio;
	}
}
