package pendientes;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RecordarEventos {

	public RecordarEventos() {
	}

	public String intentarResponder(String mensaje) throws Exception {
		leerArchivo();

		return null;

	}

	private void leerArchivo() throws Exception {
		ArrayList<String> lista = new ArrayList<>();
		Scanner read = new Scanner(new File("recordatorios.dat"));

		while (read.hasNextLine())
			lista.add(read.nextLine());
		read.close();

		for (String temp : lista) {
			if (temp.substring(0, 10) == new Fecha().getFecha()) {
			} // se podria usar la
			// funcion desde y hasta de la clase fecha

			// aca habria que hacer que los que son de 3 a 0 dias los muestre... y los que
			// la fecha es menor los elimine de la lista... y al final hacer que vuelva a
			// escribir el archivo
		}
	}

}
