package one2one;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "one2one.Cliente")
@Table(name = "customer_details", schema = "empresa_orm_2324")
public class DetallesCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "customer_id")
	private int idCliente;

	@Column(name = "address")
	private String direccion;

	@Column(name = "phone_number")
	private String numTel;

	@OneToOne
	@MapsId
	@JoinColumn(name = "customer_id")
	private Cliente cliente;

	public DetallesCliente() {

	}

	public DetallesCliente(String direccion, String numTel) {
		super();
		this.direccion = direccion;
		this.numTel = numTel;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallesCliente other = (DetallesCliente) obj;
		return idCliente == other.idCliente;
	}

	@Override
	public String toString() {
		return "Direccion" + direccion + ", Numero de telefono:" + numTel + "]";
	}

}
