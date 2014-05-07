package netClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

public class UDP_client implements Runnable{

	private static final int TIME_OUT = 5000;   // 5 secs
    // timeout used when waiting in receive()
	private static final int PACKET_SIZE = 1024;  // max size of a message

	volatile boolean active = true;
	private DatagramSocket socket = null;
	private int SERVER_PORT = 49005;
	private String SERVER_IP = null;
	private InetAddress serverAddr = null;
	
	public boolean serverConnect(String serverIP, int port) throws UnknownHostException, IOException {
		SERVER_IP = serverIP;
		SERVER_PORT = port;
		if(SERVER_IP != null) {
			serverAddr = InetAddress.getByName(SERVER_IP);
			socket = new DatagramSocket(port);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length, 
                    serverAddr, SERVER_PORT);
			socket.send(packet);
			//socket.setSoTimeout(TIME_OUT);

			return true;
		}
		else return false;
	}
	
	public boolean serverConnect(InetAddress serverIP, int port) throws UnknownHostException, IOException {
		serverAddr = serverIP;
		SERVER_PORT = port;
		if(serverAddr != null) {
			socket = new DatagramSocket(port);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length, 
                    serverAddr, SERVER_PORT);
			socket.send(packet);
			//socket.setSoTimeout(TIME_OUT);

			return true;
		}
		else return false;
	}
	
	private void readServerMessage()
	  /* Read a message sent from the NameServer (when
	     it arrives). There is a time-out associated with receive() */
	  {
	    String msg = null;
	    try {
	      byte[] data = new byte[PACKET_SIZE];
	      DatagramPacket receivePacket = new DatagramPacket(data, data.length);
	      System.out.println("Waiting on packet.");
	      socket.receive( receivePacket );  // wait for a packet

	      for(int i = 0; i < data.length; i++){
	    	  System.out.println(i + " " + data[i]);
	      }
	    }
	    catch(IOException ioe)
	    {  System.out.println(ioe);  }

	  }  // end of readServerMessage()

	
	public void run() {
		while(active) {
			/*
			try {
				Thread.sleep(10);
			}
			catch(InterruptedException ex){
				active = false;
			}
			*/
			readServerMessage();
		}

			//in.close();
			//out.close();
			socket.close();
	}
}
