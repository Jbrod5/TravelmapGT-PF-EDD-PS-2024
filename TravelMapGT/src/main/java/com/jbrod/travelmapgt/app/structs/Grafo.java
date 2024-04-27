
package com.jbrod.travelmapgt.app.structs;

import java.util.LinkedList;

/**
 * Clase que representa un grafo (dirigido y no dirigido).
 * @author jbravo
 */
public class Grafo {

    private LinkedList<Nodo> nodosDirigidos;
    private LinkedList<Nodo> nodosNoDirigidos;
    
    public void agregarNodo(String nombre){
        String mensaje = "Se quiso agregar el nodo "+  nombre + " pero ya se encontraba en el grafo.";
        
        Nodo resultado = buscarNodoDirigido(nombre);
        if(resultado == null){
            //Agrega el nodo en ambas listas
            Nodo nuevoDirigido   = new Nodo(nombre);
            nodosDirigidos.add(nuevoDirigido);
            mensaje = nombre + " agregado al grafo correctamente."; 
        }
        
        resultado = buscarNodoNoDirigido(nombre);
        if(resultado == null){
            Nodo nuevoNoDirigido = new Nodo(nombre);
            nodosNoDirigidos.add(nuevoNoDirigido);
            mensaje = nombre + " agregado al grafo correctamente.";
        }
        
        System.out.println(mensaje);
    } 
    
    
    
    public void establecerAdyacencia(String origen, String destino){
        //1. Obtener los nodos origen y destino de la lista de dirigidos
        Nodo nodoOrigen = buscarNodoDirigido(origen);
        Nodo nodoDestino = buscarNodoDirigido(destino);
        
        //Agregar la adyacencia dirigida
        if(nodoOrigen != null && nodoDestino != null){
            nodoOrigen.agregarAdyacente(nodoDestino);
        }else{
            System.out.println("Se quiso agregar la adyacencia de " + origen + " hacia " + destino + " pero origen: " + (nodoOrigen == null) + " y destino: " + (nodoDestino == null));
        }
        
        //Agregar la adyacencia no dirigida
        nodoOrigen  = buscarNodoNoDirigido(origen);
        nodoDestino = buscarNodoNoDirigido(destino);
        
        if(nodoOrigen != null && nodoDestino != null){
            nodoOrigen.agregarAdyacente(nodoDestino);
            nodoDestino.agregarAdyacente(nodoOrigen);
        }else{
            System.out.println("Se quiso agregar la adyacencia de " + origen + " hacia " + destino + " pero origen: " + (nodoOrigen == null) + " y destino: " + (nodoDestino == null));
        }
    }
    
    
    
    public Nodo buscarNodoDirigido(String nombre){
        for (int i = 0; i < nodosDirigidos.size(); i++) {
            Nodo actual = nodosDirigidos.get(i);
            if( actual != null && nombre.equals(actual.obtenerNombre()) ){
                //Se encontro el nodo
                return actual; 
            }
        }
        //No se encontro
        return null; 
    }
    
    
    
    
    public Nodo buscarNodoNoDirigido(String nombre){
        for (int i = 0; i < nodosNoDirigidos.size(); i++) {
            Nodo actual = nodosNoDirigidos.get(i);
            if( actual != null && nombre.equals(actual.obtenerNombre()) ){
                //Se encontro el nodo
                return actual; 
            }
        }
        //No se encontro
        return null; 
    }
}
