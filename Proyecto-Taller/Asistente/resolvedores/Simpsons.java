package resolvedores;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * linguo muerto.
 */
public class Simpsons extends RespuestaGenerico {

	public Simpsons() {
		super();
	}

	public String intentarResponder(String mensaje) {
		if (consulta(mensaje))
			return respuesta();
		if(mensaje.matches(".*simpsons.*"))
			return obtenerYReordenar();
		return null;
	}
	
	private String obtenerYReordenar() {
		Scanner file;
		ArrayList<String> lista = new ArrayList<String>();
		PrintWriter file2 = null;
		String ultimo = "";
		try {
			file = new Scanner(new File("Respuestas\\respuestas_SimpsonsLista.dat"));

			while (file.hasNextLine())
				lista.add(file.nextLine());

			int random = (int) (Math.random() * 15);
			while (random > lista.size() / 3)
				random = (int) (Math.random() * random);
			ultimo = lista.get(random);
			lista.remove(random);
			file.close();

			file2 = new PrintWriter(new File("Respuestas\\respuestas_SimpsonsLista.dat"));
			for (String temp : lista)
				file2.write(temp + "\r\n");
			file2.write(ultimo);
			file2.close();
		} catch (Exception e) {
		}

		return ultimo;
	}

}
