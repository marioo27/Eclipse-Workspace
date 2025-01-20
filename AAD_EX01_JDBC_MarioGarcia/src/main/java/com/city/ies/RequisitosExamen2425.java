package com.city.ies;

/**
 * Esta interfaz expone la firma de los métodos que los alumnos y alumnas deben implementar para
 * cubrir los requisitos del examen
 * 
 * @author José Sala
 * @since 07-12-2024
 */

public interface RequisitosExamen2425 {
	/**
	 * Este método debe invocar la función de BD 'TotalVentasPorCliente' para obtener el importe total
	 * de ventas de un cliente pasado como parámetro
	 * 
	 * @param idCliente identificador del cliente
	 * @return total de ventas del clientes pasado como parámetro
	 */
	public double getTotalVentas(int idCliente); // He cambiado el tipo de dato que devuelve este metodo (de int a
													// double) ya que la funcion almacenada en la BD que se invoca
													// devuelve un numero decimal, noun entero

	/**
	 * Este método debe invocar el procedimiento almacenado de BD 'DecrementarStock', el cual decrementa
	 * en una unidad el estocage de un producto pasado como parámetro
	 * 
	 * @param idProducto identificador del producto
	 */
	public boolean reducirStockProductos(int idProducto);

	/**
	 * Este método debe añadir un determinado producto a la BD
	 * 
	 * @param nomProducto identificador del producto
	 * @param precio      precio unitario del producto
	 * @param stock       cantidad disponible del producto
	 */
	public boolean aniadirProducto(String nomProducto, double precio, int stock);

	/**
	 * Este método debe actualizar el importe total de las ventas realizadas de forma transaccional
	 */
	/*
	 * public void actualizarTotales (); He comentado este metodo siguiendo las instrucciones
	 * proporcionadas, he decidido realizar el ejercicio 4 y no el 3
	 */
}
