package com.heroesyvillanos.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.heroesyvillanos.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OrdenamientoTest {
	
	private Personaje crearCompetidor(String nombre, String fantasia, Object... caracteristicas) {
	    Map<Caracteristica, Integer> mapaCaracteristicas = new HashMap<>();
	    for (int i = 0; i < caracteristicas.length; i += 2) {
	        mapaCaracteristicas.put((Caracteristica) caracteristicas[i], (Integer) caracteristicas[i + 1]);
	    }
	    return new Personaje(nombre, fantasia, mapaCaracteristicas, TipoCompetidor.HEROE);
	}
	
	@Test
	void testCompareDistintosConDistintasCaracteristicas() {
	    Personaje competidor1 = crearCompetidor("Nombre1", "Fantasia1", Caracteristica.DESTREZA, 1, Caracteristica.VELOCIDAD, 5, Caracteristica.RESISTENCIA, 1, Caracteristica.FUERZA, 1);
	    Personaje competidor5 = crearCompetidor("Nombre5", "Fantasia5", Caracteristica.DESTREZA, 5, Caracteristica.VELOCIDAD, 5, Caracteristica.RESISTENCIA, 5, Caracteristica.FUERZA, 5);

	    Ordenamiento ordenamiento = new Ordenamiento();

	    int resultado = ordenamiento.compare(competidor1, competidor5);

	    assertTrue(resultado < 0, "Se esperaba que competidor5 sea considerado mayor que competidor1");
	}
    
    @Test
    void testCompareIguales() {
    	Personaje competidor1 = crearCompetidor("Nombre1", "Fantasia1", Caracteristica.DESTREZA, 1, Caracteristica.VELOCIDAD, 5, Caracteristica.RESISTENCIA, 1, Caracteristica.FUERZA, 1);
	    Personaje competidor5 = crearCompetidor("Nombre5", "Fantasia5", Caracteristica.DESTREZA, 1, Caracteristica.VELOCIDAD, 5, Caracteristica.RESISTENCIA, 1, Caracteristica.FUERZA, 1);

        Ordenamiento ordenamiento = new Ordenamiento();

        int resultado = ordenamiento.compare(competidor1, competidor5);

        assertTrue(resultado == 0, "Se esperaba que competidor1 y competidor5 empaten");
    }

    @Test
    void testSetearOrdenCaracteristicas() {
        Ordenamiento ordenamiento = new Ordenamiento();
        ordenamiento.setearOrdenCaracteristicas(Arrays.asList(Caracteristica.FUERZA, Caracteristica.VELOCIDAD));

        List<Caracteristica> resultadoEsperado = Arrays.asList(Caracteristica.FUERZA, Caracteristica.VELOCIDAD);
        assertEquals(resultadoEsperado, ordenamiento.getOrdenCaracteristicas(), "El orden de las características debería ser [FUERZA, VELOCIDAD]");
    }
}
