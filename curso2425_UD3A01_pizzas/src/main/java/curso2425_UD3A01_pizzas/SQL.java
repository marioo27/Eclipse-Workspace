package curso2425_UD3A01_pizzas;

public class SQL {

	protected static final String INGREDIENTES_PIZZA = "SELECT nombre_ingrediente FROM ingrediente i INNER JOIN pizza_ingrediente pi ON i.cod_ingrediente = pi.ingredienteId INNER JOIN pizza p ON pi.pizzaId = p.cod_pizza WHERE p.nombre_pizza = ?";
	protected static final String GET_PRECIO = "{? = CALL getPrecioPizza(?)}";
	protected static final String ADD_INGREDIENTE = "CALL addIngredientePizza(?,?,?)";
	protected static final String ADD_PIZZA = "INSERT INTO pizza (nombre_pizza, precio) VALUES(?,?)";
	protected static final String PIZZA_EXISTS = "SELECT * FROM pizza WHERE nombre_pizza = ?";
	protected static final String INGREDIENTE_EXISTS = "SELECT * FROM ingrediente WHERE nombre_ingrediente = ?";
	protected static final String INSERT_INGREDIENTE = "INSERT INTO ingrediente (nombre_ingrediente) VALUES (?)";
}
