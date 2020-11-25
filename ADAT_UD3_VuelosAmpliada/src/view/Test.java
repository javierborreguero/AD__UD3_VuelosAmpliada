package view;

import java.io.IOException;
import java.util.Map.Entry;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Random;
import java.util.Scanner;
import controller.Controlador;
import model.Vendido;
import model.Vuelo;

public class Test {
	private Scanner sc;
	private Controlador mControlador;
	private String access, codigoVuelo;
	MongoCollection<Document> coll;
	MongoDatabase mongodb;
	MongoClient cl;
	public Test() {
		sc = new Scanner(System.in);
		mControlador = new Controlador();
	}

	public static void main(String[] args) {
		Test mTest = new Test();
		mTest.run();
	}

	private void run() {
		try {
			conexionEstalecida();
		} catch (IOException e) {
			System.err.println("EEROR: Se ha producido en la entrada de datos");
		}

	}

	private boolean conexionEstalecida() throws IOException {
		access = "Mongo";
		mControlador.conexionMongo(access);
		elegirOperacion();
		return true;
	}

	private void elegirOperacion() throws IOException {
		boolean seleccionado = false;
		do {
			System.out.println(
					"\nQue quieres hacer\n1. Comprar un vuelo\n2. Cancelar un vuelo\n3. Modificar un vuelo comprado\n4. Salir");
			int elegirOperacion = sc.nextInt();
			switch (elegirOperacion) {
			case 1:
				comprarVuelo();
				break;
			case 2:
				cancelarVuelo();
				break;
			case 3:
				System.out.println("Introduce el DNI a cambiar");
				String id = sc.next();
				sc.nextLine();
				System.out.println("Introduce el codigo de venta a cambiar");
				int codv = sc.nextInt();
				sc.nextLine();
				Vendido v2 = new Vendido();
				System.out.println("Para poder efectuar la compra primero necesitamos sus datos.\n");
				System.out.print("Escribe tu nombre ");
				v2.setNombre(sc.next());
				System.out.print("Escribe tu primer apellido ");
				v2.setApellido(sc.next());
				System.out.print("Escribe tu DNI ");
				v2.setDni(sc.next());
				System.out.print("Escribe el DNI de la persona que va a pagar el vuelo ");
				v2.setDniPagador(sc.next());
				System.out.print("Por último, indique el número de tarjeta con la que se va a pagar ");
				v2.setNumeroTarjeta(sc.next());
				sc.nextLine();
				updateData(id,codv, v2);
				break;
			case 4:
				System.out.println("CERRANDO");
				System.exit(-1);
				break;
			default:
				System.err.println("ERROR: La opción introducida no es valida. Elige una opción entre 1-4");
				elegirOperacion();
				break;
			}
		} while (seleccionado);

	}

	/*------------------- MÉTODOS PARA COMPRAR -------------------*/

	private void comprarVuelo() throws IOException {
		sc.nextLine();
		// Se gestiona la compra del vuelo
		// Primero se muestran los vuelos
		System.out.println("En nuestra base de datos tenemos registrados los siguientes vuelos");
		leer();
		// Después se pide al usuario que eliga el vuelo que quiere comprar en función
		// del codigo de vuelo;
		System.out.print("Escribe el codigo del vuelo que quieres comprar ");
		String elegirCodigoVuelo = sc.nextLine();

		for (Entry<String, Vuelo> entry : mControlador.read().entrySet()) {
			if (elegirCodigoVuelo.equalsIgnoreCase(entry.getValue().getCodigo_vuelo())) {
				System.out.println("Has elegido el vuelo " + entry.getValue().getCodigo_vuelo() + " con origen en "
						+ entry.getValue().getOrigen() + " y destino en " + entry.getValue().getDestino() + "\n");
				// Se guarda el codigo del vuelo para poesteriormente alamcenar la info nueva en
				// el array
				codigoVuelo = entry.getValue().getCodigo_vuelo();
				guardarDatos(codigoVuelo);
			}

		}

	}

