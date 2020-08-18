package calculadora_graficos;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import calculadora_calculos.*;
import calculadora_historial.Calculadora_Database;

/*
 * ESTA ES LA CLASE PRINCIPAL, DONDE TODO CONVERGE, AQUI SE UBICAN LAS PRINCIPALES VISTAS CON SUS ATRIBUTOS.
 * -EN ESTA CLASE SE INGRESAN LAS IP Y SE MUESTRAN LOS RESULTADOS (VISIBLES PARA EL USUARIO) DE LOS CALCULOS DE LAS IP'S INGRESADAS
 */

public class Vista_Principal {
	
	//ATRIBUTOS DE LA VISTA 
	private JFrame marcoPrincipal = new JFrame();
	
	private JLabel labelTitulo = new JLabel("CALCULADORA DIRECCIONES IP");
	private JLabel labelTitulo_IPv4 = new JLabel("CALCULAR IPv4");
	private JLabel labelTitulo_IPv6 = new JLabel("CALCULAR IPv6");
	
	private JPanel panelInicio = new JPanel();
	private JPanel panelPrincipal_IPv4 = new JPanel();
	private JPanel panelPrincipal_IPv6 = new JPanel();
	private JPanel panelCheckBoxes_IPv4 = new JPanel();
	
	private JButton botonIPv4 = new JButton("IPv4");
	private JButton botonCalcularIPv4 = new JButton("Calcular");
	private JButton botonLimpiarIPv4 = new JButton("Limpiar");
	private JButton botonRegresarIPv4 = new JButton("<- regresar");
	private JButton boton_CalculoHosts = new JButton ("No. Hosts");
	
	private JButton botonIPv6 = new JButton("IPv6");
	private JButton botonCalcularIPv6 = new JButton("Calcular");
	private JButton botonLimpiarIPv6 = new JButton("Limpiar");
	private JButton botonRegresarIPv6 = new JButton("<- regresar");
	
	private JButton botonHistorial = new JButton("HISTORIAL");
	
	private JButton botonCerrarYSalir = new JButton("SALIR");
	
	private JTextField textFieldIPv4_Q1 = new JTextField();
	private JTextField textFieldIPv4_Q2 = new JTextField();
	private JTextField textFieldIPv4_Q3 = new JTextField();
	private JTextField textFieldIPv4_Q4 = new JTextField();
	private JTextField textFieldMascaraIPv4_PL = new JTextField();
	
	private JTextField textFieldIPv6 = new JTextField();
	
	private JCheckBox checkBoxSeleccionarTodos = new JCheckBox("CALC. TODO");
	private JCheckBox checkBoxIPv4Binario = new JCheckBox("Binario");
	private JCheckBox checkBoxTipoIP = new JCheckBox("Tipo IP");
	private JCheckBox checkBoxClase = new JCheckBox("Clase");
	private JCheckBox checkBoxDireccionRed = new JCheckBox("Dir. de red");
	private JCheckBox checkBoxGateway = new JCheckBox("Dir. Gateway");
	private JCheckBox checkBoxBroadcast = new JCheckBox("Dir. Broadcast");
	private JCheckBox checkBoxRango = new JCheckBox("Rango");
	
	private JTextArea textAreaIPv4 = new JTextArea();
	private JTextArea textAreaIPv6 = new JTextArea();
	
	private JScrollPane scrollPaneIPv4 = new JScrollPane(textAreaIPv4);
	private JScrollPane scrollPaneIPv6 = new JScrollPane(textAreaIPv6);
		
	//OTROS ATRIBUTOS
	private ArrayList<Object[]> Tabla_SesionActual = new ArrayList<>();
	
	private int IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4, MascaraIPv4_PL;
	
