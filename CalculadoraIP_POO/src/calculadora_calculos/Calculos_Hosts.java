package calculadora_calculos;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import calculadora_historial.Calculadora_Database;

/*
 * EN ESTA CLASE SE ENCUENTRA UNA PEQUENIA VISTA UNICAMENTE USADA PARA EL CALCULO DADO UN NUMERO DE HOSTS
 * CLASE PARA EL CALCULO DE UNA IPv4 NECESARIA SEGUN UN NUMERO DADO DE HOSTS. FUNCIONAMIENTO DE LA CLASE EXPLICADO:
 * -SE INGRESA UN NUMERO DE HOSTS VALIDO (0 - 16777214) PARA DIRECCIONES PRIVADAS
 * -SE REALIZA EL DESPEJE DE LA FORMULA -> N hosts=(2^(32-mascara))-2 PARA OBTENER UNA MASCARA (EN PREFIX LENGHT)
 * -CON DICHA MASCARA SE CALCULAN EL NUMERO MAXIMO DE HOSTS POSIBLES CON LO QUE SE CALCULA: LOS HOSTS MAXIMOS, LOS HOSTS DISPONIBLES (DIFERENCIA) Y EL NRO DE DIRECCIONES
 * -TAMBIEN; CON LA MASCARA SE DETERMINA CUAL ES LA CLASE DE IPv4 PRIVADA MAS CONVENIENTE SEGUN LA NECESIDAD
 * -SE MUESTRA LA DIRECCION NECESARIA Y EL RANGO MAXIMO A LA QUE ESTA PUEDE LLEGAR.
 * 
 * AL FINAL, SI EL NUMERO DE HOSTS ESTA DENTRO DE LOS POSIBLES EN UNA RED PRIVADA Y EL NRO. DE HOSTS NO SE ENCUENTRA EN LA BBDD, SE CARGA A LA MISMA.
 */

public class Calculos_Hosts implements ActionListener{

	private JFrame frameCalculoHosts = new JFrame("Calculo de hosts");
	private JPanel panelCalculoHosts = new JPanel();
	private JButton botonCalcular = new JButton("Calcular");
	private JTextField textFieldNroHosts = new JTextField();
	private JLabel labelCalculoHosts = new JLabel("Numero de hosts que necesita:");
	private JTextArea textAreaHosts = new JTextArea();
	private int NroHosts;
	private String red_requerida;
	private String rango;
	
	Calculadora_Database DB = Calculadora_Database.instanciaDB();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*
		 * GRAFICOS PARA EL CALCULO DE HOSTS
		 */
		
		frameCalculoHosts.setLayout(null);
		frameCalculoHosts.setSize(270, 270);
		frameCalculoHosts.setLocationRelativeTo(null);
		frameCalculoHosts.setVisible(true);
		frameCalculoHosts.setContentPane(panelCalculoHosts);
		
		panelCalculoHosts.setLayout(null);
		
		Border borde1 = BorderFactory.createLoweredBevelBorder();
		
		labelCalculoHosts.setBounds(0, 10, 250, 15);
		labelCalculoHosts.setHorizontalAlignment(JLabel.CENTER);
		panelCalculoHosts.add(labelCalculoHosts);
		
		botonCalcular.setBounds(140, 30, 100, 35);
		panelCalculoHosts.add(botonCalcular);
		
		textFieldNroHosts.setBounds(10, 30, 120, 35);
		textFieldNroHosts.setFont(new Font("Helvetica",Font.BOLD,21));
		textFieldNroHosts.setBorder(borde1);
		panelCalculoHosts.add(textFieldNroHosts);
		
		textAreaHosts.setBounds(0, 70, 250, 150);
		textAreaHosts.setBackground(SystemColor.menu);
		textAreaHosts.setEditable(false);
		textAreaHosts.setFont(new Font("Helvetica",Font.PLAIN,14));
		textAreaHosts.setBorder(borde1);
		panelCalculoHosts.add(textAreaHosts);
				
		botonCalcular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean error=false;
				try {
					NroHosts = Integer.parseInt(textFieldNroHosts.getText());
					if(NroHosts<0) {
						error=true;
					}
				}catch(Exception ex) {
					error=true;
					JOptionPane.showMessageDialog(null, "Ingrese un numero de host(s) valido");
				}
					
