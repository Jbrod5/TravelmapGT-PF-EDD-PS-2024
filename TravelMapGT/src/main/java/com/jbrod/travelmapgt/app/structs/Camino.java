
package com.jbrod.travelmapgt.app.structs;

import java.util.LinkedList;

/**
 * Clase que contiene una lista de nodos que son un camino de llegada, ademas de los valores de desgaste, tiempo y distancia.
 * @author jbravo
 */
public class Camino {

    private LinkedList<Nodo> camino;
    int desgaste; 
    int distancia; 
    int tiempo; 
    
    public Camino(){
        camino = new LinkedList<>();
        desgaste = 0; 
        distancia = 0; 
        tiempo = 0; 
    }
    
    public void agregarNodo(Nodo nodo){
        if(camino.size() >0){
            //Obtener el ultimo nodo
            int posUltimo = camino.size() -1;
            Nodo ultimo = camino.get(posUltimo);
            
            //Sumar los valores del ultimo nodo hacia el que se va a insertar
            String destino = nodo.obtenerNombre();
            desgaste += ultimo.obtenerResistencia(destino);
            distancia += ultimo.obtenerDistancia(destino);
            tiempo += ultimo.obtenerTiempo(destino);
        }
        camino.add(nodo);
    }
    
    
    
    
    public int obtenerDesgaste(){
        return desgaste; 
    }
    
    public int obtenerTiempo(){
        return tiempo; 
    }
    
    public int obtenerDistancia(){
        return distancia;
    }
    
    
    public String obtenerCaminoResaltado(){
        String resaltado = "";
            
        //Recorrer los nodos que conforman el camino
        for (Nodo nodo : camino) {
            if(nodo != null){
                resaltado += "               " + nodo.obtenerNombre() +  " [color = green, style=filled];\n";
            }
        }
        
        return resaltado; 
    }
    
}
