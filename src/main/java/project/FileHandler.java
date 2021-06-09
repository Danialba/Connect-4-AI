package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileHandler implements FileManager{
	
	public final static String FILE_FOLDER = "src/main/resources/";
	
	public static String getFilePath(String filename) {
		return FILE_FOLDER+filename;
	}
	
	public List<Long> readFromFile(String filename) throws FileNotFoundException{
		File file =new File(getFilePath(filename));
		Scanner scnr= new Scanner(file);
		String str= "";
		while(scnr.hasNextLine()) {
			str+= scnr.nextLine()+"\n";
		}
		scnr.close();
		String[] ranking = str.split("\\r?\\n");
		List<Long> highScores = new ArrayList<>();
		try {
			for (String score : ranking) {
				long i =Long.parseLong(score);
				highScores.add(i);
				}
			}
		catch(NumberFormatException e) {
			throw new NumberFormatException("The file contains non-numbers");
		}
		return highScores;
	}
	
	
	
	public void writeToFile(String filename, long time) throws IOException {
		
		if (time<0) {
			throw new IllegalArgumentException("Time cannot be negative");
		}

		List<Long> rankedRanking = readFromFile(filename);
		
		rankedRanking.add(time);
		Collections.sort(rankedRanking);
		rankedRanking.remove(rankedRanking.size()-1);
		BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(filename)));
		for (long score:rankedRanking) {
			String string="";
			string+=score;
			writer.write(string);
			writer.newLine();
		}
		writer.close();
		
	}
	
	


}
	

