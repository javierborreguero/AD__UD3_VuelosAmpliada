package view;

import java.io.IOException;
import java.util.Map.Entry;

import java.util.Random;
import java.util.Scanner;

import controller.Controlador;
import model.Vuelo;

public class Test {
	private Scanner sc;
	private Controlador mControlador;
	private String access;

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
				modificarVuelo();
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
				guardarDatos();
			}

		}

	}

	private void guardarDatos() {

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

			/*----- Se gener un codigo de venta aleatorio (esto es de prueba, hay que pasarlo al controlador) -----*/
			char[] cadenaCaracteres = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			StringBuilder sbPrimeraParte = new StringBuilder();
			StringBuilder sbSegundaParte = new StringBuilder();
			Random random = new Random();
			int numero;
			String cadenaPrincipal = ""; // Inicializamos la Variable/
			for (int i = 0; i < 2; i++) {
				char letrasPrimeraParte = cadenaCaracteres[random.nextInt(cadenaCaracteres.length)];
				char letrasSegundaParte = cadenaCaracteres[random.nextInt(cadenaCaracteres.length)];
				sbPrimeraParte.append(letrasPrimeraParte);
				sbSegundaParte.append(letrasSegundaParte);
				numero = (int) (random.nextDouble() * 99 + 1000);
				cadenaPrincipal = sbPrimeraParte.toString() + numero + sbSegundaParte.toString();
			}
			String codigoVuelo = cadenaPrincipal.toUpperCase();
			System.out.println("Se ha generado el siguiente codigo de vuelo " + codigoVuelo);

			// TODO INSERTAR CAMBIOD EN EL ARRAY VENDIDOS
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
	private void cancelarVuelo() {
		// TODO En este método se trabajará la opción de cancelar un vuelo

	}

	/*------------------- MÉTODOS PARA MODIFICAR -------------------*/
	private void modificarVuelo() {
		// TODO Auto-generated method stub

	}
}
