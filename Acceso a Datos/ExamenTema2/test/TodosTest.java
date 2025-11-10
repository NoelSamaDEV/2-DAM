

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	jerarquia.EntrenadorTest.class, 
	jerarquia.GimnasioTest.class,
	persistencia.PersistenciaCSVTest.class,
	jerarquia.PokedexTest.class,
	jerarquia.PokemonTest.class})
	
/**Esta clase ejecuta todos los tests*/
public class TodosTest {

	//¡Ejecútame!
}
