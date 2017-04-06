package com.console.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.console.core.connections.Connection;
import com.console.core.connections.ConnectionManager;

/**
 * Servlet implementation class LogViewer
 */
@WebServlet("/service/vwlog")
public class LogViewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       CmdExecutor cmdExecutor=new CmdExecutor();
       HashMap connnMap=null;
   	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogViewer() {
        super();
        // TODO Auto-generated constructor stub
        
        connnMap=ConnectionManager.readConnections();
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
//		String fromLn=request.getParameter("fromLine");
//		String toLn=request.getParameter("toLine");
//		String host=request.getParameter("host");
//		String fileName=request.getParameter("fileName");
//		response.setContentType("application/json");
//		response.getWriter().write(cmdExecutor.execCmd("view",host,new String[]{fromLn,toLn},fileName,false).toString());	
	
		BufferedReader br=request.getReader();
		String st=null;
		System.out.println("reading request");	
		JSONObject reqJson=null;;
		while((st=br.readLine())!=null){
			System.out.println(st);	
			break;
		}		
		
		
		try{
			HttpCommand cmd=new HttpCommand();
			cmd=cmd.parseCmd(st);
			String host=null;
			String path=null;
			Connection con=(Connection)connnMap.get(cmd.getHost());
			
			if(con==null){
				response.getWriter().write("{ 'status' : 'false', 'err' : 'Host Not found'}");				
			}		
			
			String fromLn=cmd.getFromLine();
			String toLn=cmd.getToLine();			
			String fileName=cmd.getFileName();
			
			
			
			response.setContentType("application/json");
		//	response.getWriter().write(cmdExecutor.execCmd("find",cmd.getHost().trim(),new String[]{cmd.getQ().trim()},cmd.getPath().trim(),true).toString());
		//	response.getWriter().write(cmdExecutor.execCmd("find",con,con.getLogPath().get(cmd.getPath()).toString(),new String[]{cmd.getQ().trim()},true).toString());
		//	response.getWriter().write(cmdExecutor.execCmd("view",host,new String[]{fromLn,toLn},fileName,false).toString());
			if(fileName!=null){			
				response.getWriter().write(cmdExecutor.execCmd("view",con,fileName,new String[]{fromLn,toLn},false).toString());
			}else if(cmd.getGenCmd()!=null) {
				response.getWriter().write(cmdExecutor.execCmd(cmd.getGenCmd(),con,null,new String[]{},false).toString());
			}
		}catch(Exception ex){
			System.out.println("String is " + st);
		//	System.out.println("JSON is" + reqJson.toString() );
			ex.printStackTrace();
			response.getWriter().write("{'status' : 'BAD request'}");
			return;
		}	
	
	
	}

}
