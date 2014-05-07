package netClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Connecting");
		UDP_client client = new UDP_client();
		InetAddress address;
		try{
			address = InetAddress.getLocalHost();
			if (client.serverConnect(address, 49005)){
				System.out.println("Connected to " + address.toString());
			}
			else{
				System.out.println("Failed to connect to " + address.toString());
			}
			Thread clientThread = new Thread(client);
			clientThread.start();
		}
		catch(UnknownHostException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		

	}

}
