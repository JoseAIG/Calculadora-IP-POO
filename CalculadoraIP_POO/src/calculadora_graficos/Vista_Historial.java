package calculadora_graficos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import calculadora_historial.Calculadora_Database;
import calculadora_historial.TablaHistorial_IPv4;
import calculadora_historial.TablaHistorial_IPv6;
import calculadora_historial.TablaHistorial_NroHosts;
import calculadora_historial.TablaHistorial_SesionActual;

/*
 * EN ESTA CLASE SE MUESTRAN LAS CUATRO (4) TABLAS DE HISTORIAL, A GUSTO DEL USUARIO:
 * - TABLA SESION ACTUAL (LOCAL) (TABLA 1)
 * - TABLA IPv4 (TABLA 2)
 * - TABLA IPv6 (TABLA 3)
 * - TABLA NRO HOSTS (TABLA 4)
 * 
 * EN ESTA CLASE TAMBIEN SE PERMITE ELIMINAR EL HISTORIAL, LO CUAL BORRARA EL CONTENIDO DE LAS 3 TABLAS (OBVIAMENTE TAMBIEN DE LA BBDD)
 */

public class Vista_Historial {

	//ATRIBUTOS DE LA VISTA
	private JFrame marcoHistorial = new JFrame();
	
	private JPanel panelPrincipalHistorial = new JPanel();
	private JPanel panelRadioButtons = new JPanel();
	
	private JRadioButton radioButton_IPv4 = new JRadioButton("Historial IPv4");
	private JRadioButton radioButton_IPv6 = new JRadioButton("Historial IPv6");
	private JRadioButton radioButton_NroHosts = new JRadioButton("Nro. de hosts");
	private JRadioButton radioButton_SesionActual = new JRadioButton("Sesion actual");
	private JRadioButton radioButtons_Historial[] = {radioButton_SesionActual, radioButton_IPv4, radioButton_IPv6, radioButton_NroHosts};
	
	private ButtonGroup BG_Historial = new ButtonGroup(); 
	
	private JScrollPane scrollPaneHistorial_SesionActual;
	private JScrollPane scrollPaneHistorial_IPv4 = new JScrollPane();
	private JScrollPane scrollPaneHistorial_IPv6 = new JScrollPane();
	private JScrollPane scrollPaneHistorial_NroHosts = new JScrollPane();

	private JButton botonLimpiarHistorial = new JButton("LIMPIAR HISTORIAL");
	
	public Vista_Historial(ArrayList<Object[]> AL) {
				
		marcoHistorial.setLayout(null);
		marcoHistorial.setTitle("Historial de registros");
		marcoHistorial.setSize(700,400);
		marcoHistorial.setLocationRelativeTo(null);
		marcoHistorial.setVisible(true);
		marcoHistorial.setContentPane(panelPrincipalHistorial);
		marcoHistorial.invalidate();
		marcoHistorial.validate();
		marcoHistorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panelPrincipalHistorial.setLayout(null);
		
		Border borde1 = BorderFactory.createRaisedBevelBorder();
		
		/*
		 * CONFIG DE LOS RADIO BUTTON
		 */
		panelRadioButtons.setLayout(null);
		panelRadioButtons.setBounds(10,10,430,40);
		panelRadioButtons.setBorder(borde1);
		panelPrincipalHistorial.add(panelRadioButtons);
		int x=5;
		for(int i=0; i<4;i++) {
			radioButtons_Historial[i].setBounds(x, 12, 105, 15);
			panelRadioButtons.add(radioButtons_Historial[i]);
			BG_Historial.add(radioButtons_Historial[i]);
			x+=105;
		}
		radioButtons_Historial[0].setSelected(true);
		
		//CONFIGURACION DE LA TABLA QUE SE MOSTRARA AL INICIAR LA VISTA HISTORIAL (SESION ACTUAL)
		TablaHistorial_SesionActual sesionActual = new TablaHistorial_SesionActual(AL);
		scrollPaneHistorial_SesionActual = sesionActual.obtenerScrollPane_SesionActual();
        panelPrincipalHistorial.add(scrollPaneHistorial_SesionActual);
        
		/*
		 * ACTION LISTENERS DE LOS RADIO BUTTON
		 */
		
		radioButton_SesionActual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPaneHistorial_IPv4.setVisible(false);
				scrollPaneHistorial_IPv6.setVisible(false);
				scrollPaneHistorial_NroHosts.setVisible(false);

				TablaHistorial_SesionActual sesionActual = new TablaHistorial_SesionActual(AL);
				scrollPaneHistorial_SesionActual = sesionActual.obtenerScrollPane_SesionActual();
		        panelPrincipalHistorial.add(scrollPaneHistorial_SesionActual);
				scrollPaneHistorial_SesionActual.setVisible(true);
			}	
		});
		
		radioButton_IPv4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPaneHistorial_SesionActual.setVisible(false);
				scrollPaneHistorial_IPv6.setVisible(false);
				scrollPaneHistorial_NroHosts.setVisible(false);

				TablaHistorial_IPv4 tablaIPv4 = new TablaHistorial_IPv4();
				scrollPaneHistorial_IPv4 = tablaIPv4.obtenerScrollPane_IPv4();
				
				panelPrincipalHistorial.add(scrollPaneHistorial_IPv4);

			}
			
		});
		
		radioButton_IPv6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPaneHistorial_SesionActual.setVisible(false);
				scrollPaneHistorial_IPv4.setVisible(false);
				scrollPaneHistorial_NroHosts.setVisible(false);
				
				TablaHistorial_IPv6 tablaIPv6 = new TablaHistorial_IPv6();
				scrollPaneHistorial_IPv6 = tablaIPv6.obtenerScrollPane_IPv6();
				
				panelPrincipalHistorial.add(scrollPaneHistorial_IPv6);
			}
			
		});
		
		radioButton_NroHosts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPaneHistorial_SesionActual.setVisible(false);
				scrollPaneHistorial_IPv4.setVisible(false);
				scrollPaneHistorial_IPv6.setVisible(false);
				
				TablaHistorial_NroHosts tablaNroHosts = new TablaHistorial_NroHosts();
				scrollPaneHistorial_NroHosts = tablaNroHosts.obtenerScrollPane_NroHosts();
				
				panelPrincipalHistorial.add(scrollPaneHistorial_NroHosts);
			}
			
		});
		
		/*
		 * BOTON LIMPIAR HISTORIAL
		 */
		
		botonLimpiarHistorial.setBounds(450, 10, 190, 40);
		panelPrincipalHistorial.add(botonLimpiarHistorial);
		botonLimpiarHistorial.setBackground(Color.RED);
		botonLimpiarHistorial.setBorder(borde1);
		botonLimpiarHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int borrarHistorial = JOptionPane.showConfirmDialog(null, "Esta seguro?");
				if(borrarHistorial==0) {
					Calculadora_Database DB = Calculadora_Database.instanciaDB();
					AL.clear();
					DB.dbActualizarDatos("DELETE FROM IPv4");
					DB.dbActualizarDatos("DELETE FROM IPv6");
					DB.dbActualizarDatos("DELETE FROM NroHosts");
					JOptionPane.showMessageDialog(null, "Historial eliminado");
					marcoHistorial.dispose();
					
				}
			}
		});
		
	}
	
}
