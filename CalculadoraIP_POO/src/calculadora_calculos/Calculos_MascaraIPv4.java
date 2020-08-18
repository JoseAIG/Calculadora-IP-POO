package calculadora_calculos;

/*
 * EN ESTA CLASE SE RECIBEN LOS CUATRO (4) OCTETOS DE UNA IPv4 Y SU MASCARA EN PREFIX LENGHT
 * -DICHA MASCARA (EN PREFIX LENGHT) SE TRANSFORMA A BINARIO, TENIENDOLA EN BINARIO SE TRADUCE A DECIMAL PARA HACER LAS OPERACIONES AND Y OR (&, |) A NIVEL DE BITS
 * -HABIENDO HECHO LOS CALCULOS Y HABIENDOLOS ALMACENADOS EN ARRAYS RESPECTIVOS, SE PROCEDE A MONTAR PARA QUE ESTE LISTO PARA CUANDO LA CLASE "Vista_Principal.java" SOLICITE LOS DATOS POR METODOS
 */

public class Calculos_MascaraIPv4 {

	private String maskBinario="";
	private String redCalculada="";
	private String gatewayCalculado="";
	private String broadcastCalculado="";
	private String rangoCalculado="";
	
	private int red_Q1;
	private int red_Q2;
	private int red_Q3;
	private int red_Q4;
	private int red_Q[] = {red_Q1, red_Q2, red_Q3, red_Q4}; 
	
	private int broadcast_Q1;
	private int broadcast_Q2;
	private int broadcast_Q3;
	private int broadcast_Q4;
	private int broadcast_Q[] = {broadcast_Q1, broadcast_Q2, broadcast_Q3, broadcast_Q4};
	
	private int Mask_Q1, Mask_Q2, Mask_Q3, Mask_Q4;
	private int Mask_Q[] = {Mask_Q1, Mask_Q2, Mask_Q3, Mask_Q4};
	private int mascaraPL;

	public Calculos_MascaraIPv4(int IPv4_Q1, int IPv4_Q2, int IPv4_Q3, int IPv4_Q4, int Mascara_PL) {

		this.mascaraPL = Mascara_PL;
		int IPv4_Q[]= {IPv4_Q1, IPv4_Q2, IPv4_Q3, IPv4_Q4};
		
		//TRANSFORMAR LA MASCARA DE PREFIX LENGHT A STRING BINARIO
		int contador=0;
		for(int i=0;i<Mascara_PL;i++) {
			if(contador==8 || contador==16 || contador==24) {
				maskBinario+=".";
			}
			maskBinario+="1";
			contador++;
		}
		for(int j=0;j<32-Mascara_PL;j++) {
			if(contador==8 || contador==16 || contador==24) {
				maskBinario+=".";
			}
			maskBinario+="0";
			contador++;
		}
			
		//SPLITEAR EL STRING BINARIO PARA HACER UN PARSE Y TRANSFORMAR A DECIMAL
		String mascaraSplit[] = maskBinario.split("\\.");
		for (int i=0;i<mascaraSplit.length;i++) {
			Mask_Q[i] = Integer.parseInt(mascaraSplit[i], 2);
		}

		//REALIZAR LAS OPERACIONES A NIVEL DE BITS PARA LAS DIR. DE RED Y BROADCAST
		for(int i=0;i<4;i++) {
			red_Q[i] = (IPv4_Q[i] & Mask_Q[i]);
			broadcast_Q[i] = (IPv4_Q[i] | 255-Mask_Q[i]);			
		}

		if(mascaraPL!=32) {
			redCalculada = red_Q[0] + "." + red_Q[1] + "." + red_Q[2] + "." + red_Q[3] + "/" + mascaraPL;
			gatewayCalculado = red_Q[0] + "." + red_Q[1] + "." + red_Q[2] + "." + (red_Q[3]+1);
			broadcastCalculado = broadcast_Q[0] + "." + broadcast_Q[1] + "." + broadcast_Q[2] + "." + broadcast_Q[3];
			rangoCalculado = red_Q[0] + "." + red_Q[1] + "." + red_Q[2] + "." + (red_Q[3]+1) + " - " + broadcast_Q[0] + "." + broadcast_Q[1] + "." + broadcast_Q[2] + "." + (broadcast_Q[3]-1);
			
		}
		else {
			redCalculada = "Red indefinida";
			gatewayCalculado = "Gateway indefinido";
			broadcastCalculado = "Broadcast indefinido";
			rangoCalculado = "Rango indefinido";
		}
		
	}
	
	public String mascaraBinario() {
		return maskBinario;
	}
	
	public String redCalculada() {
		return redCalculada;
	}
	
	public String gatewayCalculado() {
		return gatewayCalculado;
	}
	
	public String broadcastCalculado() {
		return broadcastCalculado;
	}
	
	public String rangoCalculado() {
		return rangoCalculado;
	}
}
