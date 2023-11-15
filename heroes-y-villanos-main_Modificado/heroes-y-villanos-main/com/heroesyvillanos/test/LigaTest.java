package com.heroesyvillanos.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.heroesyvillanos.*;

class LigaTest {

	@Test
	void testConstructorNormal() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo);
		
		List<Competidor> competidores = new ArrayList<Competidor>();
		competidores.add(comp_1);
		competidores.add(comp_2);
		
		try {
			new Liga(nombreLiga, tipo);
			new Liga(nombreLiga, competidores, tipo);
		} 
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testExceptionConstructor() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		TipoCompetidor tipo2 = TipoCompetidor.VILLANO;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo2);
		
		List<Competidor> competidores = new ArrayList<Competidor>();
		competidores.add(comp_1);
		competidores.add(comp_2);
		
		try {
			//Constructor con personajes de distinto tipo
			new Liga(nombreLiga, competidores, tipo);
		} 
		catch (Exception e) {
			return;
		}
		fail();
	}
	
	@Test
	void testAgregarCompetidorALiga() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo);
		Competidor comp_3 = new Personaje("cap", "cap-america", caracteristicas, tipo);
		Competidor comp_4 = new Personaje("bruce", "batman", caracteristicas, tipo);
		List<Competidor> competidores = new ArrayList<Competidor>();
		competidores.add(comp_1);
		competidores.add(comp_2);
		
		try {
			Liga liga = new Liga(nombreLiga, tipo);
			liga.agregarCompetidorALiga(comp_1);
			liga.agregarCompetidorALiga(comp_2);
			
			Liga ligaDeLigas = new Liga(nombreLiga, tipo);
			ligaDeLigas.agregarCompetidorALiga(liga);
			ligaDeLigas.agregarCompetidorALiga(comp_3);
			ligaDeLigas.agregarCompetidorALiga(comp_4);
			
		}
		catch(Exception e) {
			fail();
		}
		
	}
	
	@Test
	void testExceptionAgregarCompetidorDistintoTipoALiga() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		TipoCompetidor tipo2 = TipoCompetidor.VILLANO;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		
		try {
			Liga liga = new Liga(nombreLiga, tipo2);
			liga.agregarCompetidorALiga(comp_1);
		}
		catch(Exception e) {
			return;
		}
		fail();
	}
	
	@Test
	void testMethods() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo);
		Competidor comp_3 = new Personaje("cap", "cap-america", caracteristicas, tipo);
		Competidor comp_4 = new Personaje("bruce", "batman", caracteristicas, tipo);
		
		try {
			Liga liga = new Liga(nombreLiga, tipo);
			liga.agregarCompetidorALiga(comp_1);
			liga.agregarCompetidorALiga(comp_2);
			
			Liga ligaDeLigas = new Liga(nombreLiga, tipo);
			ligaDeLigas.agregarCompetidorALiga(liga);
			ligaDeLigas.agregarCompetidorALiga(comp_3);
			ligaDeLigas.agregarCompetidorALiga(comp_4);
			
			Integer cantPersonajes = ligaDeLigas.getCantidadCompetidores();
			Integer valueSumaFuerza = ligaDeLigas.getSumaCaracteristica(Caracteristica.FUERZA);
			Integer valuePromedioFuerza = ligaDeLigas.getPromedioCaracteristica(Caracteristica.FUERZA);
			
			assertEquals(cantPersonajes, 4);
			assertEquals(valueSumaFuerza, 40);
			assertEquals(valuePromedioFuerza, 10);
		}
		catch(Exception e) {
			fail();
		}
	}
	
	@Test
	void testLucha() {
		String nombreLiga = "Marvel";		
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		TipoCompetidor tipo2 = TipoCompetidor.VILLANO;
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		Map<Caracteristica, Integer> caracteristicas2 = Map.ofEntries(
				Map.entry(Caracteristica.VELOCIDAD, 5),
				Map.entry(Caracteristica.FUERZA, 20),
				Map.entry(Caracteristica.RESISTENCIA, 10),
				Map.entry(Caracteristica.DESTREZA, 10)
			);
		
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo);
		Competidor comp_3 = new Personaje("thanos", "thanos", caracteristicas2, tipo2);
		Competidor comp_4 = new Personaje("thanos", "thanos", caracteristicas, tipo2);
		
		try {
			Liga liga = new Liga(nombreLiga, tipo);
			liga.agregarCompetidorALiga(comp_1);
			liga.agregarCompetidorALiga(comp_2);
			
			int valuePeleaFuerza = liga.esGanador(comp_3, Caracteristica.FUERZA);
			int valuePeleaVelocidad = liga.esGanador(comp_3, Caracteristica.VELOCIDAD);
			//Tener en cuenta que es en lista circular la comparacion de caracteristicas
			int valuePeleaEmpate = comp_1.esGanador(comp_4, Caracteristica.RESISTENCIA);
			
			assertTrue(valuePeleaFuerza < 0);
			assertTrue(valuePeleaVelocidad > 0);
			assertTrue(valuePeleaEmpate == 0);
			
		}
		catch(Exception e) {
			fail();
		}
	}

	@Test
	void testExceptionLuchaMismosTipos() {
		TipoCompetidor tipo = TipoCompetidor.HEROE;		
		Map<Caracteristica, Integer> caracteristicas = Map.ofEntries(
			Map.entry(Caracteristica.VELOCIDAD, 10),
			Map.entry(Caracteristica.FUERZA, 10),
			Map.entry(Caracteristica.RESISTENCIA, 10),
			Map.entry(Caracteristica.DESTREZA, 10)
		);
		Competidor comp_1 = new Personaje("peter", "spider-man", caracteristicas, tipo);
		Competidor comp_2 = new Personaje("tony", "iron-man", caracteristicas, tipo);
	
		try {
			comp_1.esGanador(comp_2, Caracteristica.FUERZA);
		}
		catch(Exception e) {
			return;
		}
		fail();
	}

}
