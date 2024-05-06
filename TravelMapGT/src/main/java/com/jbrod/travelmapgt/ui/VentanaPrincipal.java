package main.java.com.jbrod.travelmapgt.ui;

import java.awt.Dimension;
import java.io.File;
import main.java.com.jbrod.travelmapgt.app.structs.ArchivoEntrada;
import main.java.com.jbrod.travelmapgt.app.structs.Grafo;
import main.java.com.jbrod.travelmapgt.app.structs.Nodo;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel; 
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import main.java.com.jbrod.travelmapgt.app.structs.Camino;

/**
 * Ventana principal del programa.
 * @author jbravo
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private Grafo grafo;
    private LinkedList<Nodo> listaNodos;
    private LinkedList<Nodo> listaPasos;
    private Nodo nodoActual; 
    
    private LinkedList<Camino> posiblesRutas;
    
    private int tiempoTotal; 
    private int distanciaTotal;
    private int desgasteTotal;
    
    private String rutas;
    
    //private JScrollPane scrlGrafo;
    //private JLabel imagen;
    
    private String pathGrafo;
    
    public VentanaPrincipal() {
        initComponents();
        
        generarGrafo();
        setVisible(true);
        
        cbxOpcionMejorCamino.removeAllItems();
        cbxOpcionMejorCamino.addItem("Menor distancia");
        cbxOpcionMejorCamino.addItem("Menor desgaste");
        cbxOpcionMejorCamino.addItem("Menor tiempo");
        
        tiempoTotal = 0;
        distanciaTotal = 0;
        desgasteTotal = 0;
        //imagen = new JLabel();
        //scrlGrafo.setViewportView(imagen);
    }
    
    private void generarGrafo(){
        //Solicitar un path para el archivo de entrada
        String path = ""; 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de entrada");
        int seleccion = fileChooser.showOpenDialog(null);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Ruta del archivo seleccionado: " + path);
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
        
        
        //Instanciar el grafo
        grafo = new Grafo();
        ArchivoEntrada entrada = new ArchivoEntrada();
        entrada.generarInstancias(path, grafo);
        
        grafo.establecerDirigido(chxDirigido.isSelected());
        grafo.generarGrafico();
        
        obtenerListaNodos();
        
        
    }
    
    private void obtenerListaNodos(){
        if(chxDirigido.isSelected()){
            listaNodos = grafo.obtenerNodosDirigidos();
            listaNodos = grafo.establecerConduccion();
        }else{
            listaNodos = grafo.obtenerNodosNoDirigidos();
            listaNodos = grafo.establecerCaminata();
        }
        
        //Colocar las opciones de entrada 
        cbxOrigen.removeAllItems();
        cbxDestino.removeAllItems();
        for (Nodo listaNodo : listaNodos) {
            cbxOrigen.addItem(listaNodo.obtenerNombre());
            cbxDestino.addItem(listaNodo.obtenerNombre());
        }

    }
    
    public void actualizarImagenGrafo(){
        String pathGrafo = "";
        if(chkVerMejorcamino.isSelected()){
            //Cargar el mejor camino
            grafo.generarGraficoMejor(cbxOpcionMejorCamino.getSelectedIndex() +1);
            this.pathGrafo = "./GrafoMejorCamino.png";
        }else{
            this.pathGrafo = "./GrafoGeneral.png";
        }
        pathGrafo = this.pathGrafo;
        //scrlGrafo.removeAll();
        //Crear la imagen
        Icon imageIcon = new ImageIcon(pathGrafo);
        lblImagen.setIcon(imageIcon);
        //Dimension dim = new Dimension(imageIcon.getImage().getWidth(this), imageIcon.getImage().getHeight(this));
        //scrlGrafo.getViewport().setViewSize(dim);
        
        //remove(scrlGrafo);
        //scrlGrafo = new JScrollPane();
        //add(scrlGrafo);
        //scrlGrafo.setViewportView(imagen);
        //try{
        //    pnlImagen.remove(scrlGrafo);
        //}catch(Exception e){
            
        //}
        
        //scrlGrafo = new JScrollPane();
        //scrlGrafo.setViewportView(imagen);
        //pnlImagen.add(scrlGrafo);
        //pnlImagen.add(new JLabel("feli"));
        
        
        //Cargar la imagen
        //
        ;
        File archivo = new File(pathGrafo);

        if (archivo.exists()) {
            System.out.println("El archivo existe");
        } else {
            System.out.println("El archivo no existe");
        }

        //scrlGrafo.updateUI();
        //repaint();
    }
    
    
    
    public void definirRutas(){
    
        if(posiblesRutas!= null){
            int contador = 0; 
            String[] cabeceras = new String [4];
            cabeceras[0] = "Ruta";
            cabeceras[1] = "Tiempo";
            cabeceras[2] = "Desgaste";
            cabeceras[3] = "Distancia";

            String [][] tabla = new String[posiblesRutas.size()][4];
            for (Camino ruta : posiblesRutas) {
                //tabla[contador][0] = ruta.obtenerRuta();
                tabla[contador][0] = ruta.obtenerCam();
                tabla[contador][1] = Integer.toString(ruta.obtenerTiempo());
                tabla[contador][2] = Integer.toString(ruta.obtenerDesgaste());
                tabla[contador][3] = Integer.toString(ruta.obtenerDistancia());

                contador ++;
            }

            // Crear la ventana de diálogo
            JDialog dialog = new JDialog();
            JTable table = new JTable(new DefaultTableModel(tabla, cabeceras));

            // Agregar la tabla a un JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);

            // Agregar el JScrollPane a la ventana de diálogo
            dialog.add(scrollPane);

            // Mostrar la ventana de diálogo
            dialog.pack();
            dialog.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Todavía no hay posibles rutas");
        }
        
        
        
    }
    
    public void establecerPosiblesRutas(LinkedList<Camino> rutas){
        posiblesRutas = rutas; 
    }
    
    
    
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chkVerMejorcamino = new javax.swing.JCheckBox();
        cbxOpcionMejorCamino = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbxDestino = new javax.swing.JComboBox<>();
        chxDirigido = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbxPaso = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblTiempo = new javax.swing.JLabel();
        lblDesgaste = new javax.swing.JLabel();
        lblDistancia = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txfPasos = new javax.swing.JTextField();
        btnVerRutas = new javax.swing.JButton();
        scrlGrafo = new javax.swing.JScrollPane();
        lblImagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de visualizacion"));

        chkVerMejorcamino.setText("Ver major camino");
        chkVerMejorcamino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVerMejorcaminoActionPerformed(evt);
            }
        });

        cbxOpcionMejorCamino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chkVerMejorcamino)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbxOpcionMejorCamino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(chkVerMejorcamino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxOpcionMejorCamino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion de recorrido"));

        jLabel1.setText("Origen");

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOrigenActionPerformed(evt);
            }
        });

        jLabel2.setText("Destino");

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDestinoActionPerformed(evt);
            }
        });

        chxDirigido.setSelected(true);
        chxDirigido.setText("Conduciendo (dirigido)");
        chxDirigido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxDirigidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(chxDirigido))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(chxDirigido)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Movimiento"));

        jLabel3.setText("Mover hacia: ");

        cbxPaso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Mover");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxPaso, 0, 175, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Estadisticas"));

        lblTiempo.setText("Tiempo");

        lblDesgaste.setText("Desgaste");

        lblDistancia.setText("Distancia");

        txfPasos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfPasosActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(txfPasos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTiempo)
                    .addComponent(lblDesgaste)
                    .addComponent(lblDistancia))
                .addContainerGap(123, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTiempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDesgaste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDistancia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnVerRutas.setText("Ver posibles rutas");
        btnVerRutas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerRutasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVerRutas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerRutas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblImagen.setOpaque(true);
        scrlGrafo.setViewportView(lblImagen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrlGrafo, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrlGrafo, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    //Al cambiar el estado del checkbox, generar de nuevo
    private void chxDirigidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxDirigidoActionPerformed
        generarGrafo();
    }//GEN-LAST:event_chxDirigidoActionPerformed

    private void chkVerMejorcaminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVerMejorcaminoActionPerformed
        // TODO add your handling code here:
        actualizarImagenGrafo();
    }//GEN-LAST:event_chkVerMejorcaminoActionPerformed

    
            int cont = 1;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            Nodo temporal = nodoActual;
            nodoActual = listaPasos.get(cbxPaso.getSelectedIndex());
            
            tiempoTotal += temporal.obtenerTiempo(nodoActual.obtenerNombre());
            distanciaTotal += temporal.obtenerDistancia(nodoActual.obtenerNombre());
            desgasteTotal += temporal.obtenerResistencia(nodoActual.obtenerNombre());
            
            lblTiempo.setText("Tiempo: " + tiempoTotal);
            lblDistancia.setText("Distancia: " + distanciaTotal);
            lblDesgaste.setText("Desgaste: " + desgasteTotal);
            
            txfPasos.setText(txfPasos.getText() + " " + nodoActual.obtenerNombre());
            
            grafo.establecerAcual(nodoActual, this);
            listaPasos = nodoActual.obtenerAdyacentes();
            colocarNuevosPasos();
        }catch(Exception e){
        
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOrigenActionPerformed
        // TODO add your handling code here:
        try{
            nodoActual = listaNodos.get(cbxOrigen.getSelectedIndex());
            grafo.establecerAcual(nodoActual, this);
            listaPasos = nodoActual.obtenerAdyacentes();
            colocarNuevosPasos();
            
            txfPasos.setText(nodoActual.obtenerNombre());
            
            tiempoTotal = 0; 
            distanciaTotal = 0;
            desgasteTotal = 0;
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_cbxOrigenActionPerformed

    private void cbxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDestinoActionPerformed
        // TODO add your handling code here:
        try{
            Nodo destino = listaNodos.get(cbxDestino.getSelectedIndex());
            grafo.establecerDestino(destino.obtenerNombre());
        }catch(Exception e){
        
        }
    }//GEN-LAST:event_cbxDestinoActionPerformed

    private void txfPasosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfPasosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfPasosActionPerformed

    private void btnVerRutasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRutasActionPerformed
        // TODO add your handling code here:
        definirRutas();
    }//GEN-LAST:event_btnVerRutasActionPerformed
 
    
    private void colocarNuevosPasos(){
        cbxPaso.removeAllItems();
        for (Nodo listaPaso : listaPasos) {
            cbxPaso.addItem(listaPaso.obtenerNombre());
        }
    }
    
    
    public void avisarLlegada(){
        String mensaje = "Llego al destino!";
        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVerRutas;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOpcionMejorCamino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JComboBox<String> cbxPaso;
    private javax.swing.JCheckBox chkVerMejorcamino;
    private javax.swing.JCheckBox chxDirigido;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDesgaste;
    private javax.swing.JLabel lblDistancia;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JScrollPane scrlGrafo;
    private javax.swing.JTextField txfPasos;
    // End of variables declaration//GEN-END:variables
}
