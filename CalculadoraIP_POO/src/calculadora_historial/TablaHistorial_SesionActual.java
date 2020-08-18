package calculadora_historial;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 * ESTA TABLA (TABLA 1/4) ES LA MAS DIFERENTE A TODAS, SOLO MUESTRA LAS ITERACIONES (EN CASO DE QUE SEAN VALIDAS) DE LA SESION ACTUAL, AL CERRAR LA APP, ESTAS SE PERDERAN,
 * LAS QUE SI SE MANTIENEN SON LAS TABLAS 2 Y 3 (IPv4 E IPv6 PROPIAMENTE).
 * 
 * CADA VEZ QUE SE INGRESA UNA IP VALIDA, ESTA MISMA SE AGREGA A ESTA TABLA SIN IMPORTAR SI YA SE INGRESO PREVIAMENTE, PERO NO SE INGRESA MAS DE UNA VEZ EN LA BASE DE DATOS PARA LAS TABLAS 2 Y 3
 */

public class TablaHistorial_SesionActual {

	private JScrollPane scrollPane_SesionActual;	
	
	public TablaHistorial_SesionActual(ArrayList<Object[]> AL) {
		
		Object columnas[] = {"VERSION","IP","MASCARA"};
		DefaultTableModel modelo = new DefaultTableModel();	
		modelo.setColumnIdentifiers(columnas);
        JTable tablaHistorial_SesionActual = new JTable();
        tablaHistorial_SesionActual.setLayout(null);
        tablaHistorial_SesionActual.setModel(modelo);
        tablaHistorial_SesionActual.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaHistorial_SesionActual.setFillsViewportHeight(true);
        scrollPane_SesionActual = new JScrollPane(tablaHistorial_SesionActual);
        scrollPane_SesionActual.setBounds(0, 60, 680, 285);  
                
        for(int i=0;i<AL.size();i++) {
        	modelo.addRow(AL.get(i));
        }

        
        tablaHistorial_SesionActual.repaint();
		
	}
	
	public JScrollPane obtenerScrollPane_SesionActual() {
		return scrollPane_SesionActual;
	}
	
	
}
