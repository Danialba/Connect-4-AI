package project;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class FileHandlerTest {
	
	FileHandler fileHandler= new FileHandler();
	
	private static final String TESTFILENAME="TestFile";
	private static final String TESTILLEGALFILE ="TestIllegalScores";
	private List<Long> testFileContains;
	
	
	
	@BeforeEach
	//Sets up the test file using reliable methods with BufferedWriter and FileWriter.
	public void setup() {
		testFileContains= Arrays.asList(1078842260L, 1099963023L, 1104912835L, 1119244390L, 1126236200L);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileHandler.getFilePath(TESTFILENAME)));
			for (Long time: testFileContains) {
				String string="";
				string+=time;
				writer.write(string);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			fail("Cannot load test-file");
		}
	}
	
	
	@Test
	public void testReadFromFile() {
		try {
			assertEquals(fileHandler.readFromFile(TESTFILENAME),testFileContains);
		} catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		}	
	}
	
	@Test
	public void testWriteLowScoreToFile() {
		//Test that nothing happens if the time written to file is longer than the longest existing time.
		try {
			fileHandler.writeToFile(TESTFILENAME, 1326236200L);
		} catch (IOException e) {
			fail("Cannot find test-file");
		}
		try {
			assertEquals(testFileContains,fileHandler.readFromFile(TESTFILENAME));
		} catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		}	
	}
		
	@Test
	public void testWriteMediumScoreToFile() {
		//Test a score which is higher than the lowest existing score and lower than the highest existing score.
		testFileContains= Arrays.asList(1078842260L,1086236200L, 1099963023L, 1104912835L, 1119244390L);
		try {
			fileHandler.writeToFile(TESTFILENAME, 1086236200L);
		} catch (IOException e) {
			fail("Cannot find test-file");
		}
		try {
			assertEquals(fileHandler.readFromFile(TESTFILENAME),testFileContains);
		} catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		}
		
	}
	@Test
	public void testWriteNewHighsScoreToFile() {
		//Test a new highscore
		testFileContains= Arrays.asList(1066236200L,1078842260L, 1099963023L, 1104912835L, 1119244390L);
		try {
			fileHandler.writeToFile(TESTFILENAME, 1066236200L);
		} catch (IOException e) {
			fail("Cannot find test-file");
		}
		try {
			assertEquals(testFileContains, fileHandler.readFromFile(TESTFILENAME));
		} catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		}
	}
	
	@Test
	public void testWriteEqualScoreToFile() {
		//Test write a score that is already there
		testFileContains= Arrays.asList(1078842260L, 1078842260L, 1099963023L, 1104912835L, 1119244390L);
		try {
			fileHandler.writeToFile(TESTFILENAME, 1078842260L);
		} catch (IOException e) {
			fail("Cannot find test-file");
		}
		try {
			assertEquals(testFileContains, fileHandler.readFromFile(TESTFILENAME));
		} catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		}
	}
	
	@Test
	public void testReadFromNonExistentFile() {
		assertThrows(
				FileNotFoundException.class,
				() ->fileHandler.readFromFile("hei")
				);
	}
	
	@Test
	public void testWriteFromNonExistentFile() {
		assertThrows(
				IOException.class,
				() ->fileHandler.writeToFile("hei", 10000000)
				);
	}
	@Test
	public void testReadFromIllegalFile() {
		assertThrows(
				NumberFormatException.class,
				() ->fileHandler.readFromFile(TESTILLEGALFILE)
				);
	}
	
	@Test
	public void testWriteToIllegalFile() {
		assertThrows(
				NumberFormatException.class,
				() ->fileHandler.writeToFile(TESTILLEGALFILE, 909009090)
				);
	}
	
	@Test
	public void testNegativeTime() {
		assertThrows(
				IllegalArgumentException.class,
				() ->fileHandler.writeToFile(TESTFILENAME, -909009090)
				);
		try {
			assertEquals(fileHandler.readFromFile(TESTFILENAME),testFileContains);
		}
		catch (FileNotFoundException e) {
			fail("Could not load test-file.");
		
		}
	}
	
	@AfterAll
	static void teardown() {
		File TestFile = new File(FileHandler.getFilePath(TESTFILENAME));
		TestFile.delete();
	}
	
}
