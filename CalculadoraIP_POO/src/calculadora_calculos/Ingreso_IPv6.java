package calculadora_calculos;

import javax.swing.JOptionPane;

/*
 * ESTA CLASE ES SUMAMENTE IMPORTANTE, AQUI SE FILTRAN LAS IPv6 INGRESADAS POR EL USUARIO. ARROJANDO ERRORES DE SINTAXIS EN CASO DE HABER...
 * - ESTA CLASE LLAMA A LA CLASE "Calculos_IPv6_TraduccionIPv4" PARA VER SI DICHA IPv6 INGRESADA ES DE LA FORMA MIXTA, DE SER ASI ESA CLASE RETORNA QUE TIPO ES.
 * - ESTA CLASE LLAMA A LA CLASE "Calculos_TipoIPv6" PARA DETERMINAR SEGUN CONDICIONALES A QUE RANGO DE IP'S PERTENECE Y SI ES UNICAST, MULTICAST, ETC...
 * - ESTA CLASE ES LLAMADA POR LA CLASE "Vista_Principal" PASANDO POR PARAMETROS LA IPv6 INICIALMENTE INGRESADA POR EL USUARIO
 * - TAMBIEN SE COMPLETAN LAS IPv6 EN CASO DE TENER "::" O BLOQUES CON MENOS DE 4 CARACTERES (a -> 000a) PARA EL CORRECTO ALMACENADO EN LA BBDD
 */

public class Ingreso_IPv6 {
	
	private String Entrada_IPv6;
	private String Salida_IPv6; //IPv6 FILTRADA Y LISTA PARA OPERAR!!
	private String Filtro_IPv6;
	private int Mascara;
	private boolean tieneMascara=false;
	private boolean esTraduccion=false;
	
	private String Tipo_IPv6 = "";
	private String IPv6_Binario = "";
	
	private StringBuilder IPv6PrimeraConstruccion = new StringBuilder();
	private StringBuilder IPv6SegundaConstruccion = new StringBuilder();
	
