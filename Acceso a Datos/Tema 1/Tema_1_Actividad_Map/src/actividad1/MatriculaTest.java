package actividad1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatriculaTest {

	@Test
	public void testLongitudMatriculaCorta() {
		try {
			new Matricula("A");
		} 
		catch (MatriculaException e) {
			assertEquals(MatriculaException.ERROR_LONGITUD, e.getMessage());
		}
		
	}
	
	@Test
	public void testLongitudMatriculaLarga() {
		try {
			new Matricula("0123ABCd");
		} 
		catch (MatriculaException e) {
			assertEquals(MatriculaException.ERROR_LONGITUD, e.getMessage());
		}
	}
	
	@Test
	public void testMatriculaPos0(){
		try {
			new Matricula("A123ABC");
		} 
		catch (MatriculaException e) {
			assertEquals(MatriculaException.ERROR_DIGITOS, e.getMessage());
		}
	}
	
	@Test
	public void matriculaCorrecta() {
		try {
			String matricula = "0123ABC";
			Matricula m = new Matricula(matricula);
			assertEquals(matricula, m.getMatricula());
		} 
		catch (MatriculaException e) {
			fail("Cagaste");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void matriculaNull() {
		try {
			new Matricula(null);
		} 
		catch (MatriculaException e) {
			assertEquals(MatriculaException.ERROR_MATRICULA_NULA, e.getMessage());
		}
		
	}

}
