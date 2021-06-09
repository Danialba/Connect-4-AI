package project;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BoardTest {
	
	private Board board;
	
	
	private void makeFullBoard(Board board) {
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		
		board.makeMove(2, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(4, Colors.PLAYER1);
	
		
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(5, Colors.PLAYER2);
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(5, Colors.PLAYER2);
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(5, Colors.PLAYER2);
		
		board.makeMove(6, Colors.PLAYER1);
		board.makeMove(6, Colors.PLAYER2);
		board.makeMove(6, Colors.PLAYER1);
		board.makeMove(6, Colors.PLAYER2);
		board.makeMove(6, Colors.PLAYER1);
		board.makeMove(6, Colors.PLAYER2);
		
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(0, Colors.PLAYER2);
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(0, Colors.PLAYER2);
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(0, Colors.PLAYER2);
	
	}
	
	@BeforeEach
	private void setup() {
		board = new Board(5,6);
	}
	
	@Test
	public void testConstructor() {
		
		
		//Test dimensions
		assertEquals(board.getCol(),6);
		assertEquals(board.getRow(),5);
		
		//Checks if all cells initially is AIR
		for (int y = 0; y <= board.getRow(); y++) {
			for (int x = 0; x <= board.getCol(); x++) {
				assertEquals(board.getCell(y, x).getColor(), Colors.AIR);
			}
		}
				
				
		//Test edge-case
		board=new Board(0,0);
		assertEquals(board.getCol(),0);
		assertEquals(board.getRow(),0);
		
		// Test negative dimensions
		assertThrows(
				IllegalArgumentException.class,
				() -> board = new Board(9, -1)
				);
	}
	
	@Test
	public void testCloning() {
		board = new Board(5,6);
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(0, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER2);
		Board boardClone=board.clone();
		
		//test dimensions
		assertEquals(board.getCol(),boardClone.getCol());
		assertEquals(board.getRow(),boardClone.getRow());
		
		//test if the colors are alike
		for (int y = 0; y <= board.getRow(); y++) {
			for (int x = 0; x <= board.getCol(); x++) {
				assertEquals(board.getCell(y, x).getColor(), boardClone.getCell(y, x).getColor());
			}
		}
	}
	
	@Test
	public void testMakeMove() {
		board = new Board(5,6);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(6, Colors.PLAYER1);
		board.makeMove(6, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);

	
		
		assertEquals(board.getCell(5, 4).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(4, 4).getColor(), Colors.PLAYER2);
		assertEquals(board.getCell(5, 2).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(5, 3).getColor(), Colors.PLAYER2);
		assertEquals(board.getCell(5, 6).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(4, 6).getColor(), Colors.PLAYER2);
		assertEquals(board.getCell(5, 1).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(4, 3).getColor(), Colors.PLAYER2);
		assertEquals(board.getCell(3, 3).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(2, 3).getColor(), Colors.PLAYER2);
		assertEquals(board.getCell(1, 3).getColor(), Colors.PLAYER1);
		assertEquals(board.getCell(0, 3).getColor(), Colors.PLAYER2);
		
		
		//Test full column
		assertThrows(
				IllegalArgumentException.class,
				() -> board.makeMove(3, Colors.PLAYER1)
				);
		
		//Test argument out of bounds
		
		assertThrows(
				IllegalArgumentException.class,
				() -> board.makeMove(11, Colors.PLAYER1)
				);
		
		assertThrows(
				IllegalArgumentException.class,
				() -> board.makeMove(-1, Colors.PLAYER1)
				);
	}
	
	
	@Test
	public void testAvailableColumns() {
		
		//test empty board
		board = new Board(5,6);
		List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
		assertEquals(board.availableCols(), list);
		
		//test semi-empty board
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		List<Integer> list2 = Arrays.asList(0, 1, 2, 4, 5, 6);
		assertEquals(board.availableCols(), list2);
		
		
		//test full board
		Board fullBoard = new Board(5,6);
		makeFullBoard(fullBoard);
		
		List<Integer>emptyList=new ArrayList<Integer>();		
		assertEquals(fullBoard.availableCols(),emptyList);
		
	}
	
	@Test
	public void testisFourConnected() {
		
		board = new Board(5,6);
		
		//test empty board
		assertFalse(board.isFourConnected());
		
		//test three in a row horizontally
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(0, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER2);
		assertFalse(board.isFourConnected());
		
		//test four in a row horizontally
		board.makeMove(3, Colors.PLAYER1);
		assertTrue(board.isFourConnected());
		
		//test three in a row vertically
		board = new Board(5,6);
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(0, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		assertFalse(board.isFourConnected());
		
		//test four in a row vertically
		board.makeMove(0, Colors.PLAYER1);
		assertTrue(board.isFourConnected());
		
		//test three in a row ascending diagonally
		board = new Board(5,6);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(5, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER1);
		assertFalse(board.isFourConnected());
		
		//test four in a row ascending diagonally
		board.makeMove(5, Colors.PLAYER2);
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(5, Colors.PLAYER1);
		assertTrue(board.isFourConnected());
		
		//test three in a row descending diagonally
		board = new Board(5,6);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(3, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER1);
		assertFalse(board.isFourConnected());
		
		
		//test four in a row descending diagonally
		board.makeMove(1, Colors.PLAYER2);
		board.makeMove(1, Colors.PLAYER1);
		board.makeMove(1, Colors.PLAYER1);
		assertTrue(board.isFourConnected());
		
		
		//test when last token gets placed in between three others.
		board = new Board(5,6);
		board.makeMove(4, Colors.PLAYER1);
		board.makeMove(4, Colors.PLAYER2);
		board.makeMove(5, Colors.PLAYER1);
		board.makeMove(5, Colors.PLAYER2);
		board.makeMove(2, Colors.PLAYER1);
		board.makeMove(2, Colors.PLAYER2);
		board.makeMove(3, Colors.PLAYER1);
		assertTrue(board.isFourConnected());
	}
	
	@Test
	public void testLastChosenCell() {
		board.makeMove(5, Colors.PLAYER1);
		assertEquals(board.getLastChosenCell().getX(), board.getCell(5, 5).getX());
		assertEquals(board.getLastChosenCell().getY(), board.getCell(5, 5).getY());
		assertEquals(board.getLastChosenCell().getColor(), board.getCell(5, 5).getColor());
		board.makeMove(0, Colors.PLAYER2);
		assertEquals(board.getLastChosenCell().getX(), board.getCell(5, 0).getX());
		assertEquals(board.getLastChosenCell().getY(), board.getCell(5, 0).getY());
		assertEquals(board.getLastChosenCell().getColor(), board.getCell(5, 0).getColor());
		board.makeMove(1, Colors.PLAYER1);
		assertEquals(board.getLastChosenCell().getX(), board.getCell(5, 1).getX());
		assertEquals(board.getLastChosenCell().getY(), board.getCell(5, 1).getY());
		assertEquals(board.getLastChosenCell().getColor(), board.getCell(5, 1).getColor());
		board.makeMove(1, Colors.PLAYER1);
		assertEquals(board.getLastChosenCell().getX(), board.getCell(4, 1).getX());
		assertEquals(board.getLastChosenCell().getY(), board.getCell(4, 1).getY());
		assertEquals(board.getLastChosenCell().getColor(), board.getCell(4, 1).getColor());
		board.makeMove(2, Colors.PLAYER2);
		assertEquals(board.getLastChosenCell().getX(), board.getCell(5, 2).getX());
		assertEquals(board.getLastChosenCell().getY(), board.getCell(5, 2).getY());
		assertEquals(board.getLastChosenCell().getColor(), board.getCell(5, 2).getColor());
	}
	
	

}
