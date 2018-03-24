package com.console.core;

public class Tester {
	public static void main(String[] args)throws Exception {
		
//		CmdExecutor cmdExecutor=new CmdExecutor();
//		out.print(cmdExecutor.execCmd("find", "rsohp044",new String[]{"Exception"}, "/app/icm/rgricm22/J2EEServer/config/CRM/logs/*.log", true).toString());
		
		String s="//app//icm//rgricm22//J2EEServer//config//CRM//logs//weblogic_console.20170224080009.log:74:Nested Exception/Error:";

		String ary[]=s.split(":");
		System.out.println(ary[0]);
		System.out.println(ary[1]);
		int secondIndex=s.indexOf(":", s.indexOf(":")+1);
		System.out.println(s.substring(secondIndex+1, s.length()));
	}

	class Command{
		
		public String getQ() {
			return q;
		}
		public void setq(String q) {
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
		String q;
		String host;
		String path;
		
	}
	
	
}
