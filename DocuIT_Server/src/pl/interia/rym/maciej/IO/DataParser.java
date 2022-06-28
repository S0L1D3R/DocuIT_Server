package pl.interia.rym.maciej.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;

public class DataParser {
	
	protected File file;
	protected BufferedReader inputStream;
	protected PrintWriter outStream;
	
	public DataParser(String pathToFile, boolean fileCreation) {
		if(!pathToFile.endsWith(".txt")) {
			pathToFile = pathToFile + ".txt";
		}
		file = new File(pathToFile);
		try {
			if(fileCreation) {
				boolean wasCreated = file.createNewFile();
				if(wasCreated) {
					LogHandler.writeFileLog("Data parser created file on: " + file.getAbsolutePath());
				}
			}
		} catch (IOException e) {
			LogHandler.writeFileLog("DataParser while creating file on: " + file.getAbsolutePath() + " || " + e.getLocalizedMessage());
			LogHandler.writeException(e);
		}
	}
	//////
	
	/**
	 * Reads file line by line and returns ArrayList of lines. Classes that inherit from this will read the returned list.
	 */
	public ArrayList<DataBlock> readFileAsBlocks() {
		ArrayList<DataBlock> blocks = new ArrayList<DataBlock>();		
		DataBlock block = null;
		
		openInputStream();
		try {
			
			String currentline;
			block = new DataBlock();
			while ((currentline = inputStream.readLine()) != null) {
				if(currentline.startsWith("#")) {
					continue;
				}
				if(currentline.startsWith("-")) {
					blocks.add(block);
					block = new DataBlock();
					continue;
				}
				String[] splittedLine = currentline.split("->");
				if(splittedLine.length > 1) {
					block.addHash(splittedLine[0], splittedLine[1]);
				}
			}
			if(block.wasFullyRead()) {
				blocks.add(block);
			}
			closeInputStream();
			
		} catch (IOException e) {
			LogHandler.writeFileLog("DataParser error while reading file: " + e.getLocalizedMessage());
			LogHandler.writeException(e);
		}
		return blocks;
	}
	
	
	protected void openInputStream() {
		try {
			inputStream = new BufferedReader(new FileReader(file));
			LogHandler.writeFileLog("DataParser opened input stream on: " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			LogHandler.writeFileLog("DataParser failed to open input stream on: " + file.getAbsolutePath());
			LogHandler.writeException(e);
		}
	}
	
	
	protected void openOutputStream() {
		try {
			outStream = new PrintWriter(new FileWriter(file));
			LogHandler.writeFileLog("Opening output stream on: " + file.getAbsolutePath());
		} catch (IOException e) {
			LogHandler.writeFileLog("DataParser failed to open output stream on: " + file.getAbsolutePath());
			LogHandler.writeException(e);
		}
	}
	
	protected void closeInputStream() {
		try {
			inputStream.close();
			LogHandler.writeFileLog("DataParser closed input stream on: " + file.getAbsolutePath());
		} catch (IOException e) {
			LogHandler.writeFileLog("DataParser failed to close input stream on: " + file.getAbsolutePath());
			LogHandler.writeException(e);
		}
	}
	
	protected void closeOutputStream() {
		outStream.close();
	}
}
