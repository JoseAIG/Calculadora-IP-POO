package calculadora_historial;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * EN ESTA CLASE ESTA EL CONTENIDO RELACIONADO A LA CONEXION, ALMACENAMIENTO Y OBTENCION DE DATOS DE LA BBDD
 * HAY METODOS PARA: -CONECTARSE -INGRESAR IP (IPv4 O IPv6) -ACTUALIZAR DATOS (USADO PARA ELIMINAR HISTORIAL) 
 * -REVISAR SI UNA IP (IPv4, IPv6) CON SU MASCARAYA SE ENCUENTRA EN LA BBDD o NroHosts
 * -METODOS PARA OBTENER ARRAYLIST DE LOS DATOS QUE SERAN INGRESADOS A LAS TABLAS DE HISTORIAL (IPv4, IPv6 y NRO HOSTS) 
 * -METODO PARA CERRAR LA CONEXION A LA BBDD
 */

public class Calculadora_Database {
	
	private static Calculadora_Database database = new Calculadora_Database();
	private Connection conexion;
	private PreparedStatement preparedstmt;
	private ResultSet resultSet;
	private Statement statement;
		
	private Calculadora_Database() {
		try {
			Class.forName("org.postgresql.Driver");
			this.conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CalculadoraIP","postgres","masterkey");			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//METODO PARA INGRESAR LAS DIRECCIONES IPv4 Y IPv6 A LA BASE DE DATOS
	public void dbIngresarIP(String query, Object[] obj, String version) {
		try {
			this.preparedstmt = this.conexion.prepareStatement(query);
			this.preparedstmt.setString(1, (String) obj[0]);
			this.preparedstmt.setString(2, (String) obj[1]);
			this.preparedstmt.setString(3, (String) obj[2]);
			this.preparedstmt.setString(4, (String) obj[3]);
			if(version.equals("IPv4")) {
				this.preparedstmt.setString(5, (String) obj[4]);
				this.preparedstmt.setString(6, (String) obj[5]);
				this.preparedstmt.setString(7, (String) obj[6]);
				this.preparedstmt.setString(8, (String) obj[7]);
				this.preparedstmt.setString(9, (String) obj[8]);
				this.preparedstmt.setString(10, (String) obj[9]);
			}
			this.preparedstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.preparedstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//METODO PARA ACTUALIZAR 
	public void dbActualizarDatos(String query) {
		try {
			this.statement = this.conexion.createStatement();
			this.statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//METODO PARA VER SI EXISTE ESA IP YA EN LA BBDD
	public boolean dbYaEstaLaIP(String IP, String Mascara, String version) {
		boolean i=false;
		try {
			this.statement = this.conexion.createStatement();
			this.resultSet = this.statement.executeQuery("SELECT *from " + version.toLowerCase());
			while(resultSet.next()) {
				for(int j=0; j<resultSet.getRow();j++) {
					if (resultSet.getString(version.toLowerCase()).equals(IP) && resultSet.getString("mascara").equals(Mascara)) {
						i=true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.statement.close();
				this.resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	//METODO PARA SABER SI EL NRO. DE HOSTS REQUERIDOS YA ESTA EN LA BBDD
	public boolean dbYaEstaNroHosts(int NroHosts) {
		boolean i=false;
		try {
			this.statement = this.conexion.createStatement();
			this.resultSet = this.statement.executeQuery("SELECT *from NroHosts");
			while(resultSet.next()) {
				for(int j=0; j<resultSet.getRow();j++) {
					if (resultSet.getString("hosts").equals(NroHosts+"")) {
						i=true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.statement.close();
				this.resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return i;
	}

	//METODO PARA OBTENER DATOS PARA LA TABLA IPv4
	public ArrayList<String[]> getDatosTablaIPv4() {
		ArrayList<String[]> datos_IPv4 = new ArrayList<>();
		try {
			preparedstmt = conexion.prepareStatement("SELECT *FROM ipv4");
	        resultSet = preparedstmt.executeQuery();
	        while(resultSet.next()) {
	        	String datos[] = {
	        			resultSet.getString("ipv4"),
	        			resultSet.getString("mascara"),
	        			resultSet.getString("ipv4binario"),
	        			resultSet.getString("mascarabinario"),
	        			resultSet.getString("tipo"),
	        			resultSet.getString("clase"),
	        			resultSet.getString("dir_red"),
	        			resultSet.getString("dir_gateway"),
	        			resultSet.getString("dir_broadcast"),
	        			resultSet.getString("rango")
	        	};
	        	datos_IPv4.add(datos);
	        }
   
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally {
        	try {
				preparedstmt.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		return datos_IPv4;
	}
	//METODO PARA OBTENER DATOS PARA LA TABLA IPv6
	public ArrayList<String[]> getDatosTablaIPv6() {
		ArrayList<String[]> datos_IPv6 = new ArrayList<>();
		try {
			preparedstmt = conexion.prepareStatement("SELECT *FROM ipv6");
	        resultSet = preparedstmt.executeQuery();
	        while(resultSet.next()) {
	        	String datos[] = {
	        			resultSet.getString("ipv6"),
	        			resultSet.getString("mascara"),
	        			resultSet.getString("ipv6_binario"),
	        			resultSet.getString("tipo")
	        	};
	        	datos_IPv6.add(datos);
	        }
   
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally {
        	try {
				preparedstmt.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		return datos_IPv6;
	}
	
	//METODO PARA OBTENER DATOS PARA LA TABLA NRO. DE HOSTS
	public ArrayList<String[]> getDatosTablaNroHosts() {
		ArrayList<String[]> datos_NroHosts = new ArrayList<>();
		try {
			preparedstmt = conexion.prepareStatement("SELECT *FROM NroHosts");
	        resultSet = preparedstmt.executeQuery();
	        while(resultSet.next()) {
	        	String datos[] = {
	        			resultSet.getString("hosts"),
	        			resultSet.getString("red_requerida"),
	        			resultSet.getString("hosts_maximos"),
	        			resultSet.getString("hosts_libres"),
	        			resultSet.getString("rango_maximo")
	        	};
	        	datos_NroHosts.add(datos);
	        }
   
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally {
        	try {
				preparedstmt.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		return datos_NroHosts;
	}
	
	
	//METODO PARA OBTENER LA INSTANCIA
	public static Calculadora_Database instanciaDB() {
		return database;
	}
	
	//METODO PARA CERRAR LA CONEXION A LA BBDD
	public void cerrarBBDD() {
		try {
			this.conexion.close();
			JOptionPane.showMessageDialog(null, "Conexion cerrada. Hasta pronto!");
		}catch(SQLException e) {
			
		}
	}

}
