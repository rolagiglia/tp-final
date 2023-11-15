package com.heroesyvillanos.test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

import com.heroesyvillanos.*;

class PersonajeTest {

	@Test
	void testConstructorNormal() {
		String nombre = "peter";
		String nombreFantasia = "peter";
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		try {
			new Personaje(nombre, nombreFantasia, caracteristicas, tipo);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testConstructorError() {
		String nombre = "peter";
		String nombreFantasia = "spider-man";
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10),
				Map.entry(Caracteristica.RESISTENCIA, 10),
				Map.entry(Caracteristica.DESTREZA, 10)
		);
		Map<Caracteristica, Integer> caracteristicasError = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10)
		);
		
		try {
			//Caso faltan caracteristicas
			new Personaje(nombre, nombreFantasia, caracteristicasError, tipo);
			
			//Caso nombres invalidos
			new Personaje(nombre, "", caracteristicas, tipo);
			new Personaje("", nombreFantasia, caracteristicas, tipo);
			
		} catch (IllegalArgumentException e) {
			return;
		}
		
		fail();
	}
	
	@Test
	void testMethods() {
		String nombre = "peter";
		String nombreFantasia = "spider-man";
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 10),
				Map.entry(Caracteristica.FUERZA, 10),
				Map.entry(Caracteristica.RESISTENCIA, 10),
				Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Personaje p = new Personaje(nombre, nombreFantasia, caracteristicas, tipo);
		
		//Test de get promedio valores caracteristicas
		for(Caracteristica c : Caracteristica.values()) {
			assertEquals(
					caracteristicas.get(c).intValue(), 
					p.getPromedioCaracteristica(c)
			);
		}
		
		//Test de get suma valores caracteristicas
		for(Caracteristica c : Caracteristica.values()) {
			assertEquals(
					caracteristicas.get(c).intValue(), 
					p.getSumaCaracteristica(c)
			);
		}
		
		//Test de cantidad competidores, caso personaje==1
		assertEquals(1, p.getCantidadCompetidores());		
	}
	
}