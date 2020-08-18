package calculadora_historial;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * EN ESTA CLASE SE CONSTRUYE LA TABLA IPv6 PARA EL HISTORIAL (TABLA 3/4)
 */

public class TablaHistorial_IPv6 {

	private ArrayList<String[]> arrayList_DatosIPv6 = new ArrayList<>();
	private JScrollPane scrollPaneHistorial_IPv6;	
	private DefaultTableModel modelo_IPv6 = new DefaultTableModel();	
	
	public TablaHistorial_IPv6(){
		
		Calculadora_Database DB = Calculadora_Database.instanciaDB();
		arrayList_DatosIPv6 = DB.getDatosTablaIPv6();

		Object columnas[] = {"IPv6","MASCARA","IPv6 BINARIO","TIPO"};
		modelo_IPv6.setColumnIdentifiers(columnas);
	    JTable tabla_IPv6 = new JTable();
	    tabla_IPv6.setLayout(null);
	    tabla_IPv6.setModel(modelo_IPv6);
	    tabla_IPv6.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    tabla_IPv6.setFillsViewportHeight(true);
	    scrollPaneHistorial_IPv6 = new JScrollPane(tabla_IPv6);
	    scrollPaneHistorial_IPv6.setBounds(0, 60, 680, 285);
	        
	    modelo_IPv6.setRowCount(0);

	    for(int i=0;i<arrayList_DatosIPv6.size();i++) {
	    	modelo_IPv6.insertRow(i,arrayList_DatosIPv6.get(i));
	        tabla_IPv6.repaint();
	    }
	    tabla_IPv6.repaint();
	    
	}
	
	public JScrollPane obtenerScrollPane_IPv6() {
		return scrollPaneHistorial_IPv6;	
	}
	
}
