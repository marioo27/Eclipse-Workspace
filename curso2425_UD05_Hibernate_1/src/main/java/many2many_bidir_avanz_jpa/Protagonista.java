package many2many_bidir_avanz_jpa;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

//Este escenario many2many lo transformamos en dos relaciones one2many/many2one

@Entity(name = "many2many_bidir_avanz_jpa.Protagonista")
@Table(name = "protagonistas")
public class Protagonista implements Serializable {

	@EmbeddedId
	private ClaveProtagonista clave;

	@Column(name = "es_protagonista")
	private boolean esProta;

	@ManyToOne
	@MapsId("codigoActor") //El codigo de actor de esta instancia actor debe coincidir con el valor de ClaveProtagonista
	@JoinColumn(name="cod_actor")
	private Actor actor;

	@ManyToOne
	@MapsId("codigoPelicula")
	@JoinColumn(name="cod_pelicula")
	private Pelicula pelicula;

	public ClaveProtagonista getClave() {
		return clave;
	}

	public void setClave(ClaveProtagonista clave) {
		this.clave = clave;
	}

	public boolean isEsProta() {
		return esProta;
	}

	public void setEsProta(boolean esProta) {
		this.esProta = esProta;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actor, clave, esProta, pelicula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Protagonista other = (Protagonista) obj;
		return Objects.equals(actor, other.actor) && Objects.equals(clave, other.clave) && esProta == other.esProta
				&& Objects.equals(pelicula, other.pelicula);
	}

	@Override
	public String toString() {
		return "Protagonista [esProta=" + esProta + ", actor=" + actor.getNombre() + ", pelicula="
				+ pelicula.getTitulo_pelicula() + "]";
	}

}
