package com.heroesyvillanos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;

public class Juego {
	// El juego tiene una lista de personajes y de ligas, así como un menu
	private List<Personaje> personajes = new ArrayList<Personaje>();
	private List<Liga> ligas = new ArrayList<Liga>();
    private Menu menu = new Menu();
    
    // Rutas de los archivos de entrada para personajes y ligas
    private static final String pathPersonajesIn = "src/personajes.in";
    private static final String pathLigasIn = "src/ligas.in";
    
    /**
     * Muestra el mensaje de bienvenida en la interfaz de usuario, invocando el método
     * correspondiente en la instancia de la clase Menu.
     */
    public void bienvenida() {
    	menu.mostrarBienvenida();
    }
    
    /**
     * Presenta al usuario el menú principal con diferentes opciones y ejecuta la acción correspondiente
     * según la selección.
     */
    public void menuPrincipal(){
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuPrincipal");
    	
    	try {
    		switch(seleccion) {
		        case 1:
		            menuPersonajes();
		            break;
		        case 2:
		            menuLigas();
		            break;
		        case 3:
		            menuCombates();
		            break;
		        case 4:
		            menuReportes();
		            break;
		        case 5:
		        	System.exit(0);
		            break;
	    	}
		} catch (Exception e) {
			menu.mostrarTextoExcepcion(e);
		}
    }
    
    // ========== INICIO MENU PERSONAJES ==========

    /**
     * Presenta al usuario un menú relacionado con las operaciones de los personajes y ejecuta la acción seleccionada.
     * Las opciones incluyen cargar personajes desde un archivo, crear un nuevo personaje, listar los personajes existentes,
     * guardar los personajes en un archivo y regresar al menú principal.
     * @throws Exception Si ocurre algún error durante la ejecución.
     */
    private void menuPersonajes() throws Exception{
      	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuPersonajes");

          switch (seleccion) {
            case 1:
        		this.personajes = cargarPersonajesDesdeArchivo(pathPersonajesIn);
                break;
            case 2:
                crearPersonaje();
                break;
            case 3:
            	menu.mostrarTitulo("listarPersonajes");
            	listarPersonajes();
    	      break;
            case 4:
		    	guardarPersonajesEnArchivo(this.personajes, pathPersonajesIn);
                break;
            case 5:
                menuPrincipal();
                break;
          }
    }
    
    /**
     * Carga una lista de personajes desde un archivo específico.
     * 
     * @param path La ruta del archivo desde el cual cargar los personajes.
     * @return Una lista de personajes cargados desde el archivo.
     * @throws Exception Si hay errores al leer o procesar el archivo, o si se encuentra un formato incorrecto de datos.
     */
    private ArrayList<Personaje> cargarPersonajesDesdeArchivo(String path) throws Exception {
    	
		menu.mostrarTitulo("cargarPersonajes");
		
		// Inicializo array de personajes
		ArrayList<Personaje> listaPersonaje = new ArrayList<Personaje>();
		
		try (
			BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;
	            // La cabecera es leída pero la descartaremos       
	            line = br.readLine(); 
	            TipoCompetidor heovi = TipoCompetidor.VILLANO;
	            
	            while ((line = br.readLine()) != null) {
	            	String[] atributos = line.split(",");
	            	Map<Caracteristica, Integer> caract = new HashMap<Caracteristica, Integer>();
	            	
	            	// Mapeo de las características al HashMap
	            	caract.put(Caracteristica.VELOCIDAD, 	Integer.valueOf(atributos[3].trim()));
	            	caract.put(Caracteristica.FUERZA, 		Integer.valueOf(atributos[4].trim()));
	            	caract.put(Caracteristica.RESISTENCIA, 	Integer.valueOf(atributos[5].trim()));
	            	caract.put(Caracteristica.DESTREZA, 	Integer.valueOf(atributos[6].trim()));
	            	
	            	if (atributos[0].equals("Heroe")) {
	            		heovi = TipoCompetidor.HEROE;
	            	} else if (atributos[0].equals("Villano")) {
	            		heovi = TipoCompetidor.VILLANO;
	            	} else {
	            		menu.throwException("tipoCompetidor");
	            	}
	            	// Intentamos crear un personaje con todos los datos de la línea.
	            	Personaje p = new Personaje(atributos[1].trim(), atributos[2].trim(), caract, heovi);
	            	
	            	listaPersonaje.add(p);
	            }
	            
	            br.close();
		} catch (FileNotFoundException e) {	        	
			menu.throwException("fileNotFound", path);
		} catch (IOException e) {
			menu.throwException("io");
		}
		
		menu.mostrarFinal("cargarPersonajes");
	        
		return listaPersonaje;
	}
	
