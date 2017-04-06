package com.console.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.console.core.connections.*;

/**
 * Servlet implementation class CmdProcessor
 */
@WebServlet("/service/find")
public class CmdProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CmdExecutor cmdExecutor=new CmdExecutor();
	HashMap connnMap=null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CmdProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		connnMap=ConnectionManager.readConnections();
	
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br=request.getReader();
		String st=null;
		HttpCommand cmd=null;
		System.out.println("reading request");
		Connection con=null;
		JSONObject reqJson=null;;
		while((st=br.readLine())!=null){
			System.out.println(st);	
			break;
		}		
		try{
			cmd=new HttpCommand();
			cmd=cmd.parseCmd(st);
			String host=null;
			String path=null;
			con=(Connection)connnMap.get(cmd.getHost());
		//	System.out.println("Host" + cmd.getHost());
		//	System.out.println("keyset" + connnMap.keySet());
			
			if(con==null){
				response.getWriter().write("{ \"status\" : false, \"err\" : \"Error Connecting host\"}");	
				return;
			}		
			response.setContentType("application/json");
		//	response.getWriter().write(cmdExecutor.execCmd("find",cmd.getHost().trim(),new String[]{cmd.getQ().trim()},cmd.getPath().trim(),true).toString());
			response.getWriter().write(cmdExecutor.execCmd("find",con,con.getLogPath().get(cmd.getPath()).toString(),new String[]{cmd.getQ().trim()},true).toString());
			
		}catch(Exception ex){
//			System.out.println("String is " + st);
//			System.out.println("cmd is " + cmd.getPath() + " " +cmd.getQ() + "" + cmd.getHost());
//			System.out.println("con is " + con.getLogPath() + " " + con.getUserName());
//			System.out.println("cmd is " + cmd.getPath() + " " +cmd.getQ());
		//	System.out.println("JSON is" + reqJson.toString() );
			ex.printStackTrace();
			response.getWriter().write("[{ \"status\" : false, \"err\" : \"BAD request\"}]");
			return;
		}		
	}

}
