package project;

import java.util.Collections;
import java.util.List;

public class MiniMaxAI {
	
		private static final int MAX_SCORE= 1000000;
		private static final int MIN_SCORE= -1000000;
		
		
		// returns the heuristic value of a given board.
		private int calcScoreSum(Board board) {
	    
	        int verticalScore=0, horizontalScore=0, descdiaScore=0, ascdiaScore=0;
	        
	        
	        for (int row = 0; row <= board.getRow() - 3; row++) {
	            for (int col = 0; col <= board.getCol(); col++) {
	                int tempScore = calcScore(board, row, col, 1, 0);
	                verticalScore += tempScore;

	            }
	        }

	        
	        for (int row = 0; row <= board.getRow() ; row++) {
	            for (int col = 0; col <= board.getCol() - 3; col++) {
	                int tempScore = calcScore(board, row, col, 0, 1);
	                horizontalScore += tempScore;

	            }
	        }


	        for (int row = 0; row <= board.getRow() - 3 ; row++) {
	            for (int col = 0; col <= board.getCol() - 3; col++) {
	                int tempScore = calcScore(board, row, col, 1, 1);
	                descdiaScore += tempScore;

	            }
	        }

	        for (int row = 3; row <=board.getRow(); row++) {
	            for (int col = 0; col <=board.getCol() - 3; col++) {
	                int tempScore = calcScore(board, row, col, -1, 1);
	                ascdiaScore += tempScore;

	            }
	        }

	        return verticalScore + horizontalScore + descdiaScore + ascdiaScore;
	        
	    }

		

		
		private int calcScore(Board board, int row, int col, int increment_row, int increment_col) {
	    
	        int aiPoints = 0, playerPoints = 0;

	        for (int i = 0; i < 4; i++) {
	        
	            if(board.getCell(row, col).getColor() == Colors.PLAYER2){
	                aiPoints++;
	            }
	            else if (board.getCell(row, col).getColor() == Colors.PLAYER1) {
	            
	                playerPoints++;
	            }

	            row += increment_row;
	            col += increment_col;
	        }

	        if(playerPoints == 4) {
	            return MIN_SCORE;
	        }
	        else if(aiPoints == 4) {
	            return MAX_SCORE;
	        }
	        else {
	            return aiPoints;
	        }
	    }
		    
		    
	
	 //Simple MiniMax-algorithm with alpha-beta pruning. Shuffles the columns before picking to ensure unpredictability.
		private int[] miniMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) { 
		 
			if (depth==0 || board.isFourConnected()) {
			return new int[] {-1, calcScoreSum(board)};
			}
			if (maximizingPlayer){
				 int max[]= new int[] {-1,Integer.MIN_VALUE};
				 List<Integer> shuffledCols= board.availableCols();
				 Collections.shuffle(shuffledCols);
				 for (int col: shuffledCols) {
					 Board copyOfBoard= board.clone();
					 copyOfBoard.makeMove(col, Colors.PLAYER2);
					 int next[]=miniMax(copyOfBoard, depth-1, alpha, beta, false);
					 if(next[1]>max[1] || max[0]==-1) {
						 max[0]=col;
						 max[1]=next[1];
						 alpha=next[1];
						 if (max[1]==MAX_SCORE) {
							 break;
						 }
					 }
						
					 if (beta <= alpha) {
						 break;
					 }
				 }
				 return max;
			 }
			 else {
				 int min[]=new int[] {-1,Integer.MAX_VALUE};
				 List<Integer> shuffledCols= board.availableCols();
				 Collections.shuffle(shuffledCols);
				 for (int col: shuffledCols) {
					 Board copyOfBoard= board.clone();
					 copyOfBoard.makeMove(col, Colors.PLAYER1);
					 int[] next=miniMax(copyOfBoard,depth-1,alpha,beta,true);
					 if(next[1]<min[1] || min[0]==-1) {
						 min[0]=col;
						 min[1]=next[1];
						 beta=next[1];
						 if (min[1]==MIN_SCORE) {
							 break;
						 }
					 }
				 
					 if  (beta <= alpha){
						 break;
					 }
				 }
				 return min;
			 }
		 }
	 
	 //returns whichever column the Minimax chose.
	 public int AIColChosen(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
		 return miniMax(board, depth, alpha, beta, maximizingPlayer)[0];
	 }
}
