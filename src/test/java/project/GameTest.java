package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
	
	private Game game;

		
	
	@BeforeEach
	public void setup() {
		game=new Game(5,6,false,'e');
	}
		
	
	@Test
	public void TestConstructor() {
		game= new Game(5, 6, true, 'e');
		
		//Check dimensions of board
		assertEquals(game.getBoard().getCol(), 6 );
		assertEquals(game.getBoard().getRow(), 5 );
		
		//Checks if all cells initially is AIR
		for (int y = 0; y <= game.getBoard().getRow(); y++) {
			for (int x = 0; x <= game.getBoard().getCol(); x++) {
				assertEquals(game.getBoard().getCell(y, x).getColor(), Colors.AIR);
			}
		}
		
		
		//Test invalid dimensions
		assertThrows(
				IllegalArgumentException.class,
				() -> game = new Game(-3, -6, true, 'm')
				);
		
		
		//Test invalid difficulty
		assertThrows(
				IllegalArgumentException.class,
				() -> game = new Game(5, 6, true, 'k')
				);
	}
	
	@Test
	public void testDifficultyParameters() {
		
		//Check if the difficulty-settings are right
		game= new Game(5, 6, true, 'e');
		assertFalse(game.getPlayer1sTurn());
		assertEquals(game.getMiniMaxDepth(),3);
		
		game= new Game(5, 6, true, 'm');
		assertFalse(game.getPlayer1sTurn());
		assertEquals(game.getMiniMaxDepth(),5);
		
		game= new Game(5, 6, true, 'h');
		assertTrue(game.getPlayer1sTurn());
		assertEquals(game.getMiniMaxDepth(),8);
		
	}
	
	@Test
	public void testNextTurn(){
		
		//test if nextTurn places tokens correctly
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(5);
		game.nextTurn(0);
		assertEquals(game.getBoard().getCell(5, 3).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(5, 2).getColor(), Colors.PLAYER2);
		assertEquals(game.getBoard().getCell(4, 3).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(3, 3).getColor(), Colors.PLAYER2);
		assertEquals(game.getBoard().getCell(5, 1).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(4, 2).getColor(), Colors.PLAYER2);
		assertEquals(game.getBoard().getCell(5, 5).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(5, 0).getColor(), Colors.PLAYER2);
	}
		
	public void testPickingFullColumn() {
		
		//test if nothing happens when picking a full column.
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		game.nextTurn(3);
		
		assertEquals(game.getBoard().getCell(5, 3).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(4, 3).getColor(), Colors.PLAYER2);
		assertEquals(game.getBoard().getCell(3, 3).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(2, 3).getColor(), Colors.PLAYER2);
		assertEquals(game.getBoard().getCell(1, 3).getColor(), Colors.PLAYER1);
		assertEquals(game.getBoard().getCell(0, 3).getColor(), Colors.PLAYER2);
		assertEquals(game.getNumberOfTurns(), 6);
		assertFalse(game.getPlayer1sTurn());
	}
		
	public void testTieGame() {
		
		for (int i=0;i<6;i++) {
			game.nextTurn(0);
		}
		for (int i=0;i<6;i++) {
			game.nextTurn(1);
		}
		for (int i=0;i<6;i++) {
			game.nextTurn(2);
		}
		game.nextTurn(4);
		for (int i=0;i<6;i++) {
			game.nextTurn(3);
		}
		for (int i=0;i<5;i++) {
			game.nextTurn(4);
		}
		for (int i=0;i<6;i++) {
			game.nextTurn(5);
		}
		for (int i=0;i<5;i++) {
			game.nextTurn(6);
		}
		assertFalse(game.isGameTie());
		game.nextTurn(6);
		
		assertTrue(game.isGameTie());
	}
	
	public void testPlayer1Win() {
		
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		assertFalse(game.isPlayer1won());
		game.nextTurn(3);
		assertTrue(game.isPlayer1won());
		
	}
	
	@Test
	public void testPlayer2Win() {
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(4);
		assertFalse(game.isPlayer2won());
		game.nextTurn(2);
		assertTrue(game.isPlayer2won());
		
	}
	
	@Test
	public void testGameOverEdgeCase() {
		
		//game is won in the last possible move
		game.nextTurn(0);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(0);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(6);
		game.nextTurn(5);
		game.nextTurn(4);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(1);
		game.nextTurn(0);
		game.nextTurn(3);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(0);
		game.nextTurn(0);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(0);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(6);
		game.nextTurn(5);
		game.nextTurn(4);
		game.nextTurn(1);
		game.nextTurn(2);
		assertFalse(game.isGameTie());
		assertFalse(game.isPlayer1won());
		assertFalse(game.isPlayer2won());
		game.nextTurn(3);
		assertTrue(game.isPlayer2won());
		assertFalse(game.isGameTie());
	}
	
	@Test
	public void testMakingMoveAfterGameOver() {
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(4);
		game.nextTurn(2);
		//the game is over
		assertThrows(
				IllegalStateException.class,
				() -> game.nextTurn(4)
				);
	}
	
	@Test
	public void testNumberOfTurns() {
		game.nextTurn(0);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(0);
		game.nextTurn(1);
		game.nextTurn(2);
		game.nextTurn(3);
		game.nextTurn(4);
		game.nextTurn(5);
		game.nextTurn(6);
		game.nextTurn(6);
		game.nextTurn(5);
		game.nextTurn(4);
		game.nextTurn(3);
		game.nextTurn(2);
		game.nextTurn(1);
		game.nextTurn(0);
		game.nextTurn(3);
		game.nextTurn(4);
		assertEquals(game.getNumberOfTurns(),23);
		
	}
	
	@Test
	public void testWhoseTurn() {
		assertFalse(game.getPlayer1sTurn());
		game.nextTurn(0);
		assertTrue(game.getPlayer1sTurn());
		game.nextTurn(1);
		assertFalse(game.getPlayer1sTurn());
		game.nextTurn(2);
		assertTrue(game.getPlayer1sTurn());
		game.nextTurn(3);
		assertFalse(game.getPlayer1sTurn());
		game.nextTurn(4);
		assertTrue(game.getPlayer1sTurn());
		game.nextTurn(5);
		assertFalse(game.getPlayer1sTurn());
		game.nextTurn(6);
		assertTrue(game.getPlayer1sTurn());
		
	}
	
}
