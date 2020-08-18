package calculadora_calculos;

/*
 * ESTA CLASE RECIBE UNA IPv6 Y DETERMINA SI ESTA ESTA ESCRITA DE LA FORMA MIXTA (4 PRIMEROS BLOQUES HEXADECIMALES (separador por ":") Y 4 ULTIMOS BLOQUES EN DECIMAL (separados por "."))
 * TAMBIEN COMPLETA LA IP Y LA TRANSFORMA EN BINARIO, DADO QUE EL MECANISMO DE SELECCION DE ESTA CLASE ES DIFERENTE AL DE LA CLASE "Ingreso_IPv6.java" LA CUAL SE COMUNICA CON ESTA
 * A SU VEZ, DETERMINA QUE TIPO DE IPv6 MIXTA ES, ES DECIR, SI ES TRADUCCION, MAPEO O 6to4
 */

public class Calculos_IPv6_TraduccionIPv4 {

	private boolean esTraduccion = false;
	private boolean error = false;
	private String IPv6_Ingresada;
	private String tipo_IPv6Mixta = "IPv6 con nomenclatura mixta\nP.Ej: (NNNN:NNNN:NNNN:NNNN:www.xxx.yyy.zzz)\n";
	private String IPv6Mixta_Binario = "";
	private boolean tieneMask = false;
	private int mascara;
	