    /**
     * Permite la creación de un nuevo personaje con los datos ingresados por el usuario, incluyendo su nombre de fantasía, nombre real, tipo y características.
     * 
     * @throws Exception Si hay errores en la entrada de datos o si el nombre de fantasía ya está en uso por otro personaje.
     */
	private void crearPersonaje() throws Exception{
		boolean error = false;
		TipoCompetidor tipo;
		String nombreFantasia = "";
		String nombreReal = "";
		Map<Caracteristica, Integer> mapCaracteristicas = new HashMap<>();
		
		menu.mostrarTitulo("crearPersonaje");
		
		do {
			// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
			nombreFantasia = menu.registrarPalabra("crearPersonajeNombreFantasia", error);
			error = false;
			
			if(nombreFantasia.isBlank()) {
				error = true;
			}
			
			// Para cada personaje ya existente, controlamos que no exista uno con el mismo nombreFantasia
			if (!personajes.isEmpty()) {
				for (Personaje personaje : personajes) {
					if (personaje.getNombreFantasia().equals(nombreFantasia)) {
						error = true;
						break;
					}
				}
			}
		} while (error);
		
		do {
			// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
			nombreReal = menu.registrarPalabra("crearPersonajeNombreReal", error);
			error = false;
			if(nombreReal.isBlank()) {
				error = true;
			}
		} while (error);
		
		// Usamos true para indicarle a ingresoTipo que es un personaje
		tipo = menu.ingresoTipo(true);
		
		mapCaracteristicas = menu.registrarCaracteristicas();
	
		// Intentamos crear un personaje con todos los datos ingresados.
		personajes.add(new Personaje(nombreReal, nombreFantasia, mapCaracteristicas, tipo));
		
		menu.mostrarFinal("crearPersonaje");
	}
	
	/**
	 * Lista los personajes disponibles si hay alguno en la lista. En caso contrario, lanza una excepción indicando que la lista de personajes está vacía.
	 * 
	 * @throws Exception Si la lista de personajes está vacía, lo cual impide su listado.
	 */
	private void listarPersonajes() throws Exception {
		if (!personajes.isEmpty()) {
			menu.listarCompetidores(personajes);
		} else {
			menu.throwException("listaPersonajesVacia");
		}		
	}
	
