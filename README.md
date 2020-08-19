# Calculadora-IP-POO
Calculadora de direcciones IPv4 e IPv6 Programacion Orientada a Objetos

Jose Inciarte 
C.I. 27.696.083

• Para este proyecto se configuro el Build Path, añadiendo un .jar externo (jdbc) especificamente esta version "postgresql-42.2.14".

• La UI consta de varias vistas:
  
	  ► Vista de inicio.
  
	  ► Vista ingreso y calculo IPv4.
  
	  ► Pequeña vista para el calculo de una red requerida dado un numero de hosts solicitados.
  
	  ► Vista ingreso y calculo IPv6.
  
	  ► Vista historial (En esta vista se encuentran 4 tablas, seleccionables con radio buttons).
    
• Las mascaras son escritas por el usuario en Prefix Lenght tanto para IPv4 como para IPv6.

• El usuario (mediante check boxes) puede seleccionar que calculo ver de una IPv4, cabe recalcar que el usuario pulse o no algun(os) checkbox los calculos SIEMPRE se realizaran si y solo si la IPv4 con su mascara son validos, de ser asi se subira a la BBDD en caso de que esta no se encuentre cargada.

• Las IPv6 pueden ser escritas de cualquier forma (Nomenclatura explicita, reducida, simplificada al maximo o mixta IPv6/IPv4).

• Se lleva un registro local de todas las iteraciones realizadas, y un registro en Base de datos,el local almacena los datos si y solo si esta bien escrita una IP sin importar cuantas veces fue calculada en esa sesion; mientras que no sucede asi con las tablas de la BBDD, dado que si la IP "X" con mascara "Y" ya se encuentran en la misma, no se relizara una carga nuevamente de esta.

• Para comodidad del usuario se implementa el boton "Eliminar historial" en la vista de Historial, este boton borrara los datos de TODAS las tablas.
