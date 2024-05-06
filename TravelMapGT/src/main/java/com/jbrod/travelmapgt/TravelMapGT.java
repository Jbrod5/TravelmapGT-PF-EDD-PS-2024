/*
 */

package com.jbrod.travelmapgt;

import com.jbrod.travelmapgt.app.structs.ArchivoEntrada;
import com.jbrod.travelmapgt.app.structs.Grafo;
import com.jbrod.travelmapgt.ui.VentanaPrincipal;
import java.util.Scanner;

/**
 *
 * @author jbravo
 */
public class TravelMapGT {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        /*
        System.out.println("Ingrese el path del archivo");
        Scanner scanner = new Scanner(System.in); 
        String path = scanner.nextLine();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        
        Grafo grafo = new Grafo();
        ArchivoEntrada archivoEntrada = new ArchivoEntrada();
        archivoEntrada.generarInstancias(path, grafo);
        
        grafo.imprimirDirigido();
        grafo.imprimirNoDirigido();
        
        
        System.out.println("");
        System.out.println("");        
        System.out.println("");
        
        
        System.out.println("Ingrese el origen");
        String origen = scanner.nextLine();
        
        System.out.println("Ingrese el destino");
        String destino = scanner.nextLine();
        
        
        System.out.println("DIRIGIDO");
        grafo.buscarRutasConducir(origen, destino);
        System.out.println("");
        System.out.println("NO DIRIGIDO");
        grafo.buscarRutasCaminar(origen, destino);*/
        VentanaPrincipal ventana = new VentanaPrincipal();
        
    }
}