	public Vista_Principal(){
		
		Calculadora_Database dataBase = Calculadora_Database.instanciaDB();
		
		/*
		 * CONFIGURACIONES DEL MARCO PRINCIPAL
		 */
		
		marcoPrincipal.setLayout(null);
		marcoPrincipal.setResizable(false);
		marcoPrincipal.setSize(425, 250);
		marcoPrincipal.setLocationRelativeTo(null);
		marcoPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marcoPrincipal.setVisible(true);
		marcoPrincipal.setContentPane(panelInicio);
		marcoPrincipal.invalidate();
		marcoPrincipal.validate();
		
		/*
		 * BORDES Y FUENTES
		 */
		Border borde1 = BorderFactory.createRaisedBevelBorder();
		Border borde2 = BorderFactory.createLoweredBevelBorder();
		Border borde3 = BorderFactory.createEtchedBorder();
		
		Font helvetica_Bold = new Font("Helvetica",Font.BOLD,20);
		Font helvetica_Plain = new Font("Helvetica",Font.PLAIN,18);
		Font TimesNewRoman = new Font("Times New Roman",Font.PLAIN,21);
		
		/*
		 * CONFIGURACIONES PARA EL PANEL INICIO
		 */
		
		labelTitulo.setBounds(0, 40, 425, 40);
		labelTitulo.setFont(TimesNewRoman);
		labelTitulo.setHorizontalAlignment(JLabel.CENTER);
		labelTitulo.setBorder(borde3);
		panelInicio.add(labelTitulo);
		
		panelInicio.setLayout(null);
		
		botonIPv4.setBounds(90, 120, 110, 50);
		panelInicio.add(botonIPv4);
		botonIPv4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcoPrincipal.setContentPane(panelPrincipal_IPv4);
				marcoPrincipal.setSize(425, 500);
				marcoPrincipal.invalidate();
				marcoPrincipal.validate();
			}
		});
		
		botonIPv6.setBounds(205, 120, 110, 50);
		panelInicio.add(botonIPv6);
		botonIPv6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcoPrincipal.setContentPane(panelPrincipal_IPv6);
				marcoPrincipal.setSize(425, 500);
				marcoPrincipal.invalidate();
				marcoPrincipal.validate();
			}
		});
		
		botonCerrarYSalir.setBounds(5, 5, 90, 25);
		panelInicio.add(botonCerrarYSalir);
		botonCerrarYSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataBase.cerrarBBDD();
				marcoPrincipal.dispose();
			}
		});
		
		/*
		 * ACTION LISTENER DEL BOTON HISTORIAL
		 */
		
		botonHistorial.setBounds(90, 175, 225, 25);
		panelInicio.add(botonHistorial);
		botonHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vista_Historial historial = new Vista_Historial(Tabla_SesionActual);
			}	
		});
		
		/*
		 * CONFIGURACIONES PARA EL PANEL PRINCIPAL IPv4
		 */
		
		panelPrincipal_IPv4.setLayout(null);
		
		labelTitulo_IPv4.setBounds(140, 5, 160, 25);
		labelTitulo_IPv4.setFont(TimesNewRoman);
		panelPrincipal_IPv4.add(labelTitulo_IPv4);
		
		botonCalcularIPv4.setBounds(195, 85, 140, 35);
		panelPrincipal_IPv4.add(botonCalcularIPv4);
		
		botonLimpiarIPv4.setBounds(195, 125, 140, 35);
		panelPrincipal_IPv4.add(botonLimpiarIPv4);
		
		boton_CalculoHosts.setBounds(195, 175, 140, 35);
		panelPrincipal_IPv4.add(boton_CalculoHosts);

		panelCheckBoxes_IPv4.setLayout(null);
		panelCheckBoxes_IPv4.setBounds(5, 35, 120, 185);
		panelPrincipal_IPv4.add(panelCheckBoxes_IPv4);
		panelCheckBoxes_IPv4.setBorder(borde1);
		
		JTextField textFields_IPv4[] = {textFieldIPv4_Q1,textFieldIPv4_Q2,textFieldIPv4_Q3,textFieldIPv4_Q4,textFieldMascaraIPv4_PL};
		int x=145;
		for(int i=0;i<5;i++) {
			textFields_IPv4[i].setBounds(x, 35, 40, 35);
			textFields_IPv4[i].setBorder(borde2);
			textFields_IPv4[i].setFont(helvetica_Bold);
			panelPrincipal_IPv4.add(textFields_IPv4[i]);
			x+=50;
			
		}
		
		JLabel labelsIPv4[] = {new JLabel("."),new JLabel("."),new JLabel("."),new JLabel("/")};
		int xlabel=188;
		for(int i=0;i<4;i++) {
			labelsIPv4[i].setBounds(xlabel, 50, 10, 15);
			labelsIPv4[i].setFont(helvetica_Bold);
			panelPrincipal_IPv4.add(labelsIPv4[i]);
			xlabel+=50;
		}
		
		checkBoxSeleccionarTodos.setBounds(5, 5, 110, 30);
		panelCheckBoxes_IPv4.add(checkBoxSeleccionarTodos);
		
		JCheckBox checkBoxes_IPv4[] = {checkBoxIPv4Binario, checkBoxTipoIP, checkBoxClase, checkBoxDireccionRed, checkBoxGateway, checkBoxBroadcast, checkBoxRango};
		int y=30;
		for(int i=0;i<7;i++) {
			checkBoxes_IPv4[i].setBounds(5, y, 110, 30);
			panelCheckBoxes_IPv4.add(checkBoxes_IPv4[i]);
			y+=20;
		}
		
		checkBoxSeleccionarTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBoxSeleccionarTodos.isSelected()) {
					for(int i=0;i<7;i++) {
						checkBoxes_IPv4[i].setSelected(true);
					}
				}
				else {
					for(int i=0;i<7;i++) {
						checkBoxes_IPv4[i].setSelected(false);
					}
				}
			}
		});
		
		textAreaIPv4.setBounds(5, 230, 405, 230);
		textAreaIPv4.setBackground(SystemColor.menu);
		textAreaIPv4.setEditable(false);
		textAreaIPv4.setFont(helvetica_Plain);
		textAreaIPv4.setBorder(borde2);
		
		scrollPaneIPv4.setBounds(5,230,405,230);
		panelPrincipal_IPv4.add(scrollPaneIPv4);
		
		/*
		 * CONFIGURACIONES DEL PANEL IPv6
		 */
		
		panelPrincipal_IPv6.setLayout(null);
		
		labelTitulo_IPv6.setBounds(140, 5, 160, 25);
		labelTitulo_IPv6.setFont(TimesNewRoman);
		panelPrincipal_IPv6.add(labelTitulo_IPv6);
		
		botonCalcularIPv6.setBounds(135, 120, 140, 35);
		panelPrincipal_IPv6.add(botonCalcularIPv6);
		
		botonLimpiarIPv6.setBounds(135, 160, 140, 35);
		panelPrincipal_IPv6.add(botonLimpiarIPv6);
		
		textFieldIPv6.setBounds(80, 70, 250, 35);
		textFieldIPv6.setFont(new Font("Helvetica",Font.BOLD,16));
		textFieldIPv6.setBorder(borde2);
		panelPrincipal_IPv6.add(textFieldIPv6);
		
		textAreaIPv6.setBounds(5, 230, 405, 230);
		textAreaIPv6.setEditable(false);
		textAreaIPv6.setBackground(SystemColor.menu);
		textAreaIPv6.setBorder(borde2);
		textAreaIPv6.setFont(helvetica_Plain);
		
		scrollPaneIPv6.setBounds(5, 230, 405, 230);
		panelPrincipal_IPv6.add(scrollPaneIPv6);
		
		/*
		 * CONFIGURACION DE LOS BOTONES DE REGRESAR
		 */
		
		JButton botonesRegresar[] = {botonRegresarIPv4, botonRegresarIPv6};
		
		
		for(int i=0;i<2;i++) {
			botonesRegresar[i].setBounds(5, 5, 90, 25);
			botonesRegresar[i].setBorder(borde1);
			botonesRegresar[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					marcoPrincipal.setContentPane(panelInicio);
					marcoPrincipal.setSize(425,250);
					marcoPrincipal.invalidate();
					marcoPrincipal.validate();
				}
			});
		}
		
		panelPrincipal_IPv4.add(botonRegresarIPv4);
		panelPrincipal_IPv6.add(botonRegresarIPv6);
		
		/*
		 * ACTION LISTENER DEL BOTON CALCULO DE HOSTS
		 */
		
		Calculos_Hosts calculosHosts = new Calculos_Hosts();
		boton_CalculoHosts.addActionListener(calculosHosts);
		
		/*
		 * ACTION LISTENER DEL BOTON CALCULAR (IPv4)
		 */
		
		botonCalcularIPv4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					IPv4_Q1 = Integer.parseInt(textFieldIPv4_Q1.getText());
					IPv4_Q2 = Integer.parseInt(textFieldIPv4_Q2.getText());
					IPv4_Q3 = Integer.parseInt(textFieldIPv4_Q3.getText());
					IPv4_Q4 = Integer.parseInt(textFieldIPv4_Q4.getText());
					MascaraIPv4_PL = Integer.parseInt(textFieldMascaraIPv4_PL.getText());
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Ingrese valores validos");
				}

				textAreaIPv4.setText("");
								
				if(IPv4_Q1>=0 && IPv4_Q2>=0 && IPv4_Q3>=0 && IPv4_Q4>=0 && IPv4_Q1<=255 && IPv4_Q2<=255 && IPv4_Q3<=255 && IPv4_Q4<=255) {
					if(MascaraIPv4_PL>=1 && MascaraIPv4_PL<=32) {
						if(MascaraIPv4_PL==31) {
							JOptionPane.showMessageDialog(null, "Desaconsejable uso de la mascara /31, se ejecutaran calculos con errores...");
						}
						
						//OBTENER LA IPv4 EN BINARIO
						StringBuilder IPv4_Binario = new StringBuilder();
						int IPv4_Q[] = {IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4};
						for(int i=0;i<4;i++) {
							if(i!=0) {
								IPv4_Binario.append("." + Integer.toBinaryString(IPv4_Q[i]));
							}else {
								IPv4_Binario.append(Integer.toBinaryString(IPv4_Q[i]));
							}
						}
					
						String IPNormal = textFieldIPv4_Q1.getText() + "." +textFieldIPv4_Q2.getText() + "." + textFieldIPv4_Q3.getText() + "." + textFieldIPv4_Q4.getText();
					
						//AGREGAR IP AL ARRAYLIST DE TABLA SESION ACTUAL
						Tabla_SesionActual.add(new Object[] {"IPv4",IPNormal,"/"+MascaraIPv4_PL});
						
						//INSTANCIA DE LAS CLASES ENCARGADAS DE REALIZAR LOS CALCULOS
						Calculos_TipoIPv4 tipoIPv4 = new Calculos_TipoIPv4(IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4);
						Calculos_ClasesIPv4 claseIPv4 = new Calculos_ClasesIPv4(IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4);
						Calculos_MascaraIPv4 calcMask = new Calculos_MascaraIPv4(IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4,MascaraIPv4_PL);
						
						//CONDICIONAL PARA VER LA IPv4 Y MASCARA EN BINARIO
						if(checkBoxIPv4Binario.isSelected()) {
							textAreaIPv4.append(" • IP en binario:\n" + IPv4_Binario.toString() + "\n • Mascara en binario:\n" + calcMask.mascaraBinario() + "\n");
						}
					
						//CONDICIONAL PARA VER SI LA DIRECCION IP ES PUBLICA O PRIVADA // PROTOCOLO APIPA // UNICAST, MULTICAST O BROADCAST... ETC
						if(checkBoxTipoIP.isSelected()) {

							if(IPNormal.equals(calcMask.broadcastCalculado())) {
								textAreaIPv4.append(" • Tipo: " + "[BROADCAST] " + tipoIPv4.getTipoIP() + "\n");
							}
							else {
								textAreaIPv4.append(" • Tipo: " + tipoIPv4.getTipoIP() + "\n");
							}
						}
					
						//CONDICIONAL PARA VER LA CLASE DE LA DIRECCION IP
						if(checkBoxClase.isSelected()) {
							textAreaIPv4.append(" • Clase de la IP: " + claseIPv4.getClasesIPv4() + "\n");
						}
						
						//CONDICIONAL PARA VER LA DIRECCION DE RED
						if(checkBoxDireccionRed.isSelected()) {
							textAreaIPv4.append(" • Direccion de red: " + calcMask.redCalculada() + "\n");
						}
						
						//CONDICIONAL PARA VER LA DIRECCION DE GATEWAY
						if(checkBoxGateway.isSelected()) {
							textAreaIPv4.append(" • Direccion de gateway: " + calcMask.gatewayCalculado() + "\n");
						}
						
						//CONDICIONAL PARA VER LA DIRECCION DE BROADCAST
						if(checkBoxBroadcast.isSelected()) {
							textAreaIPv4.append(" • Direccion de broadcast: " + calcMask.broadcastCalculado() + "\n");
						}
						
						//CONDICIONAL PARA VER EL RANGO
						if(checkBoxRango.isSelected()) {
							textAreaIPv4.append(" • Rango: " + calcMask.rangoCalculado() + "\n");
						}
					
						//GUARDADO AUTOMATICO DE DATOS (TODOS, SE HAYAN CLICKEADO LOS CHECKBOXES O NO) EN DB SI Y SOLO SI LA IP NO ESTA ALMACENADA EN LA BASE DE DATOS
						if(!dataBase.dbYaEstaLaIP(IPNormal,"/"+MascaraIPv4_PL,"IPV4")) {							
							Object obj[] = {IPNormal,"/"+MascaraIPv4_PL,IPv4_Binario.toString(),calcMask.mascaraBinario(),tipoIPv4.getTipoIP(),claseIPv4.getClasesIPv4(),calcMask.redCalculada(),calcMask.gatewayCalculado(),calcMask.broadcastCalculado(),calcMask.rangoCalculado()};
							dataBase.dbIngresarIP("INSERT INTO ipv4 values(?,?,?,?,?,?,?,?,?,?)", obj,"IPv4");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Ingrese un valor correcto para la mascara (1 - 32)");
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Ingrese un valor correcto para la IP(0 - 255)");
				}
				
			}
		});
		
		/*
		 * ACTION LISTENER DEL BOTON LIMPIAR IPV4
		 */
		
		botonLimpiarIPv4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<5;i++) {
					textFields_IPv4[i].setText("");
				}
				textAreaIPv4.setText("");
			}
		});
		
		/*
		 * ACTION LISTENER DEL BOTON CALCULAR IPv6
		 */

		botonCalcularIPv6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textAreaIPv6.setText("");
				String Entrada_IPv6 = textFieldIPv6.getText();
				Ingreso_IPv6 ingreso = new Ingreso_IPv6(Entrada_IPv6);
				textAreaIPv6.append(" • IPv6 Expandida:\n" + ingreso.getIPv6Expandida() + "\n");
				textAreaIPv6.append(" • IPv6 Binario:\n" + ingreso.getIPv6Binario() + "\n");
				textAreaIPv6.append(" • Tipo de IPv6:\n" + ingreso.getTipoIPv6());
				
				
				String split_IPv6[] = ingreso.getIPv6Expandida().split("/");
				String mascara_IPv6;
				try {
					mascara_IPv6="/"+split_IPv6[1];
				}catch(Exception ex) {
					mascara_IPv6="-";
				}
				
				//SI NO HAY ERROR EN EL INGRESO DE LA IPv6, SE AGREGARA A LA TABLA DE LA SESION ACTUAL (LOCAL) Y SE CARGARA A LA BBDD SI Y SOLO SI YA LAS PRIMARY KEYS (IP Y MASCARA) NO ESTAN YA CARGADAS
				if(!ingreso.getIPv6Expandida().equals("Error en la IPv6")) {
					Tabla_SesionActual.add(new Object[] {"IPv6",split_IPv6[0],mascara_IPv6});
					if(!dataBase.dbYaEstaLaIP(split_IPv6[0], mascara_IPv6, "IPV6")) {
						Object obj[] = {split_IPv6[0],mascara_IPv6,ingreso.getIPv6Binario(),ingreso.getTipoIPv6()};
						dataBase.dbIngresarIP("INSERT INTO ipv6 values(?,?,?,?)", obj,"IPv6");
					}
				}
			}
		});
		
		/*
		 * ACTION LISTENER DEL BOTON LIMPIAR IPV6
		 */
		
		botonLimpiarIPv6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldIPv6.setText("");
				textAreaIPv6.setText("");
			}
		});
		
	}
}
