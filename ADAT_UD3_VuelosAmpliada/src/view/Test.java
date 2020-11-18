package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import controller.AccesoMongo;
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
				break;
			case 3:
				break;
			case 4:
				System.out.println("CERRANDO");
				System.exit(-1);
				break;
			default:
				break;
			}
		} while (seleccionado);

	}

	private void comprarVuelo() throws IOException {
		// Se gestiona la compra del vuelo
		// Primero se muestran los vuelos
		HashMap<String, Vuelo> leerVuelo = mControlador.read();
		int contador = 1;
		for (Entry<String, Vuelo> entry : leerVuelo.entrySet()) {
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
}
