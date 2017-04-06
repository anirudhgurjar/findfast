package com.console.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Logger;

import com.console.core.connections.ConnectionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.console.core.connections.Connection;
import com.google.gson.Gson;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CmdExecutor {
	
	static Logger LOG=Logger.getLogger(CmdExecutor.class.getName());
	
	 JSch jsch = new JSch();  

	 public  JSONArray execCmd(String command,Connection con,String path,String params[], boolean isRead)throws Exception  {
		//	LOG.info("Handling execution for host:" + con.getDispName() );
	//		LOG.info("path" + con.getLogPath().get(path));
			 JSONArray jar=new JSONArray();
			  String line;
			 
			Session session = null;
			if(null!=con.getKeyStore()){
		//		LOG.info("Using Keystore" + con.getKeyStore());
				jsch.addIdentity(con.getKeyStore());
				session = jsch.getSession(con.getUserName(), con.getHostName(), con.getPort());	 
			}else if(con.getUserName()!=null){
			//	LOG.info("Using username/pass:" + con.getUserName());
				session = jsch.getSession(con.getUserName(), con.getHostName(), con.getPort());
				session.setPassword(con.getPassword());
			
			}else {
				throw new Exception ("No login config..");
			}
			session.setConfig("StrictHostKeyChecking", "no");
			StringBuffer streamer=new StringBuffer();
			
			 
		try {  
			session.connect();			 
			LOG.info("Connected to.." + con.getHostName());
			
			ChannelExec exec = null;
            exec = (ChannelExec)session.openChannel("exec");
            exec.setCommand(buildCommand(command, params, path));

            exec.setInputStream(null);
            exec.connect();
        	//LOG.info("Executed command" );
            BufferedReader bf=new BufferedReader(new InputStreamReader(exec.getInputStream()));
         
	          String flds[];
	       
	           JSONObject json = new JSONObject();
	          int i=0;
	            while ((line=bf.readLine())!=null){
//	         
	            //	LOG.info("reading lines" + line );
	            	if(isRead){
	            		flds=line.split(":");
	                	if(flds.length>=3){
	              //  		LOG.info("adding lines" + flds[0] );
				            	json = new JSONObject();
				            	json.put("seq", i);
				            	json.put("fileName", flds[0]);
				            	json.put("lineNo", flds[1]);
				            	int secondIndex=line.indexOf(":", line.indexOf(":")+1);
				            	json.put("trace", line.substring(secondIndex+1, line.length()));
				          //  	LOG.info("adding trace" + line.substring(secondIndex+1, line.length()) );
				            	jar.put(i, json);
				            	//jar.put(json);
				            	i++;
		            	}
	            	}else{
	            		streamer.append("\n").append(line);
	            	}
	            }
	            exec.disconnect();
	            session.disconnect();
	            if(!isRead){
	            	json = new JSONObject();
	            	json.put("output",streamer.toString());
	            	jar.put(json);		            	
	            }
	           
		  }catch(Exception e){
			//  System.out.println("Failed to parse" + line);
			  e.printStackTrace();
			  JSONObject json = new JSONObject();
			  json.put("Error", e.getMessage());
			  jar=new JSONArray();			  
			  jar.put(json);
			  streamer.append("Error");
			  
		  }
		  
		 // LOG.info("returning object" + jar.length());
		  return (jar);
            
	 }

	 
	 

	public  JSONArray execCmd(String command,String hostname,String params[],String path, boolean isRead)  {
		LOG.info("Logging started");
		 JSONArray jar=new JSONArray();
	
			Session session = null;
				String username="rgricm22";
				String password="Unix111!";
				String host="rsohp044";
			  String line="";
			 StringBuffer streamer=new StringBuffer();
			  try {
				 jsch.addIdentity("\\\\rsohp043\\amdocs\\PROD\\ssh_key.ppk");
            System.out.println("Establishing connection.......");
		        session =getSShSession("");//jsch.getSession(username, host, 22);
		           session.setConfig("StrictHostKeyChecking", "no");
		          //session.setPassword(password);
		         //   LOG.info("Connecting.." + username +"/");
		            session.connect();
		            LOG.info("Connected to.." + hostname +"/");
		            ChannelExec exec = null;
		            exec = (ChannelExec)session.openChannel("exec");
		            exec.setCommand(buildCommand(command, params, path));
		            //  exec.setCommand("grep -n \"Exception\" /app/icm/rgricm22/J2EEServer/config/CRM/logs/*.log ");
		         //   exec.setCommand(" sed -n  '1986,2100 p'  /app/icm/rgricm22/J2EEServer/config/CRM/logs/weblogic_console.20170312080009.log "); 
		           // exec.setCommand(command);	
		            exec.setInputStream(null);
		            exec.connect();
		            BufferedReader bf=new BufferedReader(new InputStreamReader(exec.getInputStream()));
		         
		          // streamer.append("<pre>");
		           String flds[];
		           JSONObject json = new JSONObject();
		          int i=0;
		            while ((line=bf.readLine())!=null){
         
		            	if(isRead){
		            		flds=line.split(":");
		                	if(flds.length>=3){
			            		//System.out.println("Count" + ++i +"-" + line);
					            	json = new JSONObject();
					            	json.put("seq", i);
					            	json.put("fileName", flds[0]);
					            	json.put("lineNo", flds[1]);
					            	int secondIndex=line.indexOf(":", line.indexOf(":")+1);
					            	json.put("trace", line.substring(secondIndex+1, line.length()));
					            	jar.put(i, json);
					            	//jar.put(json);
					            	i++;
			            	}
		            	}else{
		            		streamer.append("\n").append(line);
		            	}
          //  System.out.println(flds[0] + "===" + flds[1]);
		            	//streamer.append(line);
		    
		            }
		           // System.out.println("Completed");
		            exec.disconnect();
		            session.disconnect();
		           // System.out.println("--- Session Closed");
		           // streamer.append("</pre>");
		            
		            if(!isRead){
		            	json = new JSONObject();
		            	json.put("output",streamer.toString());
		            	jar.put(json);		            	
		            }
		           
			  }catch(Exception e){
				  System.out.println("Failed to parse" + line);
				  e.printStackTrace();
				  streamer.append("Error");
				  
			  }
			  return (jar);
	}
	
	private String buildCommand(String cmd,String params[],String path){
		String command=null;
		if(cmd.equalsIgnoreCase("find")){
			command="grep -n \""+ params[0]+"\" " + path;
			System.out.println("Command is=" + command);
		}else if(cmd.equalsIgnoreCase("view")){			
			command="sed -n  '"+params[0] + ","+ params[1]+  " p' " + path  ;
		}else  {
			return cmd;
		}
		return command;
	}
	
	
	private Session getSShSession(String hostname)throws Exception{
		
		Session session = null;
		String username="jduraisa";
		//String password="Unix111!";
		String host="ehpap121";	
		jsch.addIdentity("\\\\rsohp043\\amdocs\\PROD\\ssh_key.ppk");
		session = jsch.getSession(username, host, 22);
		//session.setPassword(password);
		
		session.setConfig("StrictHostKeyChecking", "no");
		//session.connect();
     session=jsch.getSession(username, host, 22);
       return session;
		
	}
	
	
	
//		public static void main(String[] args)throws Exception {
//				CmdExecutor t=new CmdExecutor();
//				String path="//app//prdcrm1//J2EEServer//config//CRM//logs//*.log";
//				//System.out.println(new CmdExecutor().execCmd("find","rsohp044",new String[]{"Exception"},path,true));
//				
//				HttpCommand cmd=new HttpCommand();
//				HashMap connnMap=null;
//				connnMap=com.console.core.connections.ConnectionManager.readConnections();
//				cmd.setHost("CRM_DEV_RGRICM22");
////				cmd.setPath("server_log");
////				cmd.setQ("timeout");
//				cmd.setGenCmd("ps -ef | grep \"java\"");
//				Connection con=(Connection)connnMap.get(cmd.getHost());
//				//System.out.println(t.execCmd("find", con,con.getLogPath().get(cmd.getPath()).toString(), new String[]{cmd.getQ().trim()}, true));
//				//t.("find",con,con.getLogPath().get(cmd.getPath()).toString(),new String[]{cmd.getQ().trim()},true).toString())
//				
//				//System.out.println(new CmdExecutor().execCmd("view","rsohp044",new String[]{"67","77"},"/app/icm/rgricm22/J2EEServer/config/CRM/logs/weblogic_console.20170224080009.log",false));
//				
//				System.out.println(t.execCmd(cmd.getGenCmd(),con,null,new String[]{},false).toString());
//						//JsonReader reader=new JsonReader(new FileReader("conns.json"));
////				BufferedReader  fs=new BufferedReader ( new FileReader("conns.json"));
////				StringBuffer sbf=new StringBuffer();
////				String ln;
////				while((ln=fs.readLine())!=null){
////					sbf.append(ln);
////				}
////		System.out.println(new Gson().toJson(sbf.toString()));
//		}
//	

	

}
