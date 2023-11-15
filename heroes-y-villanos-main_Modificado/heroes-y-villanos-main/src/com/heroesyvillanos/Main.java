package com.heroesyvillanos;

public class Main {
	public static void main(String[] args) {
        Juego juego = new Juego();
        juego.bienvenida();
        while(true) {
        	juego.menuPrincipal();
        }
	}
}