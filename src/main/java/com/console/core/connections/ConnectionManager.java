package com.console.core.connections;

import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ConnectionManager {
	
	private static HashMap<String,Connection> connPool=null;
	
	
	static{
		connPool=new HashMap<String, Connection>();
	}
	

	public static HashMap readConnections(){
		try{
			JsonReader reader=new JsonReader( new FileReader(ConnectionManager.class.getClassLoader().getResource("./conns.json").getFile()));
			Connection cons[]=null;
			
			cons=new Gson().fromJson(reader, Connection[].class);
			
			for(Connection con: cons){
				connPool.put(con.getDispName(), con);			}
			
			
		}catch(Exception ex){
			System.out.println("ERR is reading file");
			System.out.println("Current path is" +System.getProperty("user.dir"));
			ex.printStackTrace();
			new Exception("Error while reading connections file");
			
		}
		
		return connPool;
	}
	
	
	public static void main(String[] args) {
		//ConnectionManager.readConnections();
	}
	
	
}
