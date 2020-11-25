package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import org.bson.Document;
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
	
	
	public void borrarVueloComprado(String codigo_vuelo) throws IOException {

			MongoCollection<Document> coleccionDeVuelos = mongoDatabase.getCollection(tableVuelos);
			Document doc = new Document("codigo", codigo_vuelo);
			coleccionDeVuelos.deleteOne(doc);
			System.out.println("Vuelo cancelado correctamente");
			//System.out.println(coleccionDeVuelos.toString());
			//System.out.println(codigo_vuelo);
			//coleccionDeVuelos.updateOne(Filters.eq("codigo", getCodigo_vuelo()), Updates.addToSet("plazas disponibles", plazas_disponibles.getPlazasDisponibles()+1));
			
		
	}
	

	
	
	
	
	

}
