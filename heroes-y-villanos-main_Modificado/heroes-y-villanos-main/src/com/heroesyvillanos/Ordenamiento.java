package com.heroesyvillanos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase Ordenamiento que implementa Comparator<Competidor>.
 * Proporciona funcionalidades para definir y utilizar un orden personalizado de Caracteristica
 * para comparar diferentes objetos Competidor.
 */
public class Ordenamiento implements Comparator<Competidor> {
	private static List<Caracteristica> ordenCaracteristicasPorDefecto = new LinkedList<Caracteristica>(
		Arrays.asList(
			Caracteristica.VELOCIDAD,
			Caracteristica.FUERZA,
			Caracteristica.RESISTENCIA,
			Caracteristica.DESTREZA
		)
	);
	
	private List<Caracteristica> ordenCaracteristicas = new LinkedList<Caracteristica>();	
	
	/**
     * Obtiene el orden actual de características utilizado en la comparación.
     * 
     * @return Lista de Caracteristica representando el orden actual.
     */
	public List<Caracteristica> getOrdenCaracteristicas() {
		return ordenCaracteristicas;
	}

	/**
     * Constructor que inicializa un objeto Ordenamiento con el orden de características por defecto.
     */
	public Ordenamiento() {
		setearOrdenCaracteristicasPorDefecto();
	}

	/**
     * Compara dos objetos Competidor basado en un conjunto ordenado de características.
     * 
     * @param c1 Primer competidor para comparar.
     * @param c2 Segundo competidor para comparar.
     * @return Un valor entero: negativo si c1 < c2, positivo si c1 > c2, y 0 si son iguales.
     * @throws IllegalArgumentException si alguno de los competidores es nulo.
     */
	@Override	
	public int compare(Competidor c1, Competidor c2) throws IllegalArgumentException {
		int resultado = 0;
		
		if (c1 == null || c2 == null) {
	        throw new IllegalArgumentException("No se puede comparar competidores nulos");
	    }
		
		for (Caracteristica caracteristicaDeComparacion : ordenCaracteristicas) {
			resultado = Integer.compare(
						c1.getPromedioCaracteristica(caracteristicaDeComparacion),
						c2.getPromedioCaracteristica(caracteristicaDeComparacion)
			);
			if (resultado != 0) {
				break;
			}
		}
		return resultado;
	}
	
	/**
     * Establece el orden de características por defecto para el objeto Ordenamiento.
     * 
     * @return El objeto Ordenamiento con el orden de características reiniciado.
     */
	public Ordenamiento setearOrdenCaracteristicasPorDefecto() {
		ordenCaracteristicas = new LinkedList<Caracteristica>(ordenCaracteristicasPorDefecto);
		return this;
	}
	
	/**
     * Define un orden específico de características para el objeto Ordenamiento.
     * Crea una copia de la lista de características proporcionada.
     * 
     * @param caracteristicas Lista de Caracteristica que define el nuevo orden.
     * @return El objeto Ordenamiento con el nuevo orden de características establecido.
     * @throws IllegalArgumentException si la lista de características está vacía.
     */
	public Ordenamiento setearOrdenCaracteristicas(List<Caracteristica> caracteristicas) throws IllegalArgumentException {
		if (caracteristicas == null || caracteristicas.size() <= 0) {
	        throw new IllegalArgumentException("La lista de caractterísticas debe contener al menos un elemento.");
	    }
		ordenCaracteristicas = new LinkedList<Caracteristica>(caracteristicas);
		return this;
	}
}
