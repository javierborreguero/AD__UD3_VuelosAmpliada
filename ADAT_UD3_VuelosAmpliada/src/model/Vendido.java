package model;

public class Vendido {
	private int asiento;
	private String dni;
	private String apellido;
	private String nombre;
	private String dniPagador;
	private String numeroTarjeta;
	private String codigoVenta;

	public Vendido(int asiento, String dni, String apellido, String nombre, String dniPagador, String numeroTarjeta,
			String codigoVenta) {
		super();
		this.asiento = asiento;
		this.dni = dni;
		this.apellido = apellido;
		this.nombre = nombre;
		this.dniPagador = dniPagador;
		this.numeroTarjeta = numeroTarjeta;
		this.codigoVenta = codigoVenta;
	}

	public int getAsiento() {
		return asiento;
	}

	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDniPagador() {
		return dniPagador;
	}

	public void setDniPagador(String dniPagador) {
		this.dniPagador = dniPagador;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(String codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	@Override
	public String toString() {
		return "Vendidos [asiento=" + asiento + ", dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre
				+ ", dniPagador=" + dniPagador + ", numeroTarjeta=" + numeroTarjeta + ", codigoVenta=" + codigoVenta
				+ "]";
	}

}
