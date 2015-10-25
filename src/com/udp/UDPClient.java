package com.udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tools.SupTools;

/**
 * 
 * TCPServer
 * 
 * @author Fengyuan Zhang
 * 
 */
public class UDPClient {
	/* the port used to accept reply */
	private final static int HOME_PORT = 1111;
	/* the port used to send the order to server */
	private final static int TARGET_PORT = 8887;
	/* the number of tries to resend a message */
	private final static int MAX_NUM = 3;
	/* the time to judge there is a timeout exception */
	private final static int TIME_OUT = 2000;

	public static void main(String[] args) {
		DatagramSocket mDs = null;// client socket
		DatagramPacket mDpsend = null;// packet to send order
		DatagramPacket mDpreceive = null;// packet to get reply
		try {
			byte[] bsend = new byte[1024];
			byte[] breceive = new byte[1024];
			mDs = new DatagramSocket(HOME_PORT);
			mDs.setSoTimeout(TIME_OUT);// set up the timeout mechanism
			mDpreceive = new DatagramPacket(breceive, breceive.length);
			InetAddress loc = InetAddress.getLocalHost();// get the address of server
			while (true) {
				System.out.println("Please input your command:");
				int length = System.in.read(bsend);//wait for your order
				mDpsend = new DatagramPacket(bsend, length, loc, TARGET_PORT);
				int tries = 0;
				boolean isResponsed = false;
				while (!isResponsed && tries < MAX_NUM) {// if do not get a reply, resend the message
					mDs.send(mDpsend);
					try {
						mDs.receive(mDpreceive);
						System.out.println("loc = "+loc);
						System.out.println("getaddress = "+mDpreceive.getAddress().toString());
						if (!mDpreceive.getAddress().equals(loc)) {// if reply is not from the
																	// server,throw out exception
							throw new IOException("Received packet from an umknown source");
						}
						isResponsed = true;
					} catch (InterruptedIOException e) {// if fail then resend
														// for MAX_NUM times
						tries += 1;
						if(tries>=3){
							SupTools.showMeswithTime("No response -- give up.");
						} else {
							SupTools.showMeswithTime("Time out," + (MAX_NUM - tries) + " more tries...");
						}	
					}
				}
				if (isResponsed) {// show the reply
					SupTools.showMeswithTime("client received data from server:" + " from "
							+ mDpreceive.getAddress().getHostAddress() + ":" + mDpreceive.getPort());
					String str_receive = new String(mDpreceive.getData(), 0, mDpreceive.getLength());
					SupTools.showMeswithTime(str_receive);
					mDpreceive.setLength(1024);// reset the length since it has
												// been adjusted to the length
												// of last message
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			mDs.close();
		}
	}

}
