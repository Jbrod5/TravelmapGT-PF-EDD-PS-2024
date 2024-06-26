
package main.java.com.jbrod.travelmapgt.app.structs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Clase que representa a un nodo de un grafo dirigido.
 * @author jbravo
 */
public class Nodo {
    
    private Grafo grafo; 
    
    private String nombre;
    private LinkedList <Nodo> adyacentes;
    
    //Almacenar pesos y resistencias
    private Map<String, Integer> distancia; 
    private Map<String, Integer> tiempo; 
    private Map<String, Integer> resistencia; 
    
    public Nodo(String nombre, Grafo grafo){
        this.nombre = nombre;
        adyacentes = new LinkedList<>();
        
        distancia = new HashMap<>();
        resistencia = new HashMap<>();
        tiempo = new HashMap<>();
        
        this.grafo = grafo; 
    }
    
    public LinkedList<Nodo> obtenerAdyacentes(){
        return adyacentes; 
    }
    
    public String obtenerNombre(){
        return nombre; 
    }
    
    public void buscarNodo(String listaNodos, String destino){
        listaNodos += nombre + " "; 
        
        //Destino encontrado, imprimir la ruta
        if(destino.equals(nombre)){
            System.out.println("Destino encontrado con la ruta: " + listaNodos);
            grafo.agregarLista(listaNodos);
        }else{
            //Seguir buscando
            for (Nodo adyacente : adyacentes) {
                if(adyacente != null){
                
                    //Si el nodo no esta ya en la lista de busqueda, buscar en el 
                    if(! (listaNodos.contains( adyacente.obtenerNombre())) ){
                        adyacente.buscarNodo(listaNodos, destino);
                    }
                }
            }
        
        }
    }
    
    
    /**
     * Agrega los valores de distancia y resistencia en un diccionario.
     * @param destino : String con el nombre del destino que contendra en los mapas distancia y resistencia
     * @param tiempo: Numero entero con el tiempo en llegar al destino.
     * @param distancia : Numero entero con la distancia hacia el destino.
     * @param resistencia: Numero entero con la resistencia (combustible o desgaste) hacia el destino.
     **/
    public void agregarValores(String destino, int tiempo,int distancia, int resistencia){
        this.distancia.put(destino, distancia);
        this.resistencia.put(destino, resistencia);
        this.tiempo.put(destino, tiempo);
    }
    
    public int obtenerDistancia(String destino){
        if(distancia.containsKey(destino)){    
            return distancia.get(destino);
        }
        
        return -1;
    }
    
    public int obtenerResistencia(String destino){
        if(resistencia.containsKey(destino)){
            return resistencia.get(destino);
        }
        return -1; 
    }
    
    public int obtenerTiempo(String destino){
        if(tiempo.containsKey(destino)){
            return tiempo.get(destino);
        }
        return -1;
    }
    
    
    

    public void agregarAdyacente(Nodo nodo){
        if(nodo != null){
            adyacentes.add(nodo);
        }
    }
    
    
    public void mostrarAdyacentes(){
        for (int i = 0; i < adyacentes.size(); i++) {
            System.out.println(adyacentes.get(i).obtenerNombre());
        }
    }
}
