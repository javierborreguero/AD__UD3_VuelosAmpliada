package controller;

import java.io.IOException;
import java.util.HashMap;

import model.Vuelo;

/* Interfaz que gestiona las operaciones en funcion dek acceso a datos seleccionado*/
public interface IAccesoDatos {
	// Leer
	public HashMap<String, Vuelo> leerVuelo() throws IOException;

}
