package s14_socket_client;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * SocketClient --- Programa que actua como terminal o cliente socket para
 * peticiones del Banco Politécnico.
 * 
 * @author Martinez Alzate John Alejandro
 * @author Norena Arenas Yeferson Eduardo
 * @author Ortiz Gutierrez Raul Enrique
 * @author Salazar Gallego Kevin De Jesus
 * @author Fajardo Barragan Leidy Marcela
 * 
 * 
 */


public class SocketClient {

	/** Instancia de la clase Scanner para captura por consola */
	private Scanner sn = null;
	/** Ip del servidor de sockets para enviar peticiones */
	private static String SERVERNAME = "localhost";

	/** Puerto del servidor de sockets para enviar peticiones */
	private static int PORT = 3000;

	/** Instancia de la clase socket */
	private Socket client = null;

	/** Constante para definir el texto para retiros de dinero */
	private static String RETIRO = "Retiro de Dinero";

	/** Constante para definir el texto para consulta de saldos */
	private static String CONSULTA = "Consultar saldo";

	/** Constante para definir el texto para salir de la aplicación */
	private static String SALIR = "Salir";

	/** Propiedad para almacenar los mensajes de información */
	private String msg;
	
	
	/** Instancia SocketClient */
	 
	private static SocketClient socketClient = null;
	

	/**
	 * Constructor de la clase SocketClient
	 */
	public SocketClient() {
		sn = new Scanner(System.in);
	}// Cierra el constructor de la clase
	
	/**
	 * Método main donde arranca la ejecución del programa
	 * @param args -  Argumentos que puede recibir el programa
	 * */

	public static void main(String[] args) {	
		
// Instancia de la clase SocketClient
		socketClient = new SocketClient();

		try {
			// Obtiene la pantalla Inicial
			socketClient.getPantallaInicial();

			// Realiza la conexion al servidor de sockets (transaccional) del banco Politecnico
			socketClient.conectarServidorSocket();
			
			System.out.println("Escribe una de las opciones");
			
			// Limpia la consola para registrar la otras opciones
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

			//  Obtiene la conexión con el servidor de sockets
			socketClient.client.getRemoteSocketAddress();

			boolean salir = false;
			int opcion = 0; // Guardarmos la opción del usuario
			String seleccion = "";

			while (!salir) {

				socketClient.getMenu(); // Obtiene el texto del menú
				socketClient.setMsg("\t\tEscoja una opción y presione \"ENTER\" para continuar ...");
				System.out.println(socketClient.getMsg());

				try {
					 
					opcion = socketClient.sn.nextInt();
					
					// De acuerdo a la opción seleccionada almacena para ser enviada al servidor

					switch (opcion) {
					case 1:
						seleccion = "1. " + RETIRO;
						salir = true;
						break;
					case 2:
						seleccion = "2. " + CONSULTA;
						salir = true;
						break;
					case 3:
						salir = true;
						break;
					default:
						System.out.println("Solo números entre 1 y 3");
					}
				} catch (InputMismatchException e) {
					// Imprime error si no escoge un número
					System.out.println("Debes insertar un número");
					socketClient.sn.next();
					salir = true;
				}

			}

			// Imprime la respuesta en el  servidor
			OutputStream outToServer = socketClient.client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF("Se escoge la opción;  " + seleccion + " - " + socketClient.client.getLocalSocketAddress());
			InputStream inFromServer = socketClient.client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}// Cierre del método main

	
	/**
	 * Obtiene el texto de la pantalla inicial
	 * */
	
	public void getPantallaInicial() {

		String text = "\n\n\n\n\n";
		text += "\t\t*******************************************************\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******     BIENVENIDOS AL BANCO POLITECNICO     *******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t*******************************************************\n\n\n";

		System.out.println(text);
		setMsg("\t\tPresione \"ENTER\" para continuar ...");
		//Ejecuta el método enter
		presioneTeclaEnter();
	}// Cierre método getPantallaInicial

	/**
	 * Método que permite aceptar la petición	  
	 * */
	public void presioneTeclaEnter() {
		System.out.println(getMsg());
		sn.nextLine();
	}

	/**
	 * Método para conectar al servidor de socket del banco Politecnico
	 * 
	 * */
	public void conectarServidorSocket() {
		System.out.println("Conectando al servidor " + SERVERNAME + " en el puerto " + PORT);
		try {
			client = new Socket(SERVERNAME, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// Cierra el método conectarServidorSocket

	
	/**
	 * Obtiene el texto del menu
	 * */
	
	public void getMenu() {
		String text = "";
		text = "\n\n\n\n\n";
		text += "\t\t*******************************************************\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******           Menu Principal                 *******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t*******************************************************\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******   1. " + RETIRO + "                    *******\n";
		text += "\t\t******   2. " + CONSULTA + "                     *******\n";
		text += "\t\t******   3. " + SALIR + "                               *******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t******\t\t\t\t\t\t*******\n";
		text += "\t\t*******************************************************\n\n";
		System.out.println(text);

	}// Cierre de la clase getMenu()

	/** Método para obtener el mensaje de información 
	 * @return msg - Retorna un mensaje
	 * */

	public String getMsg() {
		return msg;
	}

	/** Método para establecer el mensaje de información 
	 * @param msg - Recibe un mensaje 
	 * */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}// Cierre de la clase SocketClient
