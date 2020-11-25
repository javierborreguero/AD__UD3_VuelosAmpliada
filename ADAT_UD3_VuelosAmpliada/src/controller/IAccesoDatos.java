package controller;

import java.io.IOException;
import java.util.HashMap;

import model.Vuelo;

/* Interfaz que gestiona las operaciones en funcion dek acceso a datos seleccionado*/
public interface IAccesoDatos {
	// Leer
	public HashMap<String, Vuelo> leerVuelo() throws IOException;

	// Añadir
	public boolean inseetarVueloVendido(String codigoVuelo, Vuelo nuevo);


	boolean borrarVueloComprado(String Id) throws IOException;
}
