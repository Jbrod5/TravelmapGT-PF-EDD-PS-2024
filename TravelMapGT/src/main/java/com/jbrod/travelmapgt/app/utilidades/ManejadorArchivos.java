package main.java.com.jbrod.travelmapgt.app.utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Clase que se encarga del manejo de archivos como la escritura / reescritura.
 * @author jbravo
 */
public class ManejadorArchivos {

    public void escribirArchivo(String rutaArchivo, String contenido){
        try {
            // Crear el archivo si no existe
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Vaciar el contenido del archivo
            FileWriter escritor = new FileWriter(archivo, false); // "false" para sobreescribir
            escritor.write("");
            escritor.close();

            // Escribir el nuevo contenido en el archivo
            FileWriter escritor2 = new FileWriter(archivo);
            escritor2.write(contenido);
            escritor2.close();

            System.out.println("Archivo creado y actualizado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al manipular el archivo: " + e.getMessage());
        }
    }
    
    public void generarGrafo(String rutaGrafo, String nombreGrafo){
        // Generar imagen PNG del grafo usando ProcessBuilder
        String comandoDot = "dot -Tpng " + rutaGrafo + " -o " + nombreGrafo + ".png";
        try {
            Process proceso = Runtime.getRuntime().exec(comandoDot);
            proceso.waitFor();

            System.out.println("Grafo generado correctamente: " + nombreGrafo + ".png");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al generar el grafo: " + e.getMessage());
        }
    }
    
}
