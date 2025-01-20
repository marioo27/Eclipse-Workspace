package es.ciudadescolar;

public class SQL {

	protected static final String ADD_PELICULA = "INSERT INTO peliculas (titulo, anio) VALUES (?,?)";
	protected static final String GET_NUM_ACTORES = "{CALL fun_GetNumActores (?)}";
	protected static final String IS_PROTAGONIST = "(?,?)";

}
