
package com.jbrod.travelmapgt.app.structs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author jbravo
 */
public class ArchivoEntrada {

    public void generarInstancias(String path, Grafo grafo){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int contador = 0; 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                contador++;
                // Orden: origen|destino|tiempo_vehiculo|tiempo_pie|consumo_gas|desgaste_persona|distancia
                try{
                    // 0: origen
                    String origen = parts[0];
                    // 1: destino
                    String destino = parts[1];
                

                    // 2: tiempo_vehiculo
                    int tiempoVehiculo = Integer.parseInt(parts[2]);
                    // 3: tiempo_pie
                    int tiempoPie = Integer.parseInt(parts[3]);
                    // 4: consumo_gas 
                    int consumoGas = Integer.parseInt(parts[4]);
                    // 5: desgaste_persona
                    int desgastePersona = Integer.parseInt(parts[5]);
                    
                    
                    int distancia = Integer.parseInt(parts[6]);

                    
                    //Crear los nodos
                    grafo.generarPar(origen, destino, 
                            tiempoVehiculo, tiempoPie, 
                            consumoGas, desgastePersona, 
                            distancia);
                    
                    
                }catch(NumberFormatException nfe){
                    System.out.println("Hubo un error de formateo numerico en la linea: " + contador);
                }
                

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
