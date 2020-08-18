package calculadora_historial;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * CLASE TABLA HISTORIAL NRO HOSTS OBTIENE LOS DATOS DE LA BBDD REFERENTES A LOS CALCULOS PREVIOS DEL NUMERO DE HOSTS (TABLA 4/4)
 */

public class TablaHistorial_NroHosts {

	private ArrayList<String[]> arrayList_DatosNroHosts = new ArrayList<>();
	private JScrollPane scrollPaneHistorial_NroHosts;	
	private DefaultTableModel modelo_NroHosts = new DefaultTableModel();	
	
	public TablaHistorial_NroHosts(){
		
		Calculadora_Database DB = Calculadora_Database.instanciaDB();
		arrayList_DatosNroHosts = DB.getDatosTablaNroHosts();
		
		Object columnas[] = {"Nro. Hosts Solicitado","Dir. Red requerida","Hosts Maximos","Hosts Libres","Rango Maximo"};
		modelo_NroHosts.setColumnIdentifiers(columnas);
	    JTable tabla_NroHosts = new JTable();
	    tabla_NroHosts.setLayout(null);
	    tabla_NroHosts.setModel(modelo_NroHosts);
	    tabla_NroHosts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    tabla_NroHosts.setFillsViewportHeight(true);
	    scrollPaneHistorial_NroHosts = new JScrollPane(tabla_NroHosts);
	    scrollPaneHistorial_NroHosts.setBounds(0, 60, 680, 285);
	        
	    modelo_NroHosts.setRowCount(0);

	    for(int i=0;i<arrayList_DatosNroHosts.size();i++) {
	    	modelo_NroHosts.insertRow(i,arrayList_DatosNroHosts.get(i));
	        tabla_NroHosts.repaint();
	    }
	    tabla_NroHosts.repaint();
	    
	}
		
	public JScrollPane obtenerScrollPane_NroHosts() {
		return scrollPaneHistorial_NroHosts;	
	}
}