	private void guardarDatos(String codigoVuelo) throws IOException {

		// El siguiente paso es pedir los datos del comprador (nombre, dni etc.)
		System.out.println("Para poder efectuar la compra primero necesitamos sus datos.\n");
		System.out.print("Escribe tu nombre ");
		String nombre = sc.nextLine();
		System.out.print("Escribe tu primer apellido ");
		String apellido = sc.nextLine();
		System.out.print("Escribe tu DNI ");
		String dni = sc.nextLine();
		System.out.print("Escribe el DNI de la persona que va a pagar el vuelo ");
		String dniPagador = sc.nextLine();
		System.out.print("Por último, indique el número de tarjeta con la que se va a pagar ");
		String numeroTarjeta = sc.nextLine();
		if (!nombre.equals("") && !apellido.equals("") && !dni.equals("") && !dniPagador.equals("")
				&& !numeroTarjeta.equals("")) {

			/*----- Se gener un codigo de venta aleatorio -----*/
			char[] cadenaCaracteres = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			StringBuilder sbPrimeraParte = new StringBuilder();
			StringBuilder sbSegundaParte = new StringBuilder();
			Random random = new Random();
			int numero;
			String cadenaPrincipal = "";
			for (int i = 0; i < 2; i++) {
				char letrasPrimeraParte = cadenaCaracteres[random.nextInt(cadenaCaracteres.length)];
				char letrasSegundaParte = cadenaCaracteres[random.nextInt(cadenaCaracteres.length)];
				sbPrimeraParte.append(letrasPrimeraParte);
				sbSegundaParte.append(letrasSegundaParte);
				numero = (int) (random.nextDouble() * 99 + 1000);
				cadenaPrincipal = sbPrimeraParte.toString() + numero + sbSegundaParte.toString();
			}
			// Cuando el billete ha sido comprado, la cantidad de plazas disponibles
			// disminuye
			int cogerPlazasTotales = 0;
			int cogerPlazasDisponibles = 0;
			int asgianrNumeroAsiento = 0;
			int updatePlazasDisponibles = 0;
			for (Entry<String, Vuelo> entry : mControlador.read().entrySet()) {
				if (codigoVuelo.equalsIgnoreCase(entry.getValue().getCodigo_vuelo())) {
					cogerPlazasTotales = entry.getValue().getPlazas_totales();
					cogerPlazasDisponibles = entry.getValue().getPlazas_disponibles();
					int calcularNumeroAsinto = cogerPlazasTotales - cogerPlazasDisponibles;
					asgianrNumeroAsiento = calcularNumeroAsinto + 1;
					updatePlazasDisponibles = cogerPlazasDisponibles - 1;
				}
			}
			String codigoVenta = cadenaPrincipal.toUpperCase();
			// Los datos del usuario son almacenados
			Vendido mVendido = new Vendido(asgianrNumeroAsiento, dni, apellido, nombre, dniPagador, numeroTarjeta,
					codigoVenta);
			// Esta variables sirve para comprobar que el codigo del vuelos corresponde con
			// el introducido
			String checkCodigoVuelo = codigoVuelo;
			// Se guarda la información
			Vuelo mVuelo = new Vuelo(checkCodigoVuelo, mVendido, updatePlazasDisponibles);
			if (mControlador.insertar(checkCodigoVuelo, mVuelo)) {
				// Si todo ha ido bien, se muetsra al usuario el codigo de venta y el asiento
				// que le han asignado
				System.out.println("La información ha sido almacenada correctamente");
				System.out.println("\nSe ha generado el siguiete codigo venta: " + codigoVenta);

			} else {
				System.out.println("No se ha podido almacenar la información");
			}

			/*-----------------------------------------------------------------------------------------------------*/
		} else {
			System.err.println("ERROR: Todos los datos deben estar completos");
		}
	}

	private void leer() throws IOException {
		int contador = 1;
		for (Entry<String, Vuelo> entry : mControlador.read().entrySet()) {
			System.out.println("<----- Vuelo " + contador + " ----->");
			System.out.println("Codigo del vuelo: " + entry.getValue().getCodigo_vuelo());
			System.out.println("Origen: " + entry.getValue().getOrigen());
			System.out.println("Destino: " + entry.getValue().getDestino());
			System.out.println("Hora: " + entry.getValue().getHora());
			System.out.println("Fecha: " + entry.getValue().getFecha());
			System.out.println("Numero de plazas totales: " + entry.getValue().getPlazas_totales());
			System.out.println("Numero de plazas disponibles: " + entry.getValue().getPlazas_disponibles());
			System.out.println("-----------------------\n");
			contador++;
		}
	}
	/*--------------------------------------*/

	/*------------------- MÉTODOS PARA CANCELAR/BORRAR -------------------*/
	private void cancelarVuelo() throws IOException {
		
		sc.nextLine();
		System.out.println("Quieres cancelar un vuelo que has comprado, necesitamos los datos para poder cancelarlo");
		System.out.println("Introduce el codigo de vuelo que desea cancelar: ");
		String codigo_vuelo = sc.nextLine();
		System.out.println("Introduce el dni: ");
		String dni = sc.nextLine();
		System.out.println("Introduce el codigo de venta:");
		String codigoVenta= sc.nextLine();
		mControlador.borrar(codigo_vuelo);
		
		
	
	}

	/*------------------- MÉTODOS PARA MODIFICAR -------------------*/
	public void updateData(String id, String codv, Vendido vuelos, String codigoVuelo) {
		try {
			cl = new MongoClient("localhost", 27017);
			mongodb = cl.getDatabase("adat_vuelos2_0");
			coll = mongodb.getCollection("vuelos");
			Document codVuelo=new Document("codigo_vuelo",codigoVuelo);
			if (coll.find(codVuelo) != null) {
				Document doc = new Document("dni", id);
				doc.append("codigoVenta", codv);
				Document doc1 = new Document();
				JSONObject obj = new JSONObject();
				obj.put("id", vuelos.getDni());
				obj.put("nombre", vuelos.getNombre());
				obj.put("apellido", vuelos.getApellido());
				obj.put("dniPagador", vuelos.getDniPagador());
				obj.put("tarjeta", vuelos.getNumeroTarjeta());
				obj.put("codigoVenta", vuelos.getCodigoVenta());
				JSONArray arr = new JSONArray();
				arr.add(obj);
				doc1.append("ventas", arr);
				Document aux = new Document("$set", doc1);
				coll.updateOne(doc, aux);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
