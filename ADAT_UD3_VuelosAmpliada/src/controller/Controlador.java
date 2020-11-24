package controller;

import java.io.IOException;
import java.util.HashMap;
import model.Vuelo;

public class Controlador {
	private String mongoDBConfigurationFile;
	IAccesoDatos accesoDatos;

	public Controlador() {
		mongoDBConfigurationFile = "file/mongoConnection.properties";

	}

	public void conexionMongo(String access) {
		accesoDatos = new AccesoMongo(mongoDBConfigurationFile);
	}

	/* ------------ LEER ------------ */
	public HashMap<String, Vuelo> read() throws IOException {
		HashMap<String, Vuelo> verInfoVuelos = accesoDatos.leerVuelo();
		return verInfoVuelos;
	}

	public boolean insertar(String codigoVuelo, Vuelo vuelo) throws IOException {
		boolean ok = false;
		accesoDatos.inseetarVueloVendido(codigoVuelo, vuelo);
		ok = true;
		return ok;

	}

}
