package com.console.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.console.core.connections.ConnectionManager;
import com.google.gson.Gson;

/**
 * Servlet implementation class MnuOptions
 */
@WebServlet("/server/config")
public class MnuOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MnuOptions() {
        super();
        // TODO Auto-generated constructor stub
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
		response.setContentType("application/json");
		BufferedReader  fs=new BufferedReader ( new FileReader(ConnectionManager.class.getClassLoader().getResource("./conns.json").getFile()));
		StringBuffer sbf=new StringBuffer();
		String ln;
		while((ln=fs.readLine())!=null){
			sbf.append(ln);
		}
		response.getWriter().write(new Gson().toJson(sbf.toString()));
//				System.out.println(new Gson().toJson(sbf.toString()));
	}
		
	
	}


