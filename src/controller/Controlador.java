package controller;

import java.io.IOException;
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Vendido;
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
	

	public void borrar(String codigo_vuelo, String  dni, String codigoVenta) throws IOException {
		boolean ok = false;
		accesoDatos.borrarVueloComprado(codigo_vuelo, dni, codigoVenta);
		ok = true;
		

	}

	public void updateData(String id, String codv, Vendido vuelos, String codigoVuelo) throws IOException {
		boolean ok = false;
		accesoDatos.updateData(id, codv, vuelos, codigoVuelo);
		ok = true;
	}
	
	
	

}
