package actividad1;

import java.util.HashSet;
import java.util.Set;

public class Matricula {
	
	private String matricula;
	
	private void setMatricula(String matricula) 
			throws MatriculaException {
		compruebaMatricula(matricula);
		this.matricula = matricula;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public Matricula(String matricula) throws MatriculaException {
		// siempre en mayúsculas
		setMatricula(matricula.toUpperCase());
	}
	
	@Override
	public int hashCode() {
		return getMatricula().hashCode();
	}
	
	// set de matrículas ya usadas
	private static Set<String> matriculas = new HashSet<>();
	
	
	/**Intenta añadir la matrícula al set y da fallo en caso
	 * de que ya estuviese*/
	private static void añadeMatricula(String matricula) 
			throws MatriculaException {
		// si está ya en el set, no messirve
		if(!matriculas.add(matricula))
			throw new MatriculaException("Matrícula repetida");
	}
	
	
	private static void compruebaMatricula(String matricula) 
			throws MatriculaException {
		// más vale que vengan todas en mayúsculas
		matricula = matricula.toUpperCase();
		// ¡cadena con 7 caracteres!
		if (matricula.length()!=7)
			throw new MatriculaException("Longitud incorrecta de matrícula");
		// 4 digitos – 3 letras
		// compruebo dígitos
		for(int i = 0; i<4; i++) {
			char c = matricula.charAt(i);
			// c>='0' && c<='9'
			if (!Character.isDigit(c)) {
				throw new MatriculaException("Lo primero no son dígitos");
			}
		}
		for(int i=4; i<7; i++) {
			char c = matricula.charAt(i);
			if (!Character.isLetter(c)){
				throw new MatriculaException("Lo último no son letras");
			}
		}
		añadeMatricula(matricula);
	}
	
	

}
