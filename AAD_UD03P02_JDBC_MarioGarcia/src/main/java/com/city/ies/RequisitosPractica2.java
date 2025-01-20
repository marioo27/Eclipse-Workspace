package com.city.ies;

import java.time.LocalDate;

/**
 * Esta interfaz expone la firma de los métodos que los alumnos y alumnas deben implementar para cubrir los requisitos del examen
 * @author José Sala
 * @since 07-12-2024
 */

public interface RequisitosPractica2 
{
	/**
	 * Este método debe invocar la función de BD 'ObtenerRegaloPorEmail' para obtener el posible regalo asignado a la persona cuyo email se pase como parámetro
	 * @param emailPersona email (único) de la persona 
	 * @return regalo asignado a la persona pasada como parámetro
	 */
	public String getRegalo (String emailPersona);

	
	/**
	 * Este método debe añadir una determinada persona a la BD 
	 * @param nomPersona nombre de la persona
	 * @param emailPersona email (único) de la persona
	 * @param fechaNac fecha de nacimiento la persona
	 */
	public boolean aniadirPersona (String nomPersona, String emailPersona, LocalDate fechaNac);
	
	
	/**
	 * Este método debe actualizar el total de asistentes de todos los eventos de forma transaccional
	 */
	public void actualizarTotales ();
}
