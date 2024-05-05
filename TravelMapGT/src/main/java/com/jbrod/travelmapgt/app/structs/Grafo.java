
package com.jbrod.travelmapgt.app.structs;

import java.util.LinkedList;

/**
 * Clase que representa un grafo (dirigido y no dirigido).
 * @author jbravo
 */
public class Grafo {

    // CONFIGURACION DEL GRAFO
    private boolean dirigido;
    private Nodo actual;
    private String origen;
    private String destino; 
    
    // ESTRUCTURAS DEL GRAFO
    private LinkedList<Nodo> nodosDirigidos;
    private LinkedList<Nodo> nodosNoDirigidos; 
    
    public Grafo(){
        nodosDirigidos = new LinkedList<>(); 
        nodosNoDirigidos = new LinkedList<>();
    }
    
    /**
     * Agrega un nodo en base a un nombre (si no se encontraba ya) a la lista de nodos dirigidos y no dirigidos.
     * @param nombre: String con el nombre que tendra el nodo.
     **/
    public void agregarNodo(String nombre){
        String mensaje = "Se quiso agregar el nodo "+  nombre + " pero ya se encontraba en el grafo.";
        
        Nodo resultado = buscarNodoDirigido(nombre);
        if(resultado == null){
            //Agrega el nodo en ambas listas
            Nodo nuevoDirigido   = new Nodo(nombre, this);
            nodosDirigidos.add(nuevoDirigido);
            mensaje = nombre + " agregado al grafo correctamente."; 
        }
        
        resultado = buscarNodoNoDirigido(nombre);
        if(resultado == null){
            Nodo nuevoNoDirigido = new Nodo(nombre, this);
            nodosNoDirigidos.add(nuevoNoDirigido);
            mensaje = nombre + " agregado al grafo correctamente.";
        }
        
        System.out.println(mensaje);
    } 
    
    
    
    /**
     * Genera dos nodos (origen y destino, si no se encuentran ya) y luego los conecta entre si con su informacion.
     * @param origen: String con el nombre de la locacion de origen. 
     * @param destino: String con el nombre de la locacion de destino.
     * 
     * @param tiempo_vehiculo: int con el tiempo de la distancia en vehiculo. 
     * @param tiempo_pie: int con el tiempo de la distancia a pie. 
     * 
     * @param consumo_gas: int con el consumo de gasolina para llegar al destino. 
     * @param desgaste_persona: int con el desgaste de la persona para llegar al destino.
     * 
     * @param distancia: int con la distancia entre las dos locaciones.
     **/
    public void generarPar(String origen, String destino, 
            int tiempo_vehiculo, int tiempo_pie,
            int consumo_gas, int desgaste_persona,
            int distancia){
        
        
        //1. Generar nodos origen y destino
        agregarNodo(origen);
        agregarNodo(destino);
        
        //2. Establecer adyacencia y pesos DIRIGIDOS (carro)
        Nodo nodoOrigenDirigido = buscarNodoDirigido(origen);
        Nodo nodoDestinoDirigido = buscarNodoDirigido(destino);
        // - adyacencia
        nodoOrigenDirigido.agregarAdyacente(nodoDestinoDirigido);
        // - pesos
        nodoOrigenDirigido.agregarValores(destino, tiempo_vehiculo, distancia, consumo_gas);
        
        //3. Establecer adyacencia y pesos NO DIRIGIDO (pie)
        Nodo nodoA = buscarNodoNoDirigido(origen);
        Nodo nodoB = buscarNodoNoDirigido(destino);
        if(nodoA != null && nodoB != null){
            // - adyacencia
            nodoA.agregarAdyacente(nodoB);
            nodoB.agregarAdyacente(nodoA);
            // - pesos
            nodoA.agregarValores(destino, tiempo_pie, distancia, desgaste_persona);
            nodoB.agregarValores(origen, tiempo_pie, distancia, desgaste_persona);
        }else{
            if(nodoA == null){
                System.out.println("El nodo " + origen + " no existe.");
            }
            if(nodoB == null){
                System.out.println("El nodo " + destino + " no existe.");
            }
        }
    }
    
    
    /**
     * Establece vecindad entre dos nodos (tanto en la lista de nodos dirigidos como en la de no dirigidos) en base a los nombres de origen y destino.
     * @param origen: String con el nomrbe del nodo de origen.
     * @param destino: String con el nombre del nodo de destino.
     **/
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
    
    
    /**
     * Busca un nodo en la lista de nodos dirigidos en base a un nombre.
     * @param nombre: String con el nombre del nodo a buscar.
     * @return Nodo resultado de la busqueda. null si no se encuentra en la lista de nodos.
     **/
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
    
    /**
     * Busca un nodo en la lista de nodos no dirigidos en base a su nombre.
     * @param nombre: String con el nombre del nodo a buscar.
     * @return Nodo resultado de la bsuqueda, null si no se encuentra en la lista.
     **/
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
    
    
    
    // - - - - - - - - - - - - - - - - - - - - - - - - - - OBTENCION DE RUTAS - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    /**Buscar la forma de llegar a un destino en modo de conduccion (Nodos dirigidos)
     * @param origen: String con el nombre del nodo de origen.
     * @param destino String con el nombre del nodo de destino.
     */
    public void buscarRutasConducir(String origen, String destino){
        //Configurar el grafo en modo dirigido
        dirigido = true;
        //Buscar el nodo de origen 
        Nodo nodoOrigen = buscarNodoDirigido(origen);
        if(nodoOrigen != null){
            String listaBusqueda = "";
            nodoOrigen.buscarNodo(listaBusqueda, destino);
        }else{
            System.out.println("El origen: " + origen + " no existe.");
        }
        
    }
    
    /**Buscar la forma de llegar a un destino en modo caminata (Nodos no dirigidos)
     * @param origen: String con el nombre del nodo de origen.
     * @param destino: String con el nombre del nodo de destino.
     */
    public void buscarRutasCaminar(String origen, String destino){
        //Configurar el grafo en modo no dirigido 
        dirigido = false; 
        //Buscar el nodo de origen no dirigido
        Nodo nodoOrigen = buscarNodoNoDirigido(origen);
        if(nodoOrigen != null){
            String listaBusqueda = "";
            nodoOrigen.buscarNodo(listaBusqueda, destino);
        }else{
            System.out.println("El origen: " + origen + " no existe.");
        }
    }
    
    /**Agregar una lista de nodos al arbol de soluciones
     *@param lista: String con la lista de nodos separados por espacios.
     */
    public void agregarLista(String lista){
        LinkedList<Nodo> camino = new LinkedList();
        String nodos[] = lista.split(" ");
        Nodo op;
        for (String nodo : nodos) {
            if(dirigido){
                op = buscarNodoDirigido(nodo);
            }else{
                op = buscarNodoNoDirigido(nodo);
            }
            
            if(nodo != null){
                camino.add(op);
            }
        }
        //AGREGAR LA LISTA AL ARBOL

    }
    
    
    
    
    
    
    
    
    
    
    
    
    public void imprimirDirigido(){
        System.out.println("DIRIGIDO");
        for (Nodo nodosDirigido : nodosDirigidos) {
            System.out.println(nodosDirigido.obtenerNombre());
            nodosDirigido.mostrarAdyacentes();
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
    }
    
        public void imprimirNoDirigido(){
        System.out.println("NO DIRIGIDO");
        for (Nodo nodosDirigido : nodosNoDirigidos) {
            System.out.println(nodosDirigido.obtenerNombre());
            nodosDirigido.mostrarAdyacentes();
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
    }
}
