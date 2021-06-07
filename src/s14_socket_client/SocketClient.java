package s14_socket_client;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SocketClient {

	Scanner sn = null;
	static String SERVERNAME = "localhost";
	static int PORT = 3000;
	Socket client = null;
	static String RETIRO = "Retiro de Dinero";
	static String CONSULTA = "Consultar saldo";
	static String SALIR = "Salir";
	String msg;
	
	

	public SocketClient() {

		sn = new Scanner(System.in);

	}

	public static void main(String[] args) {

		SocketClient socketClient = new SocketClient();
		
		try {
			// Obtiene la pantalla Inicial
			socketClient.getPantallaInicial();	
			
			// Realiza la conexion al servidor transaccional
			socketClient.conectarServidorSocket();

			System.out.println("Escribe una de las opciones");

			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

			socketClient.client.getRemoteSocketAddress();

			//System.out.println("Just connected to " + socketClient.client.getRemoteSocketAddress());

			boolean salir = false;
			int opcion = 0; // Guardarmos la opción del usuario
			String seleccion = "";
			

			while (!salir) {

				socketClient.getMenu();
				socketClient.setMsg("\t\tEscoja una opción y presione \"ENTER\" para continuar ..."); 
				System.out.println(socketClient.getMsg());
				
				try {
					
					opcion = socketClient.sn.nextInt();

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
					System.out.println("Debes insertar un número");
					socketClient.sn.next();
					salir = true;
				}

			}
			
			OutputStream outToServer = socketClient.client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF("Se escoge la opción;  " + seleccion + " - " + socketClient.client.getLocalSocketAddress());
			InputStream inFromServer = socketClient.client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);

			//System.out.println("Server says " + in.readUTF());
		//	socketClient.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

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
		
		presioneTeclaEnter();
		}

	public void presioneTeclaEnter(){
		   System.out.println(getMsg());		 
		   sn.nextLine();
		}
	
	public void conectarServidorSocket() 
	{
		System.out.println("Connecting to " + SERVERNAME + " on port " + PORT);
		try {
			client = new Socket(SERVERNAME, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void getMenu() 
	{
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
				System.out.println(text );
				 
		
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}