package es.ciudadescolar;

public class SQL {

	protected static final String QUERY1 = "SELECT expediente, nombre, nota_raices FROM alumnos";
	protected static final String QUERY2 = "SELECT expediente, nombre, nota_raices FROM alumnos WHERE expediente > 3";
	protected static final String QUERY_NOTA_ALUMNO = "SELECT nota_raices FROM alumnos WHERE expediente = ?";
	protected static final String QUERY_SOBRESALIENTE_ALUMNO = "SELECT nombre FROM alumnos WHERE sexo = ? AND nota_raices > ?";
	protected static final String INSERT_ALUMNO = "INSERT INTO alumnos(expediente, nombre, nota_raices) VALUES(?,?,?)";
	protected static final String DELETE_ALUMNO = "DELETE FROM alumnos WHERE expediente = ?";
	protected static final String UPDATE_NOTA_ALUMNO = "UPDATE alumnos SET nota_raices = ? WHERE expediente = ?";
	protected static final String INVOCACION_SP_INFO_ALUMNO = "{CALL sp_getalumno (?)}";
	protected static final String INVOCACION_FUN_NOTA_ALUMNO = "{ ? = CALL fun_getnotaalumno (?) }";

	protected static final String QUERY_PERMISOS = "SHOW GRANTS FOR '?'@'?'";
}
