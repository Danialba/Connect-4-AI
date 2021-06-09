package project;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class CellTest {
	
	private Cell cell;


	
	@BeforeEach
	public void setup() {
		cell=new Cell(0,0);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(cell.getX(),0);
		assertEquals(cell.getY(),0);
		assertEquals(cell.getColor(),Colors.AIR);
		assertThrows(
				IllegalArgumentException.class,
				() -> cell = new Cell(-1, 0)
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> cell = new Cell(0, -1)
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> cell = new Cell(-1, -1)
				);
	}
	
	
	
	@Test
	public void testInvalidCoordinates() {
		assertThrows(
				IllegalArgumentException.class,
				() -> cell.setX(-1)
				);
		assertThrows(
				IllegalArgumentException.class,
				() -> cell.setY(-1)
				);
	}
	
	
	
	

}
