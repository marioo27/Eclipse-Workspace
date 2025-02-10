package many2many_bidir_avanz_jpa;

import java.util.Objects;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

//Esta clase representa la PK compuesta

@Embeddable // Con esta etiqueta indicamos que esta clase no es independiente sino que estará dentro
			// de otra clase
public class ClaveProtagonista implements Serializable {

	private int codigoPelicula;
	private int codigoActor;

	public ClaveProtagonista() {

	}

	public ClaveProtagonista(int codigoPelicula, int codigoActor) {
		this.codigoPelicula = codigoPelicula;
		this.codigoActor = codigoActor;
	}

	public int getCodigoPelicula() {
		return codigoPelicula;
	}

	public void setCodigoPelicula(int codigoPelicula) {
		this.codigoPelicula = codigoPelicula;
	}

	public int getCodigoActor() {
		return codigoActor;
	}

	public void setCodigoActor(int codigoActor) {
		this.codigoActor = codigoActor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoActor, codigoPelicula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaveProtagonista other = (ClaveProtagonista) obj;
		return codigoActor == other.codigoActor && codigoPelicula == other.codigoPelicula;
	}
}
