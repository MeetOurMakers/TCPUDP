package com.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

import com.tools.SupTools;

/**
 * 
 * TCPServer
 * 
 * @author Fengyuan Zhang
 * 
 */
public class UDPServer {
	/* the port used to accept UDP datagrampacket */
	private final static int UDP_PORT = 8887;
	/* the hashmap used to be operated */
	private static HashMap<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {
		DatagramSocket mDs = null;// UDP socket
		DatagramPacket mDpreceive = null;// received packet
		DatagramPacket mDpsend = null;// packet to send
		try {
			mDs = new DatagramSocket(UDP_PORT);// listen to udp port
			byte[] b = new byte[1024];
			mDpreceive = new DatagramPacket(b, b.length);
			String receivedStr = null;
			String reply = null;
			boolean flag = true;
			System.out.println("UDPServer start!");
			while (flag) {// receive the message from client
				mDs.receive(mDpreceive);
				SupTools.showMeswithTime("server received data from client:");
				receivedStr = new String(mDpreceive.getData(), 0, mDpreceive.getLength());
				SupTools.showMeswithTime(receivedStr);
				reply = SupTools.executeOrder(receivedStr, map);// execute the order and
																// generate reply
				mDpsend = new DatagramPacket(reply.getBytes(), reply.length(), mDpreceive.getAddress(),
						mDpreceive.getPort());
				mDs.send(mDpsend);// send the reply
				mDpreceive.setLength(1024);// reset the length since it has been
											// adjusted to the length of last
											// message
			}
			mDs.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}