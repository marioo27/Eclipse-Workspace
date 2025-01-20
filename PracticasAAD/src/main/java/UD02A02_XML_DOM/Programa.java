package UD02A02_XML_DOM;

import java.io.File;
import java.util.Collection;

public class Programa {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java Programa <ruta_xml> <ruta_salida>");
            System.exit(1);
        }

        File ficheroXml = new File(args[0]);
        File ficheroSalida = new File(args[1]);

        Collection<Item> listaItems = XmlManager.parsearXml(ficheroXml);

        if (listaItems != null) {
            XmlManager.generarXml(listaItems, ficheroSalida);
            System.out.println("El listado de vulnerabilidades dispone de " + listaItems.size() + " Items.");
        } else {
            System.out.println("Error al procesar el archivo XML.");
        }
    }
}