	public Ingreso_IPv6(String IPv6_Preliminar) {
		
		this.Entrada_IPv6=IPv6_Preliminar;
		
		String Div_IPv6_Mascara[] = Entrada_IPv6.split("/");
		String IPv6[] = Div_IPv6_Mascara[0].split(":");

		int longitudIPv6 = IPv6.length;

		/*
		 * IPv6 MIXTA (NNNN:NNNN:NNNN:NNNN:www.xxx.yyy.zzz)
		 */
		Calculos_IPv6_TraduccionIPv4 traduccionIPv4 = new Calculos_IPv6_TraduccionIPv4(Entrada_IPv6);
		esTraduccion = traduccionIPv4.esTraduccionIPv6IPv4();
		
		
		int numeroErrores=0;
		
		/*
		 * FILTRADO DE LA MASCARA DE LA IPv6
		 */
		
		if(Div_IPv6_Mascara.length==2 && !Entrada_IPv6.startsWith("/") && !Entrada_IPv6.endsWith("/")) {
			try {
				Mascara = Integer.parseInt(Div_IPv6_Mascara[1]);
				tieneMascara=true;
			}catch(Exception e) {
				numeroErrores++;
				JOptionPane.showMessageDialog(null, "Ingrese una mascara valida (Valores de /0 - /128)");
			}
		}
		else if(Entrada_IPv6.startsWith("/") || Entrada_IPv6.endsWith("/") || Div_IPv6_Mascara.length>2) {
			numeroErrores++;
			JOptionPane.showMessageDialog(null, "Ingrese una mascara valida (Haga uso correcto del /)");
		}

		if(Mascara>128 || Mascara<0) {
			numeroErrores++;
			JOptionPane.showMessageDialog(null, "Error en la mascara: Ingrese un valor de 0 a 128");
		}
		
		/*
		 * FILTRADO DE LA IPv6
		 */
		
		//RECONSTRUCCION DE LA IPv6 PRELIMINAR
		int contadorAppends = 0;
		if(longitudIPv6<=8) {
			
			for(int i=0;i<longitudIPv6;i++) {
				
				if(IPv6[i].equals("")) {
					
					for(int j=0;j<8-longitudIPv6+1;j++) {
						if(i!=0) {
							IPv6PrimeraConstruccion.append(":0000");
							contadorAppends++;
							
						}else {
							IPv6PrimeraConstruccion.append("0000");
							contadorAppends++;
							break;
						}
						
					}
					
				}
				else {
					if(i!=0) {
						IPv6PrimeraConstruccion.append(":" + IPv6[i]);
						contadorAppends++;
					}else {
						IPv6PrimeraConstruccion.append(IPv6[i]);
						contadorAppends++;
					}
				}
				
			}
				
		}
		//ALGORITMO PARA LLENAR DE CEROS DONDE ES NECESARIO EJ: a -> 000a
		String IPv6Pivote[] = IPv6PrimeraConstruccion.toString().split(":");
		for(int i=0;i<IPv6Pivote.length;i++) {
			if(IPv6Pivote[i].length()<4 && !Div_IPv6_Mascara[0].equals("::")) {
				while(IPv6Pivote[i].length()!=4) {
					IPv6Pivote[i] = "0" + IPv6Pivote[i];
				}
			}
			if(IPv6Pivote[i].length()==4) {
				if(i!=0) {
					IPv6SegundaConstruccion.append(":" + IPv6Pivote[i]);
				}else {
					IPv6SegundaConstruccion.append(IPv6Pivote[i]);
				}
			}else {
				IPv6SegundaConstruccion.append(IPv6Pivote[i]);
			}
		}
		
		//SI YA SE CONSTRUYERON LOS 8 BLOQUES Y SE ENCUENTRAN :: AL FINAL ENTRA EN ESTE CONDICIONAL, ARROJANDO ERROR AL USUARIO
		if((contadorAppends==8 || longitudIPv6==8) && Div_IPv6_Mascara[0].endsWith("::")) {
			JOptionPane.showMessageDialog(null, "Error de sintaxis al final: termina con '::' que estan de mas");
			numeroErrores++;
		}
		
		//EN CASO DE QUE LA ENTRADA NO SEA "::", SI EL CONTADOR DE APPENDS ES 0 Y SI LA LONGITUD DE LA IPv6 ES DIFERENTE DE 8, O EL CONTADOR DE APPENDS EXCEDE LOS 0 BLOQUES, ARROJA ERROR DE SINTAXIS
		if(!Div_IPv6_Mascara[0].equals("::")) {
			if((contadorAppends==0 && longitudIPv6!=8) || contadorAppends>8) {
				JOptionPane.showMessageDialog(null, "Error de sintaxis");
				numeroErrores++;
			}
		}
		
		//EN CASO DE QUE LA IPv6 EMPIECE CON ":" Y EL CHAR EN SEGUNDA POSICION NO SEA OTROS ":", ARROJARA ERROR
		String caracterEnSegundaPosicion = String.valueOf(Div_IPv6_Mascara[0].charAt(1));
		if(Div_IPv6_Mascara[0].startsWith(":") && !(caracterEnSegundaPosicion.equals(":"))) {
			JOptionPane.showMessageDialog(null, "Error de sintaxis: La ip comienza con ':'");
			numeroErrores++;
		}
		
		//ALGORITMO PARA ANEXAR LA CANTIDAD DE BLOQUES CON "0000" SI LOS :: ESTAN AL FINAL SIN USAR .endsWith()		
		int pivote=0;
		if(longitudIPv6<8 && Div_IPv6_Mascara[0].contains("::") && contadorAppends<8) {
			pivote = contadorAppends;
			for(int k=0;k<8-pivote;k++) {
				IPv6SegundaConstruccion.append(":0000");
				contadorAppends++;
			}
		}
				
		//LA VARIABLE PIVOTE (AUXILIAR DEL CONTADOR DE APPENDS) SI ES IGUAL A 0, ES DECIR NO SE ENTRO AL CONDICIONAL DE ARRIBA, EN CASO DE QUE LA IPv6 FINALICE CON ":" Y NO SEA LA IPv6 "::", ARROJARA ERROR
		if(pivote==0) {
			if(Div_IPv6_Mascara[0].endsWith(":") && !Div_IPv6_Mascara[0].equals("::")) {
				JOptionPane.showMessageDialog(null, "Error de sintaxis con ':' al final");
				numeroErrores++;
			}
		}
		
		//EN CASO QUE LA IPv6 SEA "::" SE ELIMINA EL ":" SOBRANTE AL INICIO DE LA IP (ESTO ES POR LA NATURALEZA DEL ALGORITMO)
		if(Div_IPv6_Mascara[0].equals("::")) {
			IPv6SegundaConstruccion.delete(0, 1);
		}
		
		//POR SUPUESTO SI NO SE COMPLETAN LOS 8 BLOQUES, YA SEA POR APPENDS INCOMPLETOS O LONGITUD FALLA DE CADENA AL INGRESO, HABRA ERROR (IPv6 INCOMPLETA)
		if(contadorAppends<8 && longitudIPv6<8 && !esTraduccion) {
			JOptionPane.showMessageDialog(null, "Error de sintaxis: IPv6 incompleta");
			numeroErrores++;
		}
		
		//EN CASO DE QUE LOS ERRORES SEAN "CERO" SI EXISTEN DE ::: - ::::::: EN LA SINTAXIS, ARROJARA ERROR AL USUARIO
		if(numeroErrores==0) {
			String dospuntos=":::";
			boolean bandera = false;
			for(int i=0;i<8;i++) {
				if(Div_IPv6_Mascara[0].contains(dospuntos)) {
					numeroErrores++;
					dospuntos+=":";
					bandera=true;
				}
			}
			if(bandera) {
				JOptionPane.showMessageDialog(null, "Error de sintaxis: problema con los ':'");
			}
		}

		//PRIMERA SALIDA DE LAS IPv6 TRAS PASAR EL FILTRADO INICIAL
		if(numeroErrores==0 && contadorAppends==0) {
			Filtro_IPv6 = Div_IPv6_Mascara[0];
			
		}
		else if(numeroErrores==0 && contadorAppends>0) {
			Filtro_IPv6 = IPv6SegundaConstruccion.toString();
		}
		else {
			Salida_IPv6 = "Error en la IPv6";
		}

		
		/*
		 * SEGUNDO FILTRADO (REVISAR SI LOS CARACTERES INTRODUCIDOS ESTAN BIEN)
		 */
		
		//DETERMINAR SI HAY BLOQUES CON MAS DE 4 CARACTERES O TIENEN UN VALOR NO HEXADECIMAL
		if((numeroErrores==0 && contadorAppends>=0 && !esTraduccion)) {
			String IPv6Armada[] = Filtro_IPv6.split(":");
			boolean bandera = false;
			for(int i=0;i<IPv6Armada.length;i++) {
				if(IPv6Armada[i].length()>4) {
					bandera=true;
				}
				try{
					Integer.parseInt(IPv6Armada[i],16);
				}catch(Exception e) {
					bandera=true;
				}
			}
			if(bandera) {
				JOptionPane.showMessageDialog(null, "Error de sintaxis: en uno(s) bloques de la direccion");
				numeroErrores++;
				Salida_IPv6 = "Error en la IPv6";
			}
			else {
				Salida_IPv6 = Filtro_IPv6;
				if(tieneMascara) {
					Salida_IPv6+="/" + Mascara;
				}				
				Calculos_TipoIPv6 tipoIPv6 = new Calculos_TipoIPv6(Salida_IPv6);
				Tipo_IPv6 = tipoIPv6.getTipoIPv6();
				IPv6_Binario = tipoIPv6.getIPv6Binario();
			}
		}
		
		if(esTraduccion) {
			Salida_IPv6 = traduccionIPv4.SalidaIPv6_TraduccionIPv4();
			Tipo_IPv6 = traduccionIPv4.SalidaTipoIPv6_TraduccionIPv4();
			IPv6_Binario = traduccionIPv4.SalidaIPv6_TraduccionIPv4_Binario();
		}
				
	}
	
	public String getIPv6Expandida() {
		return Salida_IPv6;
	}
	public String getTipoIPv6() {
		return Tipo_IPv6;
	}
	public String getIPv6Binario() {
		return IPv6_Binario;
	}
	
}