	/**
	 * Guarda la lista de personajes en un archivo de texto con el formato específico.
	 * 
	 * @param lista La lista de personajes a guardar.
	 * @param path La ruta del archivo donde se guardarán los personajes.
	 * @throws Exception Si ocurre un error al escribir en el archivo.
	 */
	private void guardarPersonajesEnArchivo(List<Personaje> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarPersonajes");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			writer.write("Héroe/Villano, NombreReal, NombrePersonaje, Velocidad, Fuerza, Resistencia, Destreza");
			writer.newLine();
			for (Personaje personaje : lista) {
				writer.write(personaje.toFileLine());
				writer.newLine();
			}
        } catch (IOException e) {
        	menu.throwException("io");
        }
		menu.mostrarFinal("guardarPersonajes");
	}
	
	// ========== FIN MENU PERSONAJES - INICIO MENU LIGAS ==========

	/**
	 * Muestra y gestiona el menú relacionado con las ligas, ofreciendo opciones como cargar, crear, listar y guardar ligas,
	 * además de agregar competidores a una liga existente o regresar al menú principal.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución del menú de ligas.
	 */
	private void menuLigas() throws Exception {        
        int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuLigas");
        
        switch (seleccion) {
        case 1:
    		this.ligas = cargarLigasDesdeArchivo(this.personajes, pathLigasIn);
        	break;
        case 2:
            crearLiga();
            break;
        case 3:
			menu.mostrarTitulo("listarLigas");
			listarLigas();
            break;
        case 4:
			guardarLigasEnArchivo(this.ligas, pathLigasIn);
            break;
        case 5:
        	ingresaNombreLigaAgregaCompetidor(); 
            break;
        case 6:
        	menuPrincipal();
            break;
        }
    }
  
	/**
	 * Carga las ligas desde un archivo, generando instancias de ligas a partir de la información almacenada.
	 * Lee el archivo y crea ligas con los competidores correspondientes (personajes o ligas) en base a los nombres
	 * especificados en el archivo.
	 *
	 * @param listPersonaje La lista de personajes disponibles para generar las ligas.
	 * @param path          La ruta del archivo desde donde se cargarán las ligas.
	 * @return Una lista de instancias de Liga creadas a partir de la información del archivo.
	 * @throws Exception Si ocurre algún error durante la lectura del archivo o la creación de las ligas.
	 */
  	private ArrayList<Liga> cargarLigasDesdeArchivo(List<Personaje>listPersonaje, String path) throws Exception {
  		
		menu.mostrarTitulo("cargarLigas");
		ArrayList<Liga> listaLiga = new ArrayList<Liga>();
		
		boolean algunaLigaNoCreada = false;
		
        try (
        	BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            TipoCompetidor heovi = TipoCompetidor.VILLANO;
            
            while ((line = br.readLine()) != null) {   	
            	ArrayList<Competidor> competidores = new ArrayList<Competidor>();          	
            	String[] ligaString = line.split(",");
            	
            	for (String personajeNombre : ligaString) {
            		// Agrego los personajes
            		for (Personaje per : listPersonaje) {						
						if(per.getNombreFantasia().trim().equals(personajeNombre.trim() )) {
							heovi = per.isTipoCompetidor();
							competidores.add(per);
						}						
					}
            		// Agrego las ligas
            		for(Liga lig : listaLiga) {
            			if(lig.getNombre().trim().equals(personajeNombre.trim() )) {
            				heovi=lig.isTipoCompetidor();
							competidores.add(lig);
						}
            		}
				}
            	if(!competidores.isEmpty()) {
            		listaLiga.add(new Liga(ligaString[0], competidores, heovi));
            	} else {
            		algunaLigaNoCreada = true;
            	}
            }	            
        } catch (FileNotFoundException e) {	        	
        	menu.throwException("fileNotFound", path);     	
        } catch (IOException e) {
        	menu.throwException("io");
        }
        
        if (algunaLigaNoCreada) {
        	menu.mostrarError("cargarLigas");
		} else {
			menu.mostrarFinal("cargarLigas");
		}
        
		return listaLiga;
	}
		
  	/**
  	 * Crea una nueva liga, solicitando al usuario ingresar un nombre y un tipo para la misma.
  	 * Verifica la existencia de la liga con el mismo nombre y permite agregar personajes o ligas a la liga creada.
  	 *
  	 * @throws Exception Si ocurre algún error durante la creación de la liga o en la interacción con el usuario.
  	 */
  	private void crearLiga() throws Exception{
		String nombreLiga;
		TipoCompetidor tipoLiga;
		int indexLiga;
		boolean error = false;
		
		menu.mostrarTitulo("crearLiga");

		// La validación de las palabras que ingresa el usuario las realizamos en Juego con un do While.
		do {
			nombreLiga = menu.registrarPalabra("crearLigaNombreLiga", error);
			error = false;
			if (!validaCadena(nombreLiga) || existeLigaEnMemoria(nombreLiga)) {
				error = true;
			}
		} while (error);
		
		// Usamos false para indicarle a ingresoTipo que es una liga
		tipoLiga = menu.ingresoTipo(false);
		
		ligas.add(new Liga(nombreLiga, tipoLiga));
		
		menu.mostrarFinal("crearLiga");
		
		// El index de la liga recien creada es el último, lo usaremos si elegimos agregar competidores a esta misma liga
		indexLiga = ligas.size() - 1;

		// AGREGO PERSONAJE O LIGA
		int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("crearLigaAgregarCompetidor");
		
		switch (seleccion) {
		case 1:
			if (!personajes.isEmpty()) {
				agregaPersonajeALiga(indexLiga);
			}
			else {
				menu.throwException("listaPersonajesVacia");
			}
			break;
		case 2:
			if (ligas.size()>1 ) {  // No hay sólo una liga creada
				agregaLigaALiga(indexLiga);
			}
			else {
				menu.throwException("listaLigasVacia2");
			}
			break;
		case 3:
			menuLigas();
			break;
		}
	}
	
  	/**
  	 * Lista todas las ligas almacenadas, mostrándolas en pantalla si hay al menos una, 
  	 * de lo contrario, lanza una excepción indicando que la lista de ligas está vacía.
  	 *
  	 * @throws Exception Si la lista de ligas está vacía y no se pueden mostrar en pantalla.
  	 */
	private void listarLigas() throws Exception{
		if(!ligas.isEmpty()) {
			menu.listarCompetidores(ligas);
		} else {
			menu.throwException("listaLigasVacia");
		}
	}
	
	/**
	 * Guarda la lista de ligas en un archivo específico, mostrando un mensaje de inicio y finalización
	 * de la operación. Cada liga se escribe como una línea en el archivo utilizando su formato
	 * definido por el método "toFileLine()" de la clase Liga.
	 *
	 * @param lista La lista de ligas a ser guardada en el archivo.
	 * @param path La ruta del archivo donde se guardarán las ligas.
	 * @throws Exception Si ocurre un error durante la escritura del archivo o el archivo no puede ser accedido.
	 */
	private void guardarLigasEnArchivo(List<Liga> lista, String path) throws Exception {
		menu.mostrarTitulo("guardarLigas");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
			for (Liga liga : lista) {
				writer.write(liga.toFileLine());
				writer.newLine();
			}			
        } catch (IOException e) {
        	menu.throwException("io");
        }
		
		menu.mostrarFinal("guardarLigas");
	}
	
	/**
	 * Permite al usuario ingresar el nombre de la liga a la que desea agregar competidores.
	 * Después de seleccionar la liga, se llama al método 'seleccionaCompetidorAAgregar' para
	 * proceder con la adición de competidores a esa liga.
	 *
	 * @throws Exception Si ocurre un error durante la selección de la liga o la adición de competidores.
	 */
	private void ingresaNombreLigaAgregaCompetidor() throws Exception{
		
		menu.mostrarTitulo("ligaAgregarCompetidor");
		
		if (ligas.isEmpty()) {
			menu.throwException("listaLigasVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		seleccionaCompetidorAAgregar(seleccion - 1);
	}
		
	/**
	 * Permite seleccionar el tipo de competidor (personaje o liga) que se desea agregar a una liga específica,
	 * según el índice de la liga proporcionado.
	 *
	 * @param indexLiga Índice de la liga a la que se agregarán competidores.
	 * @throws Exception Si ocurre un error durante la selección del tipo de competidor o la adición a la liga.
	 */
	private void seleccionaCompetidorAAgregar(int indexLiga)  throws Exception{
		
		int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("agregarLigaSeleccionarCompetidor");
		
		switch (seleccion) {
		case 1:
			if (!personajes.isEmpty()) {
				agregaPersonajeALiga(indexLiga);
			}
			else {
				menu.throwException("listaPersonajesVacia");
			}
			break;
		case 2:
			if (ligas.size()>1 ) {  //la unica liga cargada es la actual
				agregaLigaALiga(indexLiga);
			}
			else {
				menu.throwException("listaLigasVacia");
			}
			break;
		case 3:
			menuLigas();
			break;
		}
	}
	
	/**
	 * Agrega un personaje seleccionado a una liga específica según el índice de la liga proporcionado.
	 *
	 * @param indexLiga Índice de la liga a la que se agregará el personaje.
	 * @throws Exception Si la lista de personajes está vacía o si ocurre un error durante la adición a la liga.
	 */
	private void agregaPersonajeALiga(int indexLiga) throws Exception {
		menu.mostrarTitulo("ligaAgregarPersonaje");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(personajes);
		
		// Intentamos agregar a la liga pasada por parámetro el personaje seleccionado
		ligas.get(indexLiga).agregarCompetidorALiga(personajes.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarPersonaje");
	}
	
	/**
	 * Agrega una liga seleccionada a otra liga específica según el índice de la liga proporcionado.
	 *
	 * @param indexLiga Índice de la liga a la que se agregará la liga seleccionada.
	 * @throws Exception Si la lista de ligas está vacía o si ocurre un error durante la adición a la liga.
	 */
	private void agregaLigaALiga(int indexLiga) throws Exception {
		menu.mostrarTitulo("ligaAgregarLiga");
		
		if (ligas.isEmpty()) {
			menu.throwException("listaLigasVacia");
		}
		
		int seleccion = menu.seleccionarCompetidores(ligas);
		
		
		// Intentamos agregar a la liga pasada por parámetro la liga seleccionada
		ligas.get(indexLiga).agregarCompetidorALiga(ligas.get(seleccion - 1));
		
		menu.mostrarFinal("ligaAgregarLiga");
	}
	
	/**
	 * Verifica si una cadena no está en blanco y no está vacía.
	 *
	 * @param s Cadena a validar.
	 * @return True si la cadena no está en blanco ni vacía, de lo contrario, False.
	 */
	private boolean validaCadena(String s) {
		if (s.isBlank() || s.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * Verifica si existe una liga en la memoria con un nombre específico (ignorando mayúsculas y minúsculas).
	 *
	 * @param s Nombre de la liga a buscar.
	 * @return True si existe una liga con el nombre especificado (ignorando mayúsculas y minúsculas), de lo contrario, False.
	 */
	private boolean existeLigaEnMemoria(String s) {
		for(int i=0;i<ligas.size();i++)	{
			if(ligas.get(i).getNombreLiga().equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
	
	// ========== FIN MENU LIGAS - INICIO MENU COMBATES==========
			
	/**
	 * Muestra el menú de opciones para los combates y realiza las acciones correspondientes según la selección del usuario.
	 *
	 * @throws Exception Si hay algún error al procesar las selecciones del menú.
	 */
	private void menuCombates() throws Exception{
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuCombates");
    	
    	switch (seleccion) {
	        case 1:
	        	menuRealizarCombate();
	            break;
	        case 2:
	        	mostrarReglas();
	            break;
	        case 3:
	            menuPrincipal();
	            break;
	    }
    }

	/**
	 * Presenta al usuario la selección de dos competidores y una característica para llevar a cabo un combate entre ellos.
	 * Luego, realiza el combate entre los dos competidores con la característica seleccionada.
	 *
	 * @throws Exception Si hay un error al procesar el combate.
	 */
    private void menuRealizarCombate() throws Exception{
    	// Seleccionamos 2 competidores y una característica, y los hacemos combatir
    	try {
    		menu.mostrarTitulo("realizarCombateComp1");
        	Competidor comp1 = seleccionarCompetidor();
        	menu.mostrarTitulo("realizarCombateComp2");
        	Competidor comp2 = seleccionarCompetidor();
        	Caracteristica car = seleccionarCaracteristica();
        	combatir(comp1, comp2, car);
		} catch (NullPointerException e) {
			// Sólo para el caso NullPointerException sabemos que la liga está vacía.
			// Otras excepciones llevarán el mensaje creado al lanzarlas y no se catchearan
			menu.throwException("combateLigaVacia");
		}
    }
    
    /**
     * Permite al usuario seleccionar un competidor (ya sea personaje o liga) para su uso en combates o comparaciones.
     *
     * @return El competidor seleccionado por el usuario.
     * @throws Exception Si ocurre un error durante la selección del competidor.
     */
    private Competidor seleccionarCompetidor() throws Exception {
    	int seleccionTipoCompetidor = 0;
    	int seleccionCompetidor = 0;

    	seleccionTipoCompetidor = menu.mostrarMenuConInstruccionOpciones("seleccionarTipoCompetidor");
    	
    	switch (seleccionTipoCompetidor) {
        case 1:
        	// Listamos y seleccionamos personaje, y mostramos la selección
        	menu.mostrarTitulo("listarPersonajes");
        	if (personajes.isEmpty()) {
    			menu.throwException("listaPersonajesVacia");
    		}
        	//listarPersonajes();
        	seleccionCompetidor = menu.seleccionarCompetidores(personajes);
        	menu.mostrarResultado("seleccionarPersonaje", 1, personajes.get(seleccionCompetidor - 1).toString());
            break;
        case 2:
        	// Listamos y seleccionamos liga, y mostramos la selección
        	menu.mostrarTitulo("listarLigas");
        	if (ligas.isEmpty()) {
    			menu.throwException("listaLigasVacia");
    		}
        	//listarLigas();
        	seleccionCompetidor = menu.seleccionarCompetidores(ligas);
        	menu.mostrarResultado("seleccionarLiga", 1, ligas.get(seleccionCompetidor - 1).toString());
            break;
    	}
    	
        return seleccionTipoCompetidor == 1 ? personajes.get(seleccionCompetidor - 1) : ligas.get(seleccionCompetidor - 1);
    }

    /**
     * Permite al usuario seleccionar una característica para utilizar en combates o comparaciones.
     *
     * @return La característica seleccionada por el usuario.
     */
    private Caracteristica seleccionarCaracteristica() {
    	Caracteristica[] caracteristicas = Caracteristica.values();
    	int seleccion = 0;
    	
    	seleccion = menu.seleccionarCaracteristica();
    	
        menu.mostrarResultado("seleccionarCaracteristica", 1, caracteristicas[seleccion - 1].toString());
        return caracteristicas[seleccion - 1];
    }
    
    /**
     * Realiza un combate entre dos competidores utilizando una característica específica para determinar el ganador.
     *
     * @param c1 El primer competidor.
     * @param c2 El segundo competidor.
     * @param car La característica utilizada para el combate.
     * @throws Exception si ocurre un error durante el combate.
     */
	private void combatir(Competidor c1, Competidor c2, Caracteristica car) throws Exception {
		
		menu.mostrarTitulo("combatir");
		
		int resultado = c1.esGanador(c2, car);

		if (resultado > 0) {
			menu.mostrarResultado("combatir", 1, c1.toString()); //gana c1
		} else if (resultado < 0) {
			menu.mostrarResultado("combatir", 1, c2.toString()); //gana c2
		} else {
			menu.mostrarResultado("combatir", 2, ""); //empate
		}
	}
	
	/**
	 * Muestra en la consola las reglas del juego.
	 */
	private void mostrarReglas() {
		menu.mostrarReglas();
	}
    
	// ========== FIN MENU COMBATES - INICIO MENU REPORTES==========

	/**
	 * Presenta un menú de opciones para realizar diversos reportes en el programa.
	 * Las opciones incluyen obtener vencedores, realizar ordenamientos y regresar al menú principal.
	 * 
	 * @throws Exception cuando se encuentra un error en la ejecución del programa.
	 */
    private void menuReportes() throws Exception {
    	int seleccion = menu.mostrarMenuConTituloOpcionesSeleccion("menuReportes");

        switch (seleccion) {
            case 1:
            	menuObtenerVencedoresContra();
                break;
            case 2:
                List<Caracteristica> criterios = seleccionarCriteriosOrdenamiento();
                boolean ascendente = seleccionarAscendente();
                listadoOrdenadoPorCaracteristica(criterios, ascendente);
                break;
            case 3:
                menuPrincipal();
                break;
        }
    }
    
    /**
     * Muestra un menú para obtener los competidores que vencen a un personaje específico en una característica determinada.
     * Selecciona un personaje y una característica, luego muestra los competidores que superan al personaje en la característica.
     * 
     * @throws Exception cuando ocurre un error durante la ejecución del programa.
     */
	private void menuObtenerVencedoresContra() throws Exception {
		Caracteristica caracteristicaEvaluativa;
		List<Caracteristica> caracteristicas;
		Personaje personajeAEvaluar;
		
		menu.mostrarTitulo("obtenerVencedoresContra");
		
		if (personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		
		// Seleccionamos un personaje
		int seleccionPersonaje = menu.seleccionarCompetidores(personajes);
		
    	personajeAEvaluar = personajes.get(seleccionPersonaje - 1);
    	
       	if (personajeAEvaluar == null) {
    		menuReportes();
    		return;
    	}
    	
    	menu.mostrarTitulo("seleccionarCaracteristica");
    	// Seleccionamos una característica
    	caracteristicas = menuSeleccionarCaracteristica(null);
    	
    	if (caracteristicas.isEmpty()) {
    		menuReportes();
    		return;
    	}
    	else {
    		caracteristicaEvaluativa = caracteristicas.get(0);
    	}
		
		menu.mostrarFinal("obtenerVencedoresContra");
		
		// Obtenemos la lista de competidores que lo vencen
		List<Competidor> venc = obtenerVencedoresContra(personajeAEvaluar, caracteristicaEvaluativa);
		if (venc.isEmpty()) {
			menu.throwException("listaVencedoresVacia");
		}
		menu.listarCompetidores(venc);
    }
    
	/**
	 * Obtiene los competidores que vencen a un competidor dado en una característica específica.
	 * 
	 * @param retador      Competidor para el que se buscan los vencedores.
	 * @param caracteristica Característica en la que se evalúa la competencia.
	 * @return Lista de competidores que vencen al retador en la característica especificada.
	 * @throws Exception cuando ocurre un error durante la ejecución del programa.
	 */
	private List<Competidor> obtenerVencedoresContra(Competidor retador, Caracteristica caracteristica) throws Exception {
		List<Competidor> contrincantes = new ArrayList<Competidor>();
		List<Competidor> vencedores = new ArrayList<Competidor>();
		
		TipoCompetidor tipoRetador = retador.getTipoCompetidor();
		
		// Consideramos todas las ligas y personajes
		contrincantes.addAll(ligas);		
		contrincantes.addAll(personajes);
		contrincantes.removeIf(c -> (tipoRetador == c.getTipoCompetidor()));

		// Para cada contrincante vemos si le gana a nuestro retador
		for (Competidor contrincante : contrincantes) {
			// Si es del mismo tipo, no lo consideramos
			if (contrincante.getTipoCompetidor() == retador.getTipoCompetidor()) {
				break;
			}
			if (contrincante.esGanador(retador, caracteristica) > 0) {
				vencedores.add(contrincante);
			}
		}
		return vencedores;
	}
	
	/**
	 * Permite al usuario seleccionar los criterios de ordenamiento, comenzando con la elección de una característica
	 * y ofreciendo la opción de agregar más características.
	 *
	 * @return Una lista de Caracteristicas seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> seleccionarCriteriosOrdenamiento() {
		List<Caracteristica> criterios = new LinkedList<Caracteristica>();
		
		menu.mostrarTitulo("seleccionarCriterios");
		
		// Primero seleccionamos una característica
		criterios = menuSeleccionarCaracteristica(criterios);
		if (criterios.isEmpty()) {
			return criterios;
		}
		// Podemos agregar más características o no hacerlo
		criterios = menuAgregarCaracteristica(criterios);
		return criterios;
	}
	
	/**
	 * Permite al usuario seleccionar una Característica, agregándola a la lista de criterios si aún no está presente.
	 *
	 * @param criterios La lista actual de Características seleccionadas como criterios de ordenamiento.
	 * @return La lista actualizada de Características seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> menuSeleccionarCaracteristica(List<Caracteristica> criterios) {
    	Caracteristica caracteristicaEvaluativa = null;
    	
    	if (criterios == null) {
    		criterios = new LinkedList<Caracteristica>();
    	}
    	// Seleccionamos característica
    	caracteristicaEvaluativa = seleccionarCaracteristica();
    	
		if (criterios.indexOf(caracteristicaEvaluativa) >= 0) {
			// Característica ya ingresada a los criterios
			menu.mostrarError("seleccionarCaracteristica2");
			caracteristicaEvaluativa = null;
		}
		else {
			criterios.add(caracteristicaEvaluativa);
		}
		return criterios;
    }
    
	/**
	 * Permite al usuario agregar múltiples Características a la lista de criterios de ordenamiento.
	 *
	 * @param criterios La lista actual de Características seleccionadas como criterios de ordenamiento.
	 * @return La lista actualizada de Características seleccionadas para el ordenamiento.
	 */
	private List<Caracteristica> menuAgregarCaracteristica (List<Caracteristica> criterios) {
		boolean agregarCaracteristica = true;
		
		// Podemos hacerlo tantas veces como queramos
		// Una vez que agregamos las n características que existansiempre dará error hasta que elijamos parar de agregar
		while(agregarCaracteristica) {
			int seleccion = menu.mostrarMenuConInstruccionOpciones("agregarCaracteristica");
			switch (seleccion) {
				case 1: 
					criterios = menuSeleccionarCaracteristica(criterios);
					break;
				case 2:
					agregarCaracteristica = false;
					break;
			}
		}
		
		return criterios;
	}
	
	/**
	 * Permite al usuario seleccionar si el ordenamiento debe ser ascendente o no.
	 *
	 * @return Booleano que indica si el ordenamiento debe ser ascendente (true) o descendente (false).
	 */
	private boolean seleccionarAscendente() {
		int seleccion = menu.mostrarMenuConInstruccionOpciones("seleccionarAscendente");
		return (seleccion == 1) ? true : false;
	}
	
	/**
	 * Genera un listado de competidores ordenados por las características especificadas, de manera ascendente o descendente.
	 *
	 * @param criterios   Lista de características por las que se va a ordenar.
	 * @param ascendente  Indica si el ordenamiento será ascendente (true) o descendente (false).
	 * @throws Exception  Si la lista de criterios está vacía o nula, o si la lista de personajes es nula o está vacía.
	 */
	private void listadoOrdenadoPorCaracteristica(List<Caracteristica> criterios, boolean ascendente) throws Exception{
		if (criterios == null || criterios.isEmpty()) {
			// Si no hay criterios, usamos el orden por defecto de las características notificando al usuario
			menu.mostrarError("listadoOrdenado");
			criterios = new LinkedList<Caracteristica>();
			criterios.addAll(new Ordenamiento().getOrdenCaracteristicas());
		}
		List<Competidor> personajesOrdenados = new ArrayList<Competidor>();
		// Instancia de Ordenamiento
		Ordenamiento orden = new Ordenamiento();
		orden.setearOrdenCaracteristicas(criterios);
		
		if (personajes == null || personajes.isEmpty()) {
			menu.throwException("listaPersonajesVacia");
		}
		// Añadimos y ordenamos
		personajesOrdenados.addAll(personajes);
		Collections.sort(personajesOrdenados, orden);
		if (!ascendente) {
			Collections.reverse(personajesOrdenados);
		}
		menu.listarCompetidores(personajesOrdenados);
	}
	
	// ========== FIN MENU REPORTES ==========
}

