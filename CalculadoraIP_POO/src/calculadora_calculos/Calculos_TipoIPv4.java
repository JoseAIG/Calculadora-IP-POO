package calculadora_calculos;

/*
 * ESTA CLASE RECIBE POR PARAMETROS CUATRO (4) OCTETOS DE LA DIRECCION IPv4, CON DICHOS OCTETOS SE EVALUAN CONDICIONALES PARA DETERMINAR QUE TIPO DE IPv4 ES
 */

public class Calculos_TipoIPv4 {

	private String respuesta;
	
	public Calculos_TipoIPv4(int Q1, int Q2, int Q3, int Q4) {

		/*
		 * CONDICIONALES DIRECCIONES PRIVADAS
		 */
		
		//CONDICIONAL PRIVADA CLASE A (10.0.0.0 - 10.255.255.255)
		if(Q1==10 && Q2>=0 && Q3>=0 && Q4>=0 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "PRIVADA CLASE A (10.0.0.0 - 10.255.255.255)";
		}
		
		//CONDICIONAL PRIVADA CLASE B (172.16.0.0 - 172.31.255.255)
		else if(Q1==172 && Q2>=16 && Q3>=0 && Q4>=0 && Q2<=31 && Q3<=255 && Q4<=255) {
			respuesta = "PRIVADA CLASE B (172.16.0.0 - 172.31.255.255)";
		}
		
		//CONDICIONAL PRIVADA CLASE C (192.168.0.0 - 192.168.255.255)
		else if(Q1==192 && Q2==168 && Q3>=0 && Q4>=0 && Q3<=255 && Q4<=255) {
			respuesta = "PRIVADA CLASE C (192.168.0.0 - 192.168.255.255)";
		}
		
		/*
		 * CONDICIONALES OTRAS DIRECCIONES RESERVADAS (Incluye: APIPA, UNICAST, BROADCAST, MULTICAST...)
		 */
		
		//CONDICIONAL RESERVADO A SOFTWARE (0.0.0.0 - 0.255.255.255)
		else if(Q1==0 && Q2>=0 && Q3>=0 && Q4>=0 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "RESERVADO A SOFTWARE\n\t(0.0.0.0 - 0.255.255.255)";
		}
		
		//CONDICIONAL RESERVADA Espacio de direcciones compartido (100.64.0.0 – 100.127.255.255)
		else if(Q1==100 && Q2>=64 && Q3>=0 && Q4>=0 && Q2<=127 && Q3<=255 && Q4<=255) {
			respuesta = "RESERVADA Espacio de direcciones compartido (100.64.0.0 – 100.127.255.255)";
		}
		
		//CONDICIONAL RESERVADO A LOOPBACK (127.0.0.0 - 127.255.255.255)
		else if(Q1==127 && Q2>=0 && Q3>=0 && Q4>=0 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "RESERVADO A LOOPBACK (127.0.0.0 - 127.255.255.255)";
		}
		
		//CONDICIONAL APIPA [UNICAST] (169.254.0.0 - 169.254.255.255)
		else if(Q1==169 && Q2==254 && Q3>=0 && Q4>=0 && Q3<=255 && Q4<=255) {
			respuesta = "APIPA [UNICAST] (169.254.0.0 - 169.254.255.255)";
		}
		
		//CONDICIONAL ASIGNACIONES PROTOCOLO IETF (192.0.0.0 - 192.0.0.255)
		else if(Q1==192 && Q2==0 && Q3==0 && Q4>=0 && Q4<=255) {
			respuesta = "ASIGNACIONES PROTOCOLO IETF (192.0.0.0 - 192.0.0.255)";
		}
		
		//CONDICIONAL TEST-NET-1 (192.0.2.0 - 192.0.2.255)
		else if(Q1==192 && Q2==0 && Q3==2 && Q4>=0 && Q4<=255) {
			respuesta = "TEST-NET-1 (192.0.2.0 - 192.0.2.255)";
		}
		
		//CONDICIONAL RELAY IPv6 - IPv4 (192.88.99.0 - 192.88.99.255)
		else if(Q1==192 && Q2==88 && Q3==99 && Q4>=0 && Q4<=255) {
			respuesta = "RELAY IPv6 - IPv4 (192.88.99.0 - 192.88.99.255)";
		}
		
		//CONDICIONAL RESERVADO A pruebas de referencia de comunicaciones entre dos subredes separadas (198.18.0.0 - 198.19.255.255)
		else if(Q1==198 && Q2>=18 && Q3>=0 && Q4>=0 && Q2<=19 && Q3<=255 && Q4<=255) {
			respuesta = "RESERVADO A PRUEBAS DE REFERENCIA DE COMUNICACIONES ENTRE DOS SUBREDES SEPARADAS (198.18.0.0 - 198.19.255.255)";
		}
		
		//CONDICIONAL TEST-NET-2 (198.51.100.0 - 198.51.100.255)
		else if(Q1==198 && Q2==51 && Q3==100 && Q4>=0 && Q4<=255) {
			respuesta = "TEST-NET-2 (198.51.100.0 - 198.51.100.255)";
		}
		
		//CONDICIONAL TEST-NET-3 (203.0.113.0 - 203.0.113.255)
		else if(Q1==203 && Q2==0 && Q3==0 && Q4>=0 && Q4<=255) {
			respuesta = "TEST-NET-3 (203.0.113.0 - 203.0.113.255)";
		}
		
		//CONDICIONAL IP MULTICAST (224.0.0.0 - 239.255.255.255)
		else if(Q1>=224 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=239 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "IP MULTICAST (224.0.0.0 - 239.255.255.255)";
		}
		
		//CONDICIONAL RESERVADO PARA USO FUTURO (240.0.0.0 - 255.255.255.254)
		else if(Q1>=240 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=255 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "RESERVADO PARA USO FUTURO (240.0.0.0 - 255.255.255.254)";
		}
		
		//CONDICIONAL RESERVADO PARA DESTINOS MULTIDIFUSION
		else if(Q1==255 && Q2==255 && Q3==255 && Q4==255) {
			respuesta = "RESERVADO PARA DESTINOS MULTIDIFUSION (255.255.255.255)";
		}
		
		else respuesta = "PUBLICA";
		
		
	}
	
	//METODO GETTER PARA LA RESPUESTA DEL TIPO DE DIRECCION IPv4
	public String getTipoIP() {
		return this.respuesta;
	}
	
}
