package project;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private final int row;
	private final int col;
	private Cell[][] board;
	private Cell lastChosenCell;
	
	
	public Board(int row, int col) {
		if (row<0 ||col<0) {
			throw new IllegalArgumentException("Row and col must be positive");
		}
		this.row=row;
		this.col=col;
		this.board = new Cell[row+1][col+1];

		
		for (int y = 0; y <=row; y++) {
			for (int x = 0; x <= col; x++) {
				board[y][x] = new Cell(y, x);
				board[y][x].setColor(Colors.AIR);
			}
		}
	}
	
	//Constructor used to deepclone a board.
	//Everything except lastChosenCell gets cloned
	public Board(Cell[][] board) {
		this.board=board;
		row=board.length-1;
		col=board[0].length-1;
	}
	
	
	
	public Cell[][] getBoard(){
		return board;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getRow(){
		return row;
	}
	
	public Cell getCell(int row, int col) {
		return board[row][col];
	}
	
	public Cell getLastChosenCell() {
		return lastChosenCell;
	}
	
	


	//Returns a list of all columns that are not full.
	public List<Integer> availableCols() { 
		List<Integer>availableCols=new ArrayList<Integer>();
		for (int col=0;col<=getCol();col++){
			if (getCell(0,col).getColor()==Colors.AIR) {
				availableCols.add(col);
			}
		}
		return availableCols;
	}
	
	
	//returns the highest empty row for a given column. Returns -1 if column is full.
	private int getHighestEmptyRow(int chosenCol) {
		int counter = 0;
		for (int i=0;i<=getRow();i++) {
			if (getCell(i,chosenCol).getColor()==Colors.AIR){
				counter++;
			}
		}
		return counter-1;
	}
	
	
	
	//Checks if four is connected by using the last chosen cell.
	public boolean isFourConnected() {
		
		//if there has been no moves yet
		if (getLastChosenCell()==null){
			return false;
		}
		
		int[][] directions= {{0,1},{-1,1},{1,1},{1,0},{1,-1},{-1,-1}, {-1,0},{0,-1}};
		
		
		for (int[] dir: directions) {
			int dy = dir[0];
			int dx = dir[1];
			int x = getLastChosenCell().getX();
			int y = getLastChosenCell().getY();
			
			
			//Checks last chosen cell and the three cells next by in all directions
			if (y+3*dy>=0 && y+3*dy<=getRow() && 
				x+3*dx>=0 && x+3*dx <= getCol()) {
				if(
					board[y][x].getColor()==board[y+dy][x+dx].getColor() &&
					board[y][x].getColor()==board[y+2*dy][x+2*dx].getColor() &&
					board[y][x].getColor()==board[y+3*dy][x+3*dx].getColor()) {
					return true;
				}
			}
			
			
			// Checks if the last chosen cell is between one and two of the same color.
			//This is impossible for vertical directions.
			if (dx==0) {
				continue;
			}
			if (y-2*dy>=0 &&
				x-2*dx>=0 &&
				y+dy>=0 &&
				x+dx>=0 && 
				y-2*dy<=getRow() && 
				x-2*dx<=getCol() && 
				y+dy<=getRow() &&
				x+dx<=getCol()) {
				if(
					board[y][x].getColor()==board[y+dy][x+dx].getColor() &&
					board[y][x].getColor()==board[y-dy][x-dx].getColor() &&
					board[y][x].getColor()==board[y-2*dy][x-2*dx].getColor()) {
					return true;
				}
			}	
		}

	return false;	
	}
	
	public void makeMove(int chosenCol, Colors player) {
		if (chosenCol > getCol() || chosenCol<0) {
			throw new IllegalArgumentException("Argument out of bounds");
		}
		int highestEmptyRow= getHighestEmptyRow(chosenCol);
		if (highestEmptyRow==-1) {
			throw new IllegalArgumentException("The column is full");
		}
		getCell(highestEmptyRow,chosenCol).setColor(player);
		lastChosenCell= getCell(highestEmptyRow,chosenCol);
	}

	
	
	//method that deepclones the board. Useful in Minimax-algorithms
	@Override
	public Board clone(){
        Cell [][] clonedBoard = new Cell[getRow()+1][getCol()+1];
        for (int y = 0; y <= getRow(); y++) {
			for (int x = 0; x <= getCol(); x++) {
				clonedBoard[y][x] = new Cell(y,x);
				clonedBoard[y][x].setColor(getCell(y,x).getColor());
			}
		}
        return new Board(clonedBoard);
	}
}
