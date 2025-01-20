package es.aad;

public class SQL {

	protected static final String ANIADIR_PERSONA = "INSERT INTO Personas (Nombre, Email, FechaNacimiento) VALUES (?, ?, ?)";
	protected static final String GET_REGALO_POR_EMAIL = "{ ? = CALL ObtenerRegaloPorEmail (?) }";
	protected static final String ACTUALIZAR_TOTALES = "UPDATE PersonasEventos SET Asistio = ? AND CalificacionEvento = ? WHERE PersonaID = ? AND WHERE EventoID = ?";


}
