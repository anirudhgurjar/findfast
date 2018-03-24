package com.console.core;

import com.google.gson.Gson;

public class HttpCommand {


	

	public HttpCommand() {
		// TODO Auto-generated constructor stub
	}
//
//	public HttpCommand(String rawStr) {
//		parsedCmdObj = new HttpCommand();
//		System.out.println(parsedCmdObj.getQ());
//		parsedCmdObj = parser.fromJson(rawStr, this.getClass());
//		System.out.println("rat Sr" + getQ());
//		System.out.println("Logger" + parsedCmdObj.getQ());
//	}
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

		public String toString() {
			return (new StringBuffer().append(getQ()).toString());
			
		};
	
	public HttpCommand parseCmd(String st){
		return new Gson().fromJson(st, HttpCommand.class);
	}	

	public String getFromLine() {
		return fromLine;
	}

	public void setFromLine(String fromLine) {
		this.fromLine = fromLine;
	}

	public String getToLine() {
		return toLine;
	}

	public void setToLine(String toLine) {
		this.toLine = toLine;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	String fromLine;
	String toLine;
	String fileName;	
	String q;
	String host;
	String path;
	String genCmd;
	public String getGenCmd() {
		return genCmd;
	}

	public void setGenCmd(String genCmd) {
		this.genCmd = genCmd;
	}
	
	
	
}
