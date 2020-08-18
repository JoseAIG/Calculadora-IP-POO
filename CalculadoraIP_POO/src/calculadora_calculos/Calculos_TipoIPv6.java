package calculadora_calculos;

/*
 * EN ESTA CLASE SE RECIBE UNA IPv6 (CON O SIN MASCARA) Y SE DETERMINA QUE TIPO DE IPv6 ES DEPENDIENDO DE UNA SERIE DE CONDICIONALES
 * TAMBIEM EN ESTA CLASE SE CONVIERTE DICHA IPv6 INGRESADA A BINARIO
 */

public class Calculos_TipoIPv6 {

	private String IPv6;
	
	private int Mascara;
	private int IP[];
	private boolean tieneMascara=true;
	
	private String split_IPv6_Mascara[];
	private String split_Bloques_IPv6[];
	
	private String IPv6_Binario = "";
	private String Tipo_IPv6="No especificado";
		
	public Calculos_TipoIPv6(String IPv6) {
		
		this.IPv6 = IPv6;
		
		split_IPv6_Mascara = this.IPv6.split("/");
		split_Bloques_IPv6 = split_IPv6_Mascara[0].split(":");
	
		//PASAR LOS DATOS DEL ARRAY STRING (SPLITEADO) A ARRAY INT PARA LLEVAR A CABO LOS CONDICIONALES
		IP = new int[split_Bloques_IPv6.length];
		for(int i=0;i<IP.length;i++) {
			IP[i] = Integer.parseInt(split_Bloques_IPv6[i],16);
		}
		try {
			Mascara =  Integer.parseInt(split_IPv6_Mascara[1]);
		}catch(Exception e) {
			tieneMascara=false;
		}

		//CONSTRUCCION EN BINARIO DE LA IPv6
		for (int i=0;i<8;i++) {
			if(i!=0) {
				IPv6_Binario+=":" + Integer.toBinaryString(Integer.parseInt(split_Bloques_IPv6[i],16));
			}else {
				IPv6_Binario+=Integer.toBinaryString(Integer.parseInt(split_Bloques_IPv6[i],16));
			}
		}
		
		//CONTADOR DE BLOQUES CON CEROS 
		int contador=0;
		for(int i=0;i<8;i++) {
			if(IP[i]==0) {
				contador++;
			}
		}
		
		/*
		 * CONDICIONALES
		 */
		
		//RUTA POR DEFECTO ::/0
		if(contador==8 && Mascara==0 && tieneMascara) {
			Tipo_IPv6 = "[UNICAST] Enrutamiento, ruta por defecto.\n::/0 ";
		}
		//UNSPECIFIED ::/128
		if(contador==8 && Mascara==128) {
			Tipo_IPv6 = "[UNICAST] Software, sin especificar.\n::/128";
		}
		//LOOPBACK ::1/128
		if(contador==7 && IP[7]==1 && (!tieneMascara || Mascara==128)) {
			Tipo_IPv6 = "[UNICAST] Loopback.\n::1/128 (::1)";
		}
		//MAPEO IPV4
		if(contador==7 && IP[5]==65535 && Mascara==96) {
			Tipo_IPv6 = "[UNICAST] Mapeo IPv4.\n::ffff:0:0/96";
		}
		//IPv4 TRADUCIDA
		if(contador==7 && IP[4]==65535 && Mascara==96) {
			Tipo_IPv6 = "[UNICAST] IPv4 traducida.\n::ffff:0:0:0/96";
		}
		//TRADUCCION IPv4/IPv6
		if(contador==6 && IP[0]==100 && IP[1]==65435 && Mascara==96) {
			Tipo_IPv6 = "[UNICAST] Traduccion IPv4/IPv6. \n64:ff9b::/96";
		}
		//ENRUTAMIENTO, PREFIJO
		if(contador>=3 && contador<=7 && IP[0]==256 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==64)) {
			Tipo_IPv6 = "Enrutamiento, prefijo.\n100::/64 (100:: - 100::ffff:ffff:ffff:ffff)";
		}
		//GLOBAL UNICAST 2000::/3 (2000:: - 3FFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF)
		if(contador>=0 && contador<=7 && IP[0]>=8192 && IP[0]<=16383 && IP[1]>=0 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==3)) {
			Tipo_IPv6 = "[ANYCAST][UNICAST GLOBAL]\n2000::/3 (2000:: - 3fff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//INTERNET, TUNEL TEREDO 2001::/32 (2001:: - 2001::ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=1 && contador<=7 && IP[0]==8193 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==32)) {
			Tipo_IPv6 = "[UNICAST] Internet, tunel teredo.\n2001::/32 (2001:: - 2001::ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//INTERNET, ORCHIDv2 2001:20::/28 (2001:20:: - 2001:2f:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=6 && IP[0]==8193 && IP[1]>=32 && IP[1]<=47 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==28)) {
			Tipo_IPv6 = "Internet, ORCHIDv2.\n2001:20::/28 (2001:20:: - 2001:2f:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//DOCUMENTACION 2001:db8::/32 (2001:db8:: - 2001:db8:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador<=6 && contador>=0 && IP[0]==8193 && IP[1]==3512 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==32)) {
			Tipo_IPv6 = "[UNICAST] Documentacion.\n2001:db8::/32 (2001:db8:: - 2001:db8:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//6to4 ESQUEMA DE DIRECCIONAMIENTO 2002::/16 (2002:: - 2002:ffff:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=7 && IP[0]==8194 && IP[1]>=0 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==16)) {
			Tipo_IPv6 = "[UNICAST] Esquema direccionamiento 6to4.\n2002::/16 (2002:: - 2002:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//DOCUMENTACION 3FFF:FFFF::/32 (3fff:ffff:: - 3fff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=6 && IP[0]==16383 && IP[1]==65535 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==32)) {
			Tipo_IPv6 += "\nDocumentacion.\n3fff:ffff::/32 (3fff:ffff:: - 3fff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//RED PRIVADA DIRECCION LOCAL UNICA fc00::/7 (fc00:: - fdff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=7 && IP[0]>=64512 && IP[0]<=65023 && IP[1]>=0 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==7)) {
			Tipo_IPv6 = "[UNICAST] Red privada, direccion local unica.\nfc00::/7 (fc00:: - fdff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//ENLACE DIRECCION ENLACE LOCAL fe80::/10 (fe80:: - febf:ffff:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=7 && IP[0]>=65152 && IP[0]<=65215 && IP[1]>=0 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==10)) {
			Tipo_IPv6 = "[UNICAST] Direccion de enlace local (Local Link).\nfe80::/10 (fe80:: - febf:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//INTERNET, MULTIDIFUSION ff00::/8 (ff00:: - ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)
		if(contador>=0 && contador<=7 && IP[0]>=65280 && IP[0]<=65535 && IP[1]>=0 && IP[2]>=0 && IP[3]>=0 && IP[4]>=0 && IP[5]>=0 && IP[6]>=0 && IP[7]>=0 && (!tieneMascara || Mascara==8)) {
			Tipo_IPv6 = "[MULTICAST] Internet.\nff00::/8 (ff00:: - ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff)";
		}
		//MULTICAST ESPECIALES
		if(contador==6 && IP[0]==65281) {
			if(IP[7]==1) {
				Tipo_IPv6 = "[MULTICAST] Todos los nodos en el interface local (ff01::1)";
			}
			else if(IP[7]==2) {
				Tipo_IPv6 = "[MULTICAST] Todos los routers en el interface local (ff01::2)";
			}
		}
		else if(contador==6 && IP[0]==65282) {
			if(IP[7]==1) {
				Tipo_IPv6 = "[MULTICAST] Todos los nodos en el enlace local (ff02::1)";
			}
			else if (IP[7]==2) {
				Tipo_IPv6 = "[MULTICAST] Todos los Routers en el enlace local (ff02::2)";
			}
			else if(IP[7]==5) {
				Tipo_IPv6 = "[MULTICAST] OSPFIGP (ff02::5)";
			}
			else if(IP[7]==6) {
				Tipo_IPv6 = "[MULTICAST] Routers designados OSPFIGP (ff02::6)";
			}
			else if(IP[7]==9) {
				Tipo_IPv6 = "[MULTICAST] Routers RIP (ff02::9)";
			}
			else if(IP[7]==10) {
				Tipo_IPv6 = "[MULTICAST] Routers EIGRP (ff02::a)";
			}
			else if(IP[7]==13) {
				Tipo_IPv6 = "[MULTICAST] Todos los Routers PIM (ff02::d)";
			}
		}
		else if(contador==5 && IP[0]==65282) {
			if(IP[6]==1 && IP[7]==1) {
				Tipo_IPv6 = "[MULTICAST] Link Name (ff02::1:1)";
			}
			else if(IP[6]==1 && IP[7]==2) {
				Tipo_IPv6 = "[MULTICAST] All-dhcp-agents (ff02::1:2)";
			}
			else if(IP[6]==1 && IP[7]==3) {
				Tipo_IPv6 = "[MULTICAST] Link-local Resolucion Nombre Multicast (ff02::1:3)";
			}
		}
		else if((contador==5 || contador==6) && IP[0]==65285) {
			if(IP[6]==1 && IP[7]==3) {
				Tipo_IPv6 = "[MULTICAST] All-dhcp-servers (ff05::1:3)";
			}
			else if(IP[7]==2) {
				Tipo_IPv6 = "[MULTICAST] Todos los routers en el site-local (ff05::2)";

			}
		}
		
	}
	
	public String getTipoIPv6() {
		return Tipo_IPv6;
	}
	
	public String getIPv6Binario() {
		return IPv6_Binario;
	}
	
}
