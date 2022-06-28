package pl.interia.rym.maciej.IO;

import java.util.HashMap;

public class DataBlock {

	private HashMap<String, String> hashes = new HashMap<String, String>();
	
	private boolean wasFullyRead = false;
	
	public DataBlock() {
	}

	public String getValue(String key) {
		return hashes.get(key);
	}
	
	public void addHash(String key, String values) {
		hashes.put(key, values);
		if(!wasFullyRead) {
			wasFullyRead = true;
		}
	}
	
	public HashMap<String, String> getHashes() {
		return hashes;
	}

	public void setHashes(HashMap<String, String> hashes) {
		this.hashes = hashes;
	}

	public boolean wasFullyRead() {
		return wasFullyRead;
	}

	public void setWasFullyRead(boolean wasFullyRead) {
		this.wasFullyRead = wasFullyRead;
	}
	
}