	public Calculos_IPv6_TraduccionIPv4(String IPv6) {
				
		IPv6_Ingresada = IPv6;
		String Div_IPv6_Mascara[] = IPv6.split("/");
		String bloques_IPv6[] = Div_IPv6_Mascara[0].split(":");
		
		int bloquesReales_IPv6 = (bloques_IPv6.length-1);
		
		int resta_EspaciosVacios=0; 
		for(int i=0;i<bloquesReales_IPv6;i++) {
			if(bloques_IPv6[i].equals("")) {
				resta_EspaciosVacios++;
			}
		}
		
		bloquesReales_IPv6 = bloquesReales_IPv6 - resta_EspaciosVacios;
		if(IPv6_Ingresada.contains("::")) {
			String bloquesASustituir="";
			for(int i=0;i<4-bloquesReales_IPv6;i++) {
				if(IPv6_Ingresada.startsWith("::")) {
					bloquesASustituir+="0000:";
				}else {
					bloquesASustituir+=":0000:";
				}
			}
			IPv6_Ingresada = IPv6_Ingresada.replace("::", bloquesASustituir);
			if(IPv6_Ingresada.contains("::")) {
				IPv6_Ingresada = IPv6_Ingresada.replace("::", ":");
			}
		}
		
		try {
			
			String splitFinal_IPv6[] = IPv6_Ingresada.split(":");
			int IPv6_Q[] =  new int[splitFinal_IPv6.length];
			for(int k=0;k<(splitFinal_IPv6.length-1);k++) {
				try {
					IPv6_Q[k] = Integer.parseInt(splitFinal_IPv6[k],16);
					if(k!=0) {
						IPv6Mixta_Binario+=":"+Integer.toBinaryString(IPv6_Q[k]);
					}else {
						IPv6Mixta_Binario+=Integer.toBinaryString(IPv6_Q[k]);
					}
				}catch(Exception ex) {
					error=true;
				}
				if(IPv6_Q[k]<0 || IPv6_Q[k]>65535) {
					error=true;
				}
			}
			
			String splitPuntos[] = bloques_IPv6[bloques_IPv6.length-1].split("\\.");
			int parteIPv4[] = new int[splitPuntos.length];
			for(int j=0;j<parteIPv4.length;j++) {
				try {
					parteIPv4[j] = Integer.parseInt(splitPuntos[j],10);
					if(j!=0) {
						IPv6Mixta_Binario+="." + Integer.toBinaryString(parteIPv4[j]);
					}else {
						IPv6Mixta_Binario+=":" + Integer.toBinaryString(parteIPv4[j]);
					}
				}catch(Exception e) {
					error=true;
				}
				if(parteIPv4[j]>255 || parteIPv4[j]<0) {
					error=true;
				}
			}
			
			String problemaDosPuntos = ":::";
			
			for(int i=0;i<Div_IPv6_Mascara[0].length();i++) {
				if(Div_IPv6_Mascara[0].contains(problemaDosPuntos)) {
					problemaDosPuntos+=":";
					error=true;
				}
				
			}
			try {
				mascara = Integer.parseInt(Div_IPv6_Mascara[1]);
				tieneMask =true;
				if(mascara>128 || mascara<0) {
					error=true;	
				}
			}catch(Exception ex) {
				
			}
			
			if(parteIPv4.length!=4) {
				error=true;
			}
			
			if(IPv6_Ingresada.startsWith(":") || IPv6_Ingresada.endsWith(":")) {
				error=true;
			}
			
			
			//AUDITORIA FINAL
			int bloquesTotales = (splitFinal_IPv6.length-1) + splitPuntos.length;
			if(bloquesTotales==8 && splitPuntos.length==4 && error==false) {
				esTraduccion=true;
				
				//CONDICIONALES PARA TIPO IPv6 MIXTA
				
				//Software, Dirección IPv4 mapeada ::ffff:0:0/96 (::ffff:0.0.0.0 -::ffff:255.255.255.255)
				if(IPv6_Q[0]==0 && IPv6_Q[1]==0 && IPv6_Q[2]==0 && IPv6_Q[3]==65535 && parteIPv4[0]>=0 && parteIPv4[1]>=0 && parteIPv4[2]>=0 && parteIPv4[3]>=0 && (mascara==96 || !tieneMask)) {
					tipo_IPv6Mixta += "[UNICAST] SOFTWARE, DIRECCION IPv4 MAPEADA\n ::ffff:0:0/96 (::ffff:0.0.0.0 - ::ffff:255.255.255.255)";
				}
				//SOFTWARE, IPv4 TRADUCIDA ::ffff:0:0:0/96 (::ffff:0:0.0.0.0 - ::ffff:0:255.255.255.255)
				if(IPv6_Q[0]==0 && IPv6_Q[1]==0 && IPv6_Q[2]==65535 && IPv6_Q[3]==0 && parteIPv4[0]>=0 && parteIPv4[1]>=0 && parteIPv4[2]>=0 && parteIPv4[3]>=0 && (mascara==96 || !tieneMask)) {
					tipo_IPv6Mixta += "[UNICAST] SOFTWARE, IPv4 TRADUCIDA\n ::ffff:0:0:0/96 (::ffff:0:0.0.0.0 - ::ffff:0:255.255.255.255)";
				}
				//INTERNET, TRADUCCION IPv4/IPv6 64:ff9b::/96 (64:ff9b::0.0.0.0 - 64:ff9b::255.255.255.255)
				if(IPv6_Q[0]==100 && IPv6_Q[1]==65435 && IPv6_Q[2]==0 && IPv6_Q[3]==0 && parteIPv4[0]>=0 && parteIPv4[1]>=0 && parteIPv4[2]>=0 && parteIPv4[3]>=0 && (mascara==96 || !tieneMask)) {
					tipo_IPv6Mixta += "[UNICAST] INTERNET, TRADUCCION IPv4/IPv6\n 64:ff9b::/96 (64:ff9b::0.0.0.0 - 64:ff9b::255.255.255.255)";
				}
				
			}
			
		}
		catch(Exception e) {
			
		}
			
	}
	
	public boolean esTraduccionIPv6IPv4() {
		return esTraduccion;
	}
	
	public String SalidaIPv6_TraduccionIPv4() {
		return IPv6_Ingresada;
	}
	
	public String SalidaIPv6_TraduccionIPv4_Binario() {
		return IPv6Mixta_Binario;
	}
	
	public String SalidaTipoIPv6_TraduccionIPv4() {
		return tipo_IPv6Mixta;
	}
	
}
