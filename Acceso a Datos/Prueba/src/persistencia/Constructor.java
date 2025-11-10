package persistencia;

/**Versión de Function pero con excepciones
 * @param <T> el tipo del input de la función
 * @param <R> el tipo del resultado de la función
 * @param <E> el tipo de la excepción que lanza el método de la función
 * */
@FunctionalInterface
public interface Constructor<T, R, E extends Throwable> {

	/**
     * Aplica esta función al argumento dado. Para usarlo de forma
     * funcional con un constructor se emplea como <em>Clase::new</em>
     *
     * @param t el argumento de la función
     * @return el resultado de la función
     * @throws E una excepción
     * */
	R apply(T t) throws E;
	
}
