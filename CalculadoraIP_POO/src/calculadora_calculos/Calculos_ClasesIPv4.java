package calculadora_calculos;

/*
 * EN ESTA CLASE SE RECIBEN LOS OCTETOS DE UNA IPv4 Y SE DETERMINA A QUE CLASE DE IP PERTENECE
 */

public class Calculos_ClasesIPv4 {

	private String respuesta;
	
	public Calculos_ClasesIPv4(int Q1, int Q2, int Q3, int Q4) {
		
		//CONDICIONAL PARA LA CLASE A (0.0.0.0 - 127.255.255.255)
		if(Q1>=0 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=127 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "CLASE A";
		}
		
		//CONDICIONAL PARA LA CLASE B (128.0.0.0 - 191.255.255.255)
		else if(Q1>=128 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=191 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "CLASE B";
		}
		
		//CONDICIONAL PARA LA CLASE C (192.0.0.0 - 223.255.255.255)
		else if(Q1>=192 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=223 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "CLASE C";
		}
		
		//CONDICIONAL PARA LA CLASE D (224.0.0.0 - 239.255.255.255)
		else if(Q1>=224 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=239 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "CLASE D";
		}
		
		//CONDICIONAL PARA LA CLASE E (240.0.0.0 - 255.255.255.255)
		else if(Q1>=240 && Q2>=0 && Q3>=0 && Q4>=0 && Q1<=255 && Q2<=255 && Q3<=255 && Q4<=255) {
			respuesta = "CLASE E";
		}
		
	}
	
	public String getClasesIPv4() {
		return this.respuesta;
	}
	
}
