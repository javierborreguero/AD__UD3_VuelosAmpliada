package model;

/**
 * f Clase que me sirve para como entidad tanto para fichero como para la base
 * de datos
 */
public class Vuelo {
	private String id;
	private String codigo_vuelo;
	private String origen;
	private String destino;
	private String fecha;
	private String hora;
	private int plazas_totales;
	private int plazas_disponibles;
	private Vendido vendidos;

	public Vuelo(String codigo_vuelo, String origen, String destino, String fecha, String hora, int plazas_totales,
			int plazas_disponibles) {

		this.codigo_vuelo = codigo_vuelo;
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.hora = hora;
		this.plazas_totales = plazas_totales;
		this.plazas_disponibles = plazas_disponibles;

	}

	public Vuelo(String codigo_vuelo, Vendido vendidos, int plazas_disponibles) {
		this.codigo_vuelo = codigo_vuelo;
		this.vendidos = vendidos;
		this.plazas_disponibles = plazas_disponibles;
	}

	public Vuelo() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo_vuelo() {
		return codigo_vuelo;
	}

	public void setCodigo_vuelo(String codigo_vuelo) {
		this.codigo_vuelo = codigo_vuelo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getPlazas_totales() {
		return plazas_totales;
	}

	public void setPlazas_totales(int plazas_totales) {
		this.plazas_totales = plazas_totales;
	}

	public int getPlazas_disponibles() {
		return plazas_disponibles;
	}

	public void setPlazas_disponibles(int plazas_disponibles) {
		this.plazas_disponibles = plazas_disponibles;
	}

	public Vendido getVendidos() {
		return vendidos;
	}

	public void setVendidos(Vendido vendidos) {
		this.vendidos = vendidos;
	}

	@Override
	public String toString() {
		return "Vuelos [id=" + id + ", codigo_vuelo=" + codigo_vuelo + ", origen=" + origen + ", destino=" + destino
				+ ", fecha=" + fecha + ", hora=" + hora + ", plazas_totales=" + plazas_totales + ", plazas_disponibles="
				+ plazas_disponibles + "]";
	}

}