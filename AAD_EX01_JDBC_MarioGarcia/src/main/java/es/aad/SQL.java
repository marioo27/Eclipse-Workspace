package es.aad;

/**
 * Esta es la clase donde se definen las sentencias SQL que se requieren para invocar a los metodos
 * de la clase DbManager
 * 
 * @author Mario Garcia
 * @since 18-12-2024
 * @version 1.0.0
 */
public class SQL {
	protected static final String ANIADIR_PRODUCTO = "INSERT INTO productos (Nombre, Precio, Stock) VALUES (?, ?, ?)";
	protected static final String GET_TOTAL_VENTAS = "{ ? = CALL TotalVentasPorCliente (?)}";
	protected static final String REDUCIR_STOCK = "{ CALL DecrementarStock (?)}";

	protected static final String MOSTRAR_CLIENTE = "SELECT * from Clientes WHERE ClienteID = ?";
}
