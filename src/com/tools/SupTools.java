package com.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**

* To offer support function to deal with the message

* @author Fengyuan Zhang

*/
public class SupTools {
	/**
	 * To execute the order from client on the map in server
	 *@param order
	 * the command from the client
	 *@param map
	 * the hashmap to be opreated on
	 *@return reply
	 * the reply for the client
	 *@author Fengyuan Zhang
	*/
	public static String executeOrder(String order, HashMap<String, String> map) {
		String[] parts = order.split("\\s+");
		String value = null;
		String result = null;
		String reply = null;
		if(parts.length < 1){
			reply = "Please input your command!";
			return reply;
		}
		switch (parts[0]) {
		case "put"://put operation
			if(parts.length < 3){//order less than 3 parts
				reply = "cannot read your command! Maybe you forget the value.";
				return reply;
			}
			if(parts.length > 3){//order more than 3 parts
				reply = "cannot read your command! Maybe you offer more than one value.";
				return reply;
			}
			map.put(parts[1], parts[2]);
			reply = parts[0]+" "+parts[1]+" "+parts[2] + " success!";
			break;
		case "get":
			if(parts.length > 2){//order less than 2 parts
				reply = "cannot read your command! Maybe you offer more than one key.";
				return reply;
			}
			if(parts.length < 2){//order more than 2 parts
				reply = "cannot read your command! Maybe you forget the key.";
				return reply;
			}
			value = map.get(parts[1]);
			if(value == null)
				reply = parts[0]+" "+parts[1] + " fails because no matching key!";
			else
				reply = "the value of "+parts[1]+" is "+value;
			break;
		case "delete":
			if(parts.length > 2){//order less than 2 parts
				reply = "cannot read your command! Maybe you offer more than one key.";
				return reply;
			}
			if(parts.length < 2){//order more than 2 parts
				reply = "cannot read your command! Maybe you forget the key.";
				return reply;
			}
			result = map.remove(parts[1]);
			if(result == null)
				reply = parts[0]+" "+parts[1] + " fails because no matching keys!";
			else
				reply = "delete the key "+parts[1]+ " success!";
			break;
		default:
			reply = "cannot read your command!";
		}		
		return reply;
	}

	/**
	 * To packge the message with the current time
	 *@param str
	 * the message to be operated
	 *@author Fengyuan Zhang
	*/
	public static void showMeswithTime(String str){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		System.out.println(date+" : "+str);
	}
}
