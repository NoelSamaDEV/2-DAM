package actividad1;

public class Vehiculo {
	
	private Matricula matricula;
	private String modelo;
	private double km;
	
	public Matricula getMatricula() {
		return matricula;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public double getKm() {
		return km;
	}
	
	public void setKm(double km) {
		this.km = km;
	}
	
	public Vehiculo(String matricula, String modelo, double km) 
			throws MatriculaException {
		this.matricula = new Matricula(matricula);
		setModelo(modelo);
		setKm(km);
	}
	

} // fin de Vehiculo
