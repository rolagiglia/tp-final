package com.heroesyvillanos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Menu {
	
	private Scanner scanner = new Scanner(System.in);
	private static final Locale es_AR = new Locale("es", "AR");
	private static final ResourceBundle bundle = ResourceBundle.getBundle("resources.text", es_AR);
	
	/**
	 * Muestra un menú con un título y opciones de selección en la consola, y espera que el usuario elija una opción.
	 * Utiliza el ResourceBundle (bundle) para obtener los textos del menú según el nombre especificado.
	 *
	 * @param nombreMenu El nombre del menú para cargar las opciones desde el ResourceBundle.
	 * @return La opción seleccionada por el usuario, ya validada para corresponder a una de las mostradas.
	 */
	public int mostrarMenuConTituloOpcionesSeleccion(String nombreMenu){
		ArrayList<String> opciones = new ArrayList<String>();
		String titulo = new String();
		String seleccion = new String();
		String error = new String();
		
		try {
			titulo = bundle.getString(nombreMenu + ".titulo");
		} catch (MissingResourceException e) {
			titulo = "";
		}
		
		boolean continuar = true;
		int i = 1;
		// Agregamos opciones en tanto haya en el bundle, cuando falle la opción n dejamos de buscar
		while(continuar) {
			try {
				opciones.add(bundle.getString(nombreMenu + ".opcion" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		try {
			opciones.add(bundle.getString(nombreMenu + ".salir"));
		} catch (MissingResourceException e) {
			//no agregar salir, no hacer nada
		}
		
		try {
			seleccion = bundle.getString(nombreMenu + ".seleccion");
		} catch (MissingResourceException e) {
			seleccion = "";
		}
		
		try {
			error = bundle.getString(nombreMenu + ".error");
		} catch (MissingResourceException e) {
			error = "";
		}
		
		
		mostrarMenuEnConsola(titulo, opciones, seleccion);
		
		int opcion = 0;
		continuar = true;
		
		// La opción no puede ser 0, y sólo serán válidos los input que coincidan con opciones en pantalla
        while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
        
        return opcion;
		
	}
	
	/**
	 * Muestra un menú con una instrucción y opciones de selección en la consola, y espera que el usuario elija una opción.
	 * Utiliza el ResourceBundle (bundle) para obtener los textos del menú según el nombre especificado.
	 *
	 * @param nombreMenu El nombre del menú para cargar las opciones desde el ResourceBundle.
	 * @return La opción seleccionada por el usuario, ya validada para corresponder a una de las mostradas.
	 */
	public int mostrarMenuConInstruccionOpciones(String nombreMenu){
		ArrayList<String> opciones = new ArrayList<String>();
		String instruccion = new String();
		String error = new String();
		
		try {
			instruccion = bundle.getString(nombreMenu + ".instruccion");
		} catch (MissingResourceException e) {
			instruccion = "";
		}
		
		boolean continuar = true;
		int i = 1;
		// Agregamos opciones en tanto haya en el bundle, cuando falle la opción n dejamos de buscar
		while(continuar) {
			try {
				opciones.add(bundle.getString(nombreMenu + ".opcion" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		
		try {
			error = bundle.getString(nombreMenu + ".error");
		} catch (MissingResourceException e) {
			error = "";
		}
		
		mostrarMenuEnConsola(instruccion, opciones);
		
		int opcion = 0;
		continuar = true;
		
		// La opción no puede ser 0, y sólo serán válidos los input que coincidan con opciones en pantalla
        while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
        
        return opcion;
		
	}
	
	/**
	 * Muestra en la consola un menú con un título, una lista de opciones y una indicación de selección.
	 *
	 * @param titulo     El título del menú a mostrar en la consola.
	 * @param opciones   La lista de opciones del menú a mostrar en la consola.
	 * @param seleccion  La indicación de selección del menú a mostrar en la consola.
	 */
	private void mostrarMenuEnConsola(String titulo, ArrayList<String> opciones, String seleccion) {
		animarTexto("");
		animarTexto(titulo);
		for(String opcion : opciones) {
			animarTexto(opcion);
		}
		animarTexto(seleccion);
		
	}
	
	/**
	 * Muestra en la consola un menú con una instrucción y una lista de opciones.
	 *
	 * @param instruccion La instrucción o mensaje a mostrar antes de las opciones del menú.
	 * @param opciones    La lista de opciones del menú a mostrar en la consola.
	 */
	private void mostrarMenuEnConsola(String instruccion, ArrayList<String> opciones) {
		animarTexto("");
		animarTexto(instruccion);
		for(String opcion : opciones) {
			animarTexto(opcion);
		}
		
	}
	
	/**
	 * Muestra un texto específico en la consola.
	 *
	 * @param s El texto a mostrar en la consola.
	 */
	private void mostrarTextoEnConsola(String s) {
		animarTexto("");
		animarTexto(s);
		
	}
	
	/**
	 * Muestra un texto específico en la consola sin introducir líneas adicionales.
	 *
	 * @param s El texto a mostrar en la consola sin separación.
	 */
	private void mostrarTextoEnConsolaSinSeparacion(String s) {
		animarTexto(s);
		
	}
	
	/**
	 * Lee la entrada del usuario y verifica si coincide con alguna de las opciones presentadas.
	 *
	 * @param opciones La lista de opciones a verificar.
	 * @return El número correspondiente a la opción seleccionada por el usuario. Si es 0, no hay coincidencias o la entrada es inválida.
	 */
	private int registrarOpcion(ArrayList<String> opciones){
		// Leemos el input del usuario y validamos si alguna opción de las mostradas matchea
		String op = scanner.nextLine();
		for(String opcion : opciones) {
			if (op != "" && opcion.startsWith(op)) {
				// Siempre las opciones comienzan con número y punto así que realizamos un split
				String[] splitOpcion = opcion.split("\\.");
				if (splitOpcion.length > 0) {
		            int numeroOpcion = Integer.parseInt(splitOpcion[0]);
		            // Incluso si el usuario escribe el texto de la opción completa, retornamos siempre el int que corresponde
		            return numeroOpcion;
				}
			}
		}
		return 0;
	}

	/**
	 * Solicita al usuario el ingreso de una palabra, mostrando una instrucción y un mensaje de error si corresponde.
	 *
	 * @param nombreMenu El nombre del menú para cargar las instrucciones desde el ResourceBundle.
	 * @param error      Indica si se debe mostrar un mensaje de error (true) o no (false).
	 * @return La palabra ingresada por el usuario.
	 */
	public String registrarPalabra(String nombreMenu, boolean error) {
		
		String instruccion = new String();
		String err = new String();
		
		try {
			instruccion = bundle.getString(nombreMenu + ".instruccion");
		} catch (MissingResourceException e) {
			instruccion = "";
		}
		
		if (error) {
			try {
				err = bundle.getString(nombreMenu + ".error");
			} catch (MissingResourceException e) {
				err = "";
			}
			
			mostrarTextoEnConsola(err);
		}
		
		mostrarTextoEnConsola(instruccion);
		String nombre = registrarString();
		return nombre;
	}
	
	/**
	 * Lee la entrada del usuario y devuelve el texto ingresado.
	 *
	 * @return El texto ingresado por el usuario.
	 */
	private String registrarString() {
		String s = scanner.nextLine();
		return s;
	}
	
	/**
	 * Solicita al usuario la selección del tipo de competidor, ya sea héroe o villano, a través de un menú de opciones.
	 *
	 * @param personaje Indica si se trata de la creación de un personaje (true) o una liga (false).
	 * @return El tipo de competidor seleccionado: héroe o villano.
	 */
	public TipoCompetidor ingresoTipo(boolean personaje) {
		TipoCompetidor tipo = TipoCompetidor.HEROE;
		int seleccion = 0;
		
		// Varía la instrucción dependiendo de si era un Personaje o una Liga
		if (personaje) {
			seleccion = mostrarMenuConInstruccionOpciones("crearPersonajeTipo");
		} else {
			seleccion = mostrarMenuConInstruccionOpciones("crearLigaTipo");
		}
		
		if (seleccion == 1) {
			tipo = TipoCompetidor.HEROE;
		} else {
			tipo = TipoCompetidor.VILLANO;
		}
		
		return tipo;
	}

	/**
	 * Muestra en la consola el mensaje asociado a una excepción dada.
	 *
	 * @param e La excepción de la cual se mostrará el mensaje.
	 */
	public void mostrarTextoExcepcion(Exception e) {
		mostrarTextoEnConsola(e.getMessage());
		
	}

	/**
	 * Muestra en la consola el título de un menú obtenido del ResourceBundle.
	 *
	 * @param nombreMenu El nombre del menú para cargar el título desde el ResourceBundle.
	 */
	public void mostrarTitulo(String nombreMenu) {
		String s = new String();
		
		try {
			s = bundle.getString(nombreMenu + ".titulo");
		} catch (MissingResourceException e) {
			s = "";
		}
		
		mostrarTextoEnConsola(s);
	}
	
	/**
	 * Muestra en la consola el mensaje final de un menú obtenido del ResourceBundle.
	 *
	 * @param nombreMenu El nombre del menú para cargar el mensaje final desde el ResourceBundle.
	 */
	public void mostrarFinal(String nombreMenu) {
		String s = new String();
		
		try {
			s = bundle.getString(nombreMenu + ".final");
		} catch (MissingResourceException e) {
			s = "";
		}
		
		mostrarTextoEnConsola(s);
	}

	/**
	 * Permite al usuario registrar valores para distintas características de un personaje, mostrando un menú y solicitando valores enteros positivos.
	 *
	 * @return Un mapeo de características (Caracteristica) con sus valores (Integer) ingresados por el usuario.
	 */
	public Map<Caracteristica, Integer> registrarCaracteristicas() {
		Map<Caracteristica, Integer> caracteristicasValores = new HashMap<>();
		Caracteristica[] caracteristicas = Caracteristica.values();		
		String titulo = new String();
		
		try {
			titulo = bundle.getString("registrarCaracteristica.titulo");
		} catch (MissingResourceException e) {
			titulo = "";
		}
		
		mostrarTextoEnConsola(titulo);
		
		int valor = 0;
		boolean error;
		
		// Para cada característica, registramos su valor, que debe ser entero positivo
		for (int i = 0; i < caracteristicas.length; i++) {
			error = false;
			do {
				valor = registrarValorCaracteristica((i+1) + "- " + caracteristicas[i] + ". ", error);
				error = false;
				if (valor > 0) {
					caracteristicasValores.put(caracteristicas[i], valor);
				} else {
					error = true;
				}
			} while (error);
			
		}
		return caracteristicasValores;
	}
	
	/**
	 * Muestra en la consola una lista numerada de las distintas características disponibles para un personaje.
	 */
	public void listarCaracteristicas() {
		Caracteristica[] caracteristicas = Caracteristica.values();
    	for(int i = 0; i < caracteristicas.length; i++) {
    		mostrarTextoEnConsolaSinSeparacion((i + 1) + "- " + caracteristicas[i]);
    	}
	}
	
	/**
	 * Permite al usuario seleccionar una característica de una lista predefinida, mostrando un menú de opciones numeradas.
	 *
	 * @return La opción numerada seleccionada por el usuario, correspondiente a una característica.
	 */
	public int seleccionarCaracteristica() {
		
		ArrayList<String> opciones = new ArrayList<>();
		int opcion = 0;
		String instruccion = new String();
		String error = new String();
		
		try {
			instruccion = bundle.getString("seleccionarCaracteristica.instruccion");
		} catch (MissingResourceException e) {
			instruccion = "";
		}
		
		try {
			error = bundle.getString("seleccionarCaracteristica.error");
		} catch (MissingResourceException e) {
			error = "";
		}
		
		int i = 0;
		
		// Listamos todas las características
		for (Caracteristica caracteristica : Caracteristica.values()) {
		    opciones.add((i+1) + ". " + caracteristica.name());
		    mostrarTextoEnConsolaSinSeparacion((i+1) + ". " + caracteristica.name());
		    i++;
		}
		
		mostrarTextoEnConsola(instruccion);
		
		// La opción no puede ser 0, y sólo serán válidos los input que coincidan con opciones en pantalla
		boolean continuar = true;
		while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
		
		return opcion;
	}

	/**
	 * Permite al usuario registrar el valor de una característica, mostrando una instrucción y, opcionalmente, un mensaje de error.
	 *
	 * @param caracteristicaTexto El texto que describe la característica a la que se refiere la instrucción.
	 * @param error               Indica si se debe mostrar un mensaje de error (true) o no (false).
	 * @return El valor numérico ingresado por el usuario para la característica.
	 */
	private int registrarValorCaracteristica(String caracteristicaTexto, boolean error) {
		String instruccion = new String();
		String err = new String();
		
		try {
			instruccion = bundle.getString("registrarCaracteristica.instruccion");
		} catch (MissingResourceException e) {
			instruccion = "";
		}
		
		if (error) {
			try {
				err = bundle.getString("registrarCaracteristica.error");
			} catch (MissingResourceException e) {
				err = "";
			}
			
			mostrarTextoEnConsola(err);
		}
		
		mostrarTextoEnConsola(caracteristicaTexto + instruccion);
		
		// Si el valor no es numérico, será 0, lo cual provocará error y que se vuelva a llamar este método
		String val = scanner.nextLine();
		int numeroValor = 0;
		if (!val.isEmpty()) {
			try {
				numeroValor = Integer.parseInt(val);
			} catch (Exception e) {
				numeroValor = 0;
			}
		}
		return numeroValor;
	}

	/**
	 * Muestra en la consola el mensaje de bienvenida al juego obtenido del ResourceBundle.
	 */
	public void mostrarBienvenida() {
		String titulo = bundle.getString("bienvenida.titulo");
		
		try {
			titulo = bundle.getString("bienvenida.titulo");
		} catch (MissingResourceException e) {
			titulo = "";
		}
		
		mostrarTextoEnConsola(titulo);
		
	}

	/**
	 * Muestra en la consola una lista numerada de competidores (admite tanto personajes como ligas).
	 *
	 * @param competidores La lista de competidores a ser mostrada en la consola.
	 */
	public void listarCompetidores(List<? extends Competidor> competidores) {
		for (int i = 0; i < competidores.size(); i++) {
			mostrarTextoEnConsolaSinSeparacion((i+1) + "- " + competidores.get(i));
		}
		
	}

	/**
	 * Permite al usuario seleccionar un competidor de una lista proporcionada, mostrando un menú de opciones numeradas. Admite tanto personajes como ligas.
	 *
	 * @param competidores La lista de competidores entre los que el usuario puede elegir.
	 * @return El número correspondiente al competidor seleccionado por el usuario.
	 */
	public int seleccionarCompetidores(List<? extends Competidor> competidores) {
		ArrayList<String> opciones = new ArrayList<String>();
		int opcion = 0;
		String instruccion = new String();
		String error = new String();
		
		try {
			instruccion = bundle.getString("ligaAgregarCompetidor.instruccion");
		} catch (MissingResourceException e) {
			instruccion = "";
		}
		
		try {
			error = bundle.getString("ligaAgregarCompetidor.error");
		} catch (MissingResourceException e) {
			error = "";
		}
		
		boolean continuar = true;
		
		// Listamos los competidores
		for (int i = 0; i < competidores.size(); i++) {
			mostrarTextoEnConsolaSinSeparacion((i+1) + ". " + competidores.get(i));
			opciones.add((i+1) + ". " + competidores.get(i).toString());
		}
		
		mostrarTextoEnConsola(instruccion);
		
		// La opción no puede ser 0, y sólo serán válidos los input que coincidan con opciones en pantalla
		while(continuar) {
        	continuar = false;
        	opcion = registrarOpcion(opciones);

            if (opcion == 0) {
            	mostrarTextoEnConsola(error);
				continuar = true;
			}
        }
		
		return opcion;
	}

	/**
	 * Muestra en la consola un resultado correspondiente a una acción realizada, utilizando un mensaje del ResourceBundle.
	 *
	 * @param nombreMenu El nombre del menú para cargar el mensaje de resultado desde el ResourceBundle.
	 * @param i          El número asociado al resultado específico.
	 * @param s2         El mensaje o información adicional a mostrar junto con el resultado.
	 */
	public void mostrarResultado(String nombreMenu, int i, String s2) {
		
		String s1 = new String();
		
		try {
			s1 = bundle.getString(nombreMenu + ".resultado" + i);
		} catch (MissingResourceException e) {
			s1 = "";
		}
		
		mostrarTextoEnConsola(s1 + s2);
		
	}

	/**
	 * Muestra en la consola las reglas del juego, recuperándolas del ResourceBundle y presentándolas en bloques numerados.
	 */
	public void mostrarReglas() {
		boolean continuar = true;
		int i = 1;
		
		// Bloque 1
		while(continuar) {
			try {
				mostrarTextoEnConsolaSinSeparacion(bundle.getString("mostrarReglasBloque1.l" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
		
		// Caracteristicas
		listarCaracteristicas();
		
		continuar = true;
		i = 1;
		// Bloque 2
		while(continuar) {
			try {
				mostrarTextoEnConsolaSinSeparacion(bundle.getString("mostrarReglasBloque2.l" + i));
				i++;
			} catch (MissingResourceException e) {
				continuar = false;
			}
		}
	}

	/**
	 * Muestra en la consola un mensaje de error recuperado del ResourceBundle.
	 *
	 * @param nombreMenu El nombre del menú para cargar el mensaje de error desde el ResourceBundle.
	 */
	public void mostrarError(String nombreMenu) {
		String s = new String();
		try {
			s = bundle.getString(nombreMenu + ".error");
		} catch (MissingResourceException e) {
			s = "";
		}
		
		mostrarTextoEnConsola(s);
	}
	
	/**
	 * Lanza una excepción con un mensaje recuperado del ResourceBundle, según el nombre del menú proporcionado.
	 *
	 * @param nombreMenu El nombre del menú para cargar el mensaje de excepción desde el ResourceBundle.
	 * @throws Exception La excepción con el mensaje correspondiente.
	 */
	public void throwException(String nombreMenu) throws Exception{
		
		String s = new String();
		
		try {
			s = bundle.getString(nombreMenu + ".exception");
		} catch (MissingResourceException e) {
			s = "Error";
		}

		throw new Exception(s);
	}
	
	/**
	 * Lanza una excepción con un mensaje obtenido del ResourceBundle, combinado con una cadena adicional, según el nombre del menú proporcionado.
	 *
	 * @param nombreMenu El nombre del menú para cargar el mensaje de excepción desde el ResourceBundle.
	 * @param s2         La cadena adicional que se combina con el mensaje de la excepción.
	 * @throws Exception La excepción con el mensaje combinado.
	 */
	public void throwException(String nombreMenu, String s2) throws Exception{
		
		String s1 = new String();
		
		try {
			s1 = bundle.getString(nombreMenu + ".exception");
		} catch (MissingResourceException e) {
			s1 = "Error";
		}

		throw new Exception(s1 + s2);
	}
	
	/**
	 * Muestra el texto de forma animada en la consola, imprimiendo los caracteres con pausas entre ellos.
	 *
	 * @param text El texto a animar en la consola.
	 */
	private void animarTexto(String text) {
		
		if (text.isBlank()) {
			try{
		        Thread.sleep(100);//pausa entre caracteres
		    }catch(InterruptedException ex){
		        Thread.currentThread().interrupt();
		    }
		}
		int i;
		for(i = 0; i < text.length(); i++){
		    System.out.printf("%c", text.charAt(i));
		    try{
		        Thread.sleep(2);//pausa entre caracteres
		    }catch(InterruptedException ex){
		        Thread.currentThread().interrupt();
		    }
		}
		System.out.printf("%n");
	}

}
