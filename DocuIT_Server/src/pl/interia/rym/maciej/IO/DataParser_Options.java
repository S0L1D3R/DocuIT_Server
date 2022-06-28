package pl.interia.rym.maciej.IO;

public class DataParser_Options extends DataParser {

	private DataBlock block;
	
	public DataParser_Options(String pathToFile) {
		super(pathToFile, true);
	}
	
	public void readFile() {
		if(!file.exists() || file.length() == 0) {
			openOutputStream();
				outStream.println("ServerName->DocuServer");
				outStream.println("Port->5454");
				outStream.println("Location_ServerData->C:\\Program Files\\DocuITServer\\Data");
				outStream.println("Location_LogFile->C:\\Program Files\\DocuITServer\\ServerLogs.txt");
			closeOutputStream();
		}
		setblock(readFileAsBlocks().get(0));
	}
	
	public String getServerName() {
		return block.getValue("ServerName");
	}
	
	public int getPort() {
		return Integer.parseInt(block.getValue("Port"));
	}
	
	public String getLocation_DataFolder() {
		String path = block.getValue("Location_ServerData");
		if(path.toCharArray()[path.length()-1] != '\\') {
			path = path + "\\";
		}
		return path;
	}
	
	public String getLocation_LogFile() {
		return block.getValue("Location_LogFile");
	}

	public DataBlock getblock() {
		return block;
	}

	public void setblock(DataBlock block) {
		this.block = block;
	}
}
