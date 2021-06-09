package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileManager {
	
	List<Long> readFromFile(String filename) throws FileNotFoundException;
		
	void writeToFile(String Filename, long time) throws IOException;
		

}
