package com.console.core.connections;

import java.io.Serializable;
import java.util.HashMap;

public class Connection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKeyStore() {
		return keyStore;
	}
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}
	public boolean isUseKeyStore() {
		return isUseKeyStore;
	}
	public void setUseKeyStore(boolean isUseKeyStore) {
		this.isUseKeyStore = isUseKeyStore;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	private String hostName;
	private String userName;
	private String password;
	private String keyStore;
	public HashMap getLogPath() {
		return logPath;
	}
	public void setLogPath(HashMap logPath) {
		this.logPath = logPath;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	private HashMap logPath;
	private String dispName;
	private boolean isUseKeyStore;
	private int port;
	
	
}
