package pl.interia.rym.maciej.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHandler {

	private static File logFile;
	private static PrintWriter fileWriter;
	
	//TODO  Dodac Enuma, zeby moc uzywac write[]Log w zaleznosci od source. Network = networkowe sprawy, general ogolne itp
	//Ale jak to zrobic? klasa LogHandler przeciez nie ma parametrow do pliku. Ba, nie ma jak ich przeciez przekazac XD
	
	public static void setLogFile(String pathToFile) {
		logFile = new File(pathToFile);
		if(!logFile.exists()) {
			try {
				boolean wasCreatedWhenInvoked = logFile.createNewFile();
				fileWriter = new PrintWriter(new FileWriter(logFile, true));
				if(wasCreatedWhenInvoked) {
					writeFileLog("Log file created.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				fileWriter = new PrintWriter(new FileWriter(logFile, true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String getCurrentDateTime() {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		  LocalDateTime now = LocalDateTime.now();		  
		  return (dtf.format(now) + ": ");
	}
	
	public static void writeGeneralInfo(String message) {
		fileWriter.println(getCurrentDateTime() + "[ServerInfo] "+ message);
		fileWriter.flush();
	}
	
	public static void writeFileLog(String message) {
		fileWriter.println(getCurrentDateTime() + "[FilesInfo] "+ message);
		fileWriter.flush();
	}
	
	public static void writeServerSocketLog(String message) {
		fileWriter.println(getCurrentDateTime() + "[ServerSocketInfo] "+ message);
		fileWriter.flush();
	}
	
	public static void writeConnectionLog(String message) {
		fileWriter.println(getCurrentDateTime() + "[ConnectionInfo] " + message);
		fileWriter.flush();
	}
	
	public static void writeException(Exception exc) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exc.printStackTrace(pw);
		fileWriter.println(getCurrentDateTime() + "[ExceptionInfo] " + sw.toString());
		fileWriter.flush();
	}

	public static File getLogFile() {
		return logFile;
	}

	public static PrintWriter getFileWriter() {
		return fileWriter;
	}
}
