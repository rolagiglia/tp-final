package com.heroesyvillanos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Liga extends Competidor {
	private String nombreLiga;
	private List<Competidor> competidores; // Puede contener personajes y ligas
	private Map<Caracteristica, Integer> cache_promedio_caracteristicas;
	
	// Constructor
	public Liga(String nombre, TipoCompetidor tipo) throws Exception{
		if(!esNombreValido(nombre)) {
			throw new Exception("Nombre invalido");
		}
		
		this.tipoCompetidor = tipo;

		this.nombreLiga = nombre;
		this.competidores = new ArrayList<Competidor>();
		this.cache_promedio_caracteristicas = new HashMap<Caracteristica, Integer>();
		for (Caracteristica c : Caracteristica.values())    //caracteristicas en 0 
			cache_promedio_caracteristicas.put(c, 0);
	}
	
	// Constructor
	public Liga(String nombre, List<Competidor> competidores, TipoCompetidor tipo) throws Exception{
		if(!esNombreValido(nombre)) {
			throw new Exception("Nombre invalido");
		}
		
		for (Competidor c : competidores) {
			if(c.tipoCompetidor != tipo) {
				throw new Exception("No se puede agregar un personaje/liga a una liga con distintos tipos de competidor");
			}
		}
		
		this.tipoCompetidor = tipo;
		this.nombreLiga = nombre;
		this.competidores = competidores;
		this.cache_promedio_caracteristicas = new HashMap<Caracteristica, Integer>();
		
		this.updateCacheCaracteristicas();
	}
	
	public String getNombreLiga() {
		return this.nombreLiga;
	}
	
	public void agregarCompetidorALiga(Competidor c) throws Exception {
		if(!tipoCompetidor.equals(c.tipoCompetidor)) {
			throw new Exception("No se puede agregar un personaje/liga a una liga con distintos tipos de competidor"); 
		}
		if(this.equals(c)){  // esta misma liga
			throw new Exception("Esta es la misma liga a la que esta agregando competidores!"); 
		}
		if(!c.puedeEntrarEnLiga()){
			throw new Exception("Este personaje ya pertenece a una liga");
		}
		for(Competidor comp : c.getCompetidores()) {
			if(comp.equals(this)) {
				throw new Exception("La liga que quieres agregar ya incluye a esta liga");
			}
		}
		
		competidores.add(c);
		c.setEstaDentroDeLiga(true);
		this.updateCacheCaracteristicas();
	}
	
	@Override
	public int getPromedioCaracteristica(Caracteristica c) throws NullPointerException{
		return cache_promedio_caracteristicas.get(c);
	}
	
	private void updateCacheCaracteristicas() {
		int cantComp = this.getCantidadCompetidores();
		if(cantComp!=0) {
			for (Caracteristica c : Caracteristica.values()) {
				int value = this.getSumaCaracteristica(c) / cantComp;
				cache_promedio_caracteristicas.put(c, value);
			}
		}
	}
	
	@Override
	public int getCantidadCompetidores() {
		int sum = 0;
		if(!this.competidores.isEmpty())  {
			for(Competidor comp : competidores) {
				sum += comp.getCantidadCompetidores();
			}
		}
		return sum;
	}
	
	public int getSumaCaracteristica(Caracteristica c) {
		int sum = 0;
		if(!this.competidores.isEmpty())  {
			for(Competidor comp : competidores) {
				sum += comp.getSumaCaracteristica(c);
			}
		}
		return sum;
	}
	
	public List<Competidor> getCompetidores() {		
		List<Competidor> aux_competidores = new ArrayList<Competidor>();
		for(Competidor comp : competidores) {
			if(comp instanceof Liga) {
				aux_competidores.add(comp);				
			}
			aux_competidores.addAll(comp.getCompetidores());	
		}
		return aux_competidores;
	}
	
	@Override
	public String toString() {
		return nombreLiga;
	}
	
	// Getters y Setters
	public String getNombre() {
		return nombreLiga;
	}
	
	public void setCompetidores(List<Competidor> competidores) {
		this.competidores = competidores;
	}
	
	public TipoCompetidor isTipoCompetidor() {
		return tipoCompetidor;
	}
	
	@Override
	protected String getNombreParaArchivo() {
		return nombreLiga;
	}

	public String toFileLine() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.nombreLiga);
		for (Competidor c : competidores) {
			sb.append(", " + c.getNombreParaArchivo());
		}
		return sb.toString();
	}
}
