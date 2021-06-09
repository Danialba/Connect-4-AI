package project;


public class Game {

	private Board board;
	private final boolean onePlayer; // decides if it is a two-player or one-player game 
	private final char difficulty; //'e' (easy), 'm' (medium) or 'h' (hard)"
	private boolean gameTie;
	private boolean player1won;
	private boolean player2won;
	private boolean player1sTurn; //decides whose turn it is, ironically, false means that it is player 1's turn.
	private int numberOfTurns;
	private MiniMaxAI miniMaxAI= new MiniMaxAI();
	private int miniMaxDepth; 

	
	public Game(int row, int col, boolean onePlayer, char difficulty) {
		this.board=new Board(row, col);
		this.onePlayer=onePlayer;
		this.difficulty=difficulty;
		if (onePlayer) {
			setDifficultyParameters(difficulty);
		}
	}
		
		
	public boolean getPlayer1sTurn() {
		return player1sTurn;
	}
		
	public Board getBoard(){
		return board;
	}
	
	public char getDifficulty() {
		return difficulty;
	}
	
	public boolean getOnePlayer() {
		return onePlayer;
	}
	
	public int getNumberOfTurns() {
		return numberOfTurns;
	}
	
	public boolean isGameTie() {
		return gameTie;
	}

	public boolean isPlayer1won() {
		return player1won;
	}

	public boolean isPlayer2won() {
		return player2won;
	}
	
	public int getMiniMaxDepth() {
		return miniMaxDepth;
	}
	
	//sets the difficulty by deciding the depth in the MinimaxAI, as well as deciding if the player or computer has the first turn.
	private void setDifficultyParameters(char difficulty) {
		if (!(difficulty=='e' || difficulty=='m' || difficulty=='h')) {
			throw new IllegalArgumentException("difficulty must be 'e' (easy), 'm' (medium) or 'h' (hard)");
		}
		if (difficulty=='e') {
			setPlayer1sTurn(false);
			setMiniMaxDepth(3);
		}
		else if (difficulty=='m') {
			setPlayer1sTurn(false);
			setMiniMaxDepth(5);
		}
		else if (difficulty=='h') {
			setPlayer1sTurn(true);
			setMiniMaxDepth(8);
		}
	}
	

	private void setPlayer1sTurn(boolean player1sTurn) {
		this.player1sTurn=player1sTurn;
	}
	
	private void setPlayer1won(boolean player1won) {
		this.player1won = player1won;
	}
	
	private void setPlayer2won(boolean player2won) {
		this.player2won = player2won;
	}
	
	private void setGameTie(boolean gameTie) {
		this.gameTie = gameTie;
	}
	
	private void setMiniMaxDepth(int miniMaxDepth){
		this.miniMaxDepth=miniMaxDepth;
		
	}
	
	//returns whatever column the miniMaxAI chooses.
	private int computerPlay() {
		return miniMaxAI.AIColChosen(board, miniMaxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);	
	}
	
	
	private void checkGameOver() {
		if (board.isFourConnected()){
			if (player1sTurn) {
				setPlayer1won(true);
			}
			else {
				setPlayer2won(true);
			}
		}
		//checks if the game is tied by checking how many turns there have been
		else if (numberOfTurns==(board.getCol()+1)*(board.getRow()+1)) {
			setGameTie(true);
		}
	}
	
	public void nextTurn(int chosenCol) {
		if (isGameTie()|| isPlayer1won() || isPlayer2won()) {
			throw new IllegalStateException("The game is over");
		}
		
		try {
			player1sTurn=!player1sTurn;
			
			if (player1sTurn) {
				board.makeMove(chosenCol, Colors.PLAYER1);
			}
			
			//if the game is two player, player2 picks the column
			else if(!onePlayer) {
				board.makeMove(chosenCol, Colors.PLAYER2);
			}
			
			//if the game is one player, the computer picks the column
			else if(onePlayer) {
				board.makeMove(computerPlay(), Colors.PLAYER2);
			}
		}
		
		// if the column is full:
		catch (Exception e) {
			player1sTurn=!player1sTurn;
			return;
		}
		numberOfTurns++;
		if (numberOfTurns > 6) {
			checkGameOver();
		}
	}
}
