package com.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.tools.SupTools;

/**
 * 
 * TCPClient
 * 
 */
public class TCPClient {
	/* the name of server */
	private final static String SERVER_NAME = "localhost";
	/* the port of server */
	private final static int TARGET_PORT = 8888;
	/* the time to judge there is a timeout exception */
	private final static int TIME_OUT = 2000;

	public static void main(String[] args) {
		Socket mSocket = null;//socket used to connect the server
		DataInputStream in = null;//the stream to receive reply
		DataOutputStream out = null;//the stream to send message
		BufferedReader mBrsend = null;//used to read the user's input
		try {
			mSocket = new Socket(SERVER_NAME, TARGET_PORT);//establish connection to sever
			mSocket.setSoTimeout(TIME_OUT);//set the timeout mechanism
			in = new DataInputStream(mSocket.getInputStream());
			out = new DataOutputStream(mSocket.getOutputStream());
			mBrsend = new BufferedReader(new InputStreamReader(System.in));//wait for your order
			System.out.println("Connection established:");
			SupTools.showInstruction();
			System.out.println("bye : terminate the connection with server");
			System.out.println("you are not allowed to send an empty message or the connection will be reset");
			boolean flag = true;
			while (flag) {
				System.out.println("Please input your command:");
				String str = mBrsend.readLine();
				out.writeUTF(str);//encode and send message
				if ("bye".equals(str) || str==null || str.equals("")) {//if you send "bye", you will end the connection
					flag = false;
				} else {
					String reply = in.readUTF();//receive and decode the reply
					SupTools.showMeswithTime("client received data from server:" + " from "
							+ mSocket.getInetAddress() + ":" + mSocket.getPort());
					SupTools.showMeswithTime(reply);
				}
			}
		} catch (SocketTimeoutException e) {//if timeout then print this line
			System.out.println("Time out, no response.");
		} catch (EOFException e){
			System.out.print("Connection shut down. Maybe the server crashed.");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
