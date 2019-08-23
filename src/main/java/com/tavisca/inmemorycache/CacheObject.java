package com.tavisca.inmemorycache;

public class CacheObject {

	private long expiryTime;  //in milliseconds
	
	private long lastModifiedTime; //in milliseconds
	
	private Object data;
	
	
	public CacheObject() {
	}

	public CacheObject(long expiryTime,long lastModifiedTime,Object data) {
		this.expiryTime = expiryTime;
		this.lastModifiedTime = lastModifiedTime;
		this.data = data;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isObjectExpired() {
		if(System.currentTimeMillis() > this.expiryTime)
			return true;
		else
			return false;
	}
}