				textAreaHosts.setText("");
				if(!error) {
					int mascara = (int) Math.floor(-((Math.log10(NroHosts+2)/Math.log10(2))-32));
					textAreaHosts.append("Mascara requerida: /" + mascara + "\n");
				
					int redQ1=0;
					int redQ2=0;
					int redQ3=0;
					int redQ4=0;
				
					int i=0; 	//Lo que se le suma al Q2
					int j=0;	//Lo que se le suma al Q3
					int k=0;	//Lo que se le suma al Q4
				
					int direcciones;
								
					//Numero de hosts maximos
					int maxHosts = (int) Math.pow(2, 32-mascara)-2;
					direcciones=(maxHosts+2);

					if(maxHosts<=16777214) {
						textAreaHosts.append("No. hosts maximos: " + maxHosts + "\n");
						textAreaHosts.append("Hosts libres: " + (maxHosts-NroHosts) + "\n");
						direcciones=(maxHosts+2);
						textAreaHosts.append("No. direcciones: " + direcciones + "\n");
					}
					else {
						System.out.println("Se excede el rango posible para una red privada");
					}
					
					if(mascara>=16 && mascara<=30) {	
						redQ1=192;
						redQ2=168;
						redQ3=0;
						redQ4=0;
					}
					else if(mascara>=12 && mascara<16) {
						redQ1=172;
						redQ2=16;
						redQ3=0;
						redQ4=0;
					}
					else if(mascara>=8 && mascara<12) {
						redQ1=10;
						redQ2=0;
						redQ3=0;
						redQ4=0;
					}
				
					if(direcciones<=256) {
						red_requerida = redQ1 + "." + redQ2 + "." + redQ3 + "." + redQ4 + "/" + mascara;
						textAreaHosts.append("La red requerida es: " + red_requerida + "\n");
						rango=redQ1 + "." + redQ2 + "." + redQ3 + "." + (redQ4+1) + " - " + redQ1 + "." + redQ2 + "." + redQ3 + "." + (redQ4+maxHosts);
						textAreaHosts.append("El rango es:\n" + rango);
					}
					else if(direcciones<=65536) {
						red_requerida = redQ1 + "." + redQ2 + "." + redQ3 + "." + redQ4 + "/" + mascara;
						textAreaHosts.append("La red requerida es: " + red_requerida + "\n");
						
						j=(direcciones/256)-1;
						k=254;
						rango = redQ1 + "." + redQ2 + "." + redQ3 + "." + (redQ4+1) + " - " + redQ1 + "." + redQ2 + "." + (redQ3+j) + "." + (redQ4+k);
						textAreaHosts.append("El rango es:\n" + rango);
					}
					else if(direcciones<=1048576) {
						red_requerida = redQ1 + "." + redQ2 + "." + redQ3 + "." + redQ4 + "/" +mascara;
						textAreaHosts.append("La red requerida es: " + red_requerida + "\n");
						
						i=(direcciones/(256*256))-1;
						j=255;
						k=254;
						rango = redQ1 + "." + redQ2 + "." + redQ3 + "." + (redQ4+1) + " - " + redQ1 + "." + (redQ2+i) + "." + (redQ3+j) + "." + (redQ4+k);
						textAreaHosts.append("El rango es:\n" + rango);
					}
					else if (direcciones<=16777216) {
						red_requerida = redQ1 + "." + redQ2 + "." + redQ3 + "." + redQ4 + "/" +mascara;
						textAreaHosts.append("La red requerida es: " + red_requerida + "\n");
						
						i=(direcciones/(256*256))-1;
						j=255;
						k=254;
						rango = redQ1 + "." + redQ2 + "." + redQ3 + "." + (redQ4+1) + " - " + redQ1 + "." + (redQ2+i) + "." + (redQ3+j) + "." + (redQ4+k);
						textAreaHosts.append("El rango es:\n" + rango);
					}
					else {
						textAreaHosts.append("El numero de host solicitados;\nexcede la cantidad posible en redes privadas.");
					}
										
					if(NroHosts<=16777214 && !DB.dbYaEstaNroHosts(NroHosts)){
						DB.dbActualizarDatos("INSERT INTO NroHosts VALUES("+NroHosts+",'"+red_requerida+"',"+maxHosts+","+(maxHosts-NroHosts)+",'"+rango+"');");
					}
					
				}
				else {
					textAreaHosts.append("Error con el numero de hosts\nque ingreso.");
				}

			}
			
		});
		
	}

	
}
