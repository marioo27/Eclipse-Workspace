package Ficheros;

import java.io.File;
import java.io.IOException;

public class conociendoMetodos {

	public static void main(String[] args) {


		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("file.separator"));
		System.out.println(System.getProperty("java.version"));
		
		File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "mario.txt");
		
		try
		{
			if(!fichero.createNewFile())
				System.out.println("Error. El fichero [" + fichero.getPath() + "] ya existe");
			else
				System.out.println("Se ha creado el fichero [" + fichero.getPath() + "]");
		
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		File directorio = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "carpeta");
		
		if(!directorio.mkdir())
			System.out.println("Imposible crear el directorio [" + directorio.getPath() + "]");
		
		else
			System.out.println("Se ha creado el directorio [" + directorio.getPath() + "]");
			
		//NO SE PUEDE BORRAR UN FICHERO SI ESTÁ VACÍO
		
		/*
		if(!fichero.delete())
			System.out.println("Error, imposible borrar el fichero [" + fichero.getPath() + "]");
		
		else
			System.out.println("Se ha borrado el fichero [" + fichero.getPath() + "]");
		*/
		
		if(directorio.exists() && directorio.isDirectory() && directorio.list().length == 0)
			
		
			if(!directorio.delete())
			System.out.println("Error, imposible borrar el directorio [" + directorio.getPath() + "]");
		
			else
			System.out.println("Se ha borrado el directorio [" + directorio.getPath() + "]");
		
		
		if(directorio.list().length > 0)
			System.out.println("El directorio [" + directorio.getPath() + "] no está vacío");
			
			
		System.out.println("Final del programa");
		
		/*
		 * Visualizar las propiedades de un fichero y su tamaño en bytes
		 */
		
		String propiedadesFichero = new String();
		
		if(fichero.canExecute())
			propiedadesFichero = propiedadesFichero + "X";
		if(fichero.canRead())
			propiedadesFichero = propiedadesFichero + "R";
		if(fichero.canWrite())
			propiedadesFichero = propiedadesFichero + "W";
		if(fichero.isHidden())
			propiedadesFichero = propiedadesFichero + "A";
		
		System.out.println("El fichero pesa [" + fichero.length() +"] bytes y tiene las propiedades [" + propiedadesFichero + "]");
			
					
	}

}
