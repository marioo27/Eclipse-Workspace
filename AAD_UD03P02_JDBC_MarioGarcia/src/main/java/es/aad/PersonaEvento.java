package es.aad;

import java.util.Objects;

public class PersonaEvento {
	private int personaId;
	private int eventoId;
	private boolean asistio;
	private int nota;

	public PersonaEvento(int personaId, int eventoId, boolean asistio, int nota) {
		this.personaId = personaId;
		this.eventoId = eventoId;
		this.asistio = asistio;
		this.nota = nota;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getEventoId() {
		return eventoId;
	}

	public void setEventoId(int eventoId) {
		this.eventoId = eventoId;
	}

	public boolean isAsistio() {
		return asistio;
	}

	public void setAsistio(boolean asistio) {
		this.asistio = asistio;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "PersonaEvento [personaId=" + personaId + ", eventoId=" + eventoId + ", asistio=" + asistio + ", nota="
				+ nota + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(asistio, eventoId, nota, personaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaEvento other = (PersonaEvento) obj;
		return asistio == other.asistio && eventoId == other.eventoId && nota == other.nota
				&& personaId == other.personaId;
	}

}
