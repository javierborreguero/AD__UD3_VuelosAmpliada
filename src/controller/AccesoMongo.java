package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import model.Vendido;
import model.Vuelo;

public class AccesoMongo implements IAccesoDatos {
	MongoClient mongoConnection;
	private String ip, dabase_name, tableVuelos;
	private int port;
	Properties mongoConnectionProperties;
	private MongoDatabase mongoDatabase;
	MongoCollection<Document> coll;
	MongoDatabase mongodb;
	MongoClient cl;

	public AccesoMongo(String archivo) {
		mongoConnectionProperties = new Properties();
		try {
			mongoConnectionProperties.load(new FileReader(archivo));
			ip = mongoConnectionProperties.getProperty("ip");
			dabase_name = mongoConnectionProperties.getProperty("dabase_name");
			port = Integer.parseInt(mongoConnectionProperties.getProperty("port"));
			tableVuelos = mongoConnectionProperties.getProperty("vuelos");
			mongoConnection = new MongoClient(ip, port);
			mongoDatabase = (MongoDatabase) mongoConnection.getDatabase(dabase_name);
			if (mongoConnection != null) {
				System.out.println("ACCESO A DATOS - MONGO");
			} else {
				System.out.println("Error: Conexión no establecida");
				System.exit(-1);
			}
		} catch (IOException e) {
			System.out.println("ERROR: Se ha producido un error en la entrada/salida de datos " + e);
			System.exit(-1);
		}

	}

	@Override
	public HashMap<String, Vuelo> leerVuelo() throws IOException {
		HashMap<String, Vuelo> leerVuelos = new HashMap<String, Vuelo>();
		MongoCollection<Document> coleccionDeVuelos = mongoDatabase.getCollection(tableVuelos);

		for (Document document : coleccionDeVuelos.find()) {
			String codigo = document.getString("codigo");
			String origen = document.getString("origen");
			String destino = document.getString("destino");
			String fecha = document.getString("fecha");
			String hora = document.getString("hora");
			String stringPlazasTotales = String.valueOf(document.get("plazas_totales").toString());
			double plazastotalesToDouble = Double.parseDouble(stringPlazasTotales);
			int finalPlazasTotales = (int) plazastotalesToDouble;
			String stringPlazasDisponibles = String.valueOf(document.get("plazas_disponibles").toString());
			double plazasDisponiblesToDouble = Double.parseDouble(stringPlazasDisponibles);
			int finalPlazasDisponibles = (int) plazasDisponiblesToDouble;
			Vuelo mVuelo = new Vuelo(codigo, origen, destino, fecha, hora, finalPlazasTotales, finalPlazasDisponibles);
			leerVuelos.put(codigo, mVuelo);

		}
		return leerVuelos;
	}

	@Override
	public boolean inseetarVueloVendido(String codigoVuelo, Vuelo nuevo) {
		// Se actualizan las plazas disponibles en el vuelo
		MongoCollection<Document> coleccionDeVuelos = mongoDatabase.getCollection(tableVuelos);
		BasicDBObject searchFlight = new BasicDBObject();
		searchFlight.append("codigo", nuevo.getCodigo_vuelo());
		BasicDBObject updateFLight = new BasicDBObject();
		updateFLight.append("$set", new BasicDBObject().append("plazas_disponibles", nuevo.getPlazas_disponibles()));
		coleccionDeVuelos.updateOne(searchFlight, updateFLight);
		// Se añaden los datos relativos al (asiento, dni, apellido, nombre, dniPagador,
		// tarjeta, codigoVenta)
		Document newVendido = new Document().append("asiento", nuevo.getVendidos().getAsiento())
				.append("dni", nuevo.getVendidos().getDni()).append("apellido", nuevo.getVendidos().getApellido())
				.append("nombre", nuevo.getVendidos().getNombre())
				.append("dniPagador", nuevo.getVendidos().getDniPagador())
				.append("tarjeta", nuevo.getVendidos().getNumeroTarjeta())
				.append("codigoVenta", nuevo.getVendidos().getCodigoVenta());
		coleccionDeVuelos.updateOne(Filters.eq("codigo", nuevo.getCodigo_vuelo()),
				Updates.addToSet("vendidos", newVendido));

		return true;
	}

	public void borrarVueloComprado(String codigo_vuelo, String dni, String codigoVenta) throws IOException {

		MongoCollection<Document> coleccionDeVuelos = mongoDatabase.getCollection(tableVuelos);
		Document quienCambio = new Document("codigo", codigo_vuelo);
		Document billeteDel = new Document("dni", dni);
		billeteDel.append("codigoVenta", codigoVenta);
		Document cambios = new Document("vendidos", billeteDel);
		Document auxPull = new Document("$pull", cambios);
		// System.out.println(auxPull.toJson());
		coleccionDeVuelos.updateOne(quienCambio, auxPull);

		// Al eliminar los vuelos se aumenta una plaza disponible

		Document aumentPlaza = new Document("plazas_disponibles", 1);
		Document auxInc = new Document("$inc", aumentPlaza);
		coleccionDeVuelos.updateOne(quienCambio, auxInc);
		System.out.println("Vuelo cancelado correctamente");

	}

	public void updateData(String id, String codv, Vendido vuelos, String codigoVuelo) {
		try {
			MongoCollection<Document> coleccionDeVuelos = mongoDatabase.getCollection(tableVuelos);
			Document codVuelo = new Document("codigo", codigoVuelo);
			if (coleccionDeVuelos.find(codVuelo) != null) {
				Document doc1 = new Document();
				JSONObject obj = new JSONObject();
				obj.put("asiento", vuelos.getAsiento());
				obj.put("dni", vuelos.getDni());
				obj.put("nombre", vuelos.getNombre());
				obj.put("apellido", vuelos.getApellido());
				obj.put("dniPagador", vuelos.getDniPagador());
				obj.put("tarjeta", vuelos.getNumeroTarjeta());
				obj.put("codigoVenta", codv);
				JSONArray arr = new JSONArray();
				arr.add(obj);
				doc1.append("vendidos", arr);
				Document aux = new Document("$set", doc1);
				coleccionDeVuelos.updateOne(codVuelo, aux);
			}

		} catch (Exception e) {
			System.err.println("ERROR: Lo sentimos ha habido al actualizar los datos");
		}
	}

}
