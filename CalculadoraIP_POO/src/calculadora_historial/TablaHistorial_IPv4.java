package calculadora_historial;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * EN ESTA CLASE SE OBTIENEN LOS DATOS DE LA TABLA IPv4 DE LA BBDD Y LA MONTA EN UN JTABLE EL CUAL ESTA EN UN JSCROLLPANE, 
 * ESTE COMPONENTE ES SOLICITADO EN LA CLASE "Vista_Historial.java"
 * 
 * TABLA 2/4
 */

public class TablaHistorial_IPv4{
	
	ArrayList<String[]> arrayList_DatosIPv4 = new ArrayList<>();
	JScrollPane scrollPaneHistorial_IPv4;	
	DefaultTableModel modelo_IPv4 = new DefaultTableModel();	
	
	public TablaHistorial_IPv4() {
		Calculadora_Database DB = Calculadora_Database.instanciaDB();
		arrayList_DatosIPv4 = DB.getDatosTablaIPv4();
		
		Object columnas[] = {"IPv4","MASCARA","IPv4 BINARIO","MASCARA BINARIO","TIPO","CLASE","DIR. RED","DIR. GATEWAY","DIR. BROADCAST","RANGO"};
        modelo_IPv4.setColumnIdentifiers(columnas);
        JTable tabla_IPv4 = new JTable();
        tabla_IPv4.setLayout(null);
        tabla_IPv4.setModel(modelo_IPv4);
        tabla_IPv4.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla_IPv4.setFillsViewportHeight(true);
        scrollPaneHistorial_IPv4 = new JScrollPane(tabla_IPv4);
        scrollPaneHistorial_IPv4.setBounds(0, 60, 680, 285);  
        
        modelo_IPv4.setRowCount(0);

        for(int i=0;i<arrayList_DatosIPv4.size();i++) {
        	modelo_IPv4.insertRow(i,arrayList_DatosIPv4.get(i));
        	tabla_IPv4.repaint();
        }
        tabla_IPv4.repaint();
	}
	
	public JScrollPane obtenerScrollPane_IPv4() {
		return scrollPaneHistorial_IPv4;
	}
	
}
