package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameController {
	
	private static final int row=5;
	private static final int col=6;
	
	
	private FileHandler fileHandler= new FileHandler();
	private Game game;
	private long startTime;
	
	
	@FXML private Pane rightPane; // carries all the buttons and text to the right of the gameplay.
	@FXML private Pane boardPane;// carries the white, red and yellow circles
	@FXML private Pane paneWithCols; //carries the column buttons
	@FXML private Pane paneInFront; // carries the win-texts and highscores. Moves to front when it should be impossible to pick a column.
	@FXML private Text youStart,yellowStart, timeText;
	@FXML private Button col0, col1, col2, col3, col4, col5, col6;
	
	//These buttons are not visible as default
	@FXML private Button onePlayer, twoPlayer;
	@FXML private Button easy, medium, hard;
	@FXML private Button resetGame, showHighscores;
	
	
	
	
	@FXML
	private void initialize() {
		createBoard();
		}

	private void createBoard() {
		for (int x = 0; x <= col; x++) {
			for (int y = 0; y <= row; y++) {
				Circle circle = new Circle();
				circle.setRadius(40);
				circle.setCenterX(50+x*100);
				circle.setCenterY(50+y*100);
				circle.setFill(Color.WHITE);
				boardPane.getChildren().add(circle);
			}
		}
		paneInFront.toFront();
		twoPlayer.setVisible(true);
		onePlayer.setVisible(true);
		resetGame.setVisible(true);

		
	}
	
	@FXML
	private void handle1Player() {
		onePlayer.setVisible(false);
		twoPlayer.setVisible(false);
		easy.setVisible(true);
		medium.setVisible(true);
		hard.setVisible(true);
	}
	
	@FXML
	private void handle2Player() {
		paneWithCols.toFront();
		twoPlayer.setVisible(false);
		onePlayer.setVisible(false);
		yellowStart.setVisible(true);
		game= new Game(row,col,false,'e');
	}
	
	@FXML
	private void handleEasy() {
		paneWithCols.toFront();
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		youStart.setVisible(true);
		game= new Game(row,col,true,'e');

	}
	
	@FXML
	private void handleMedium() {
		paneWithCols.toFront();
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		youStart.setVisible(true);
		game= new Game(row,col,true,'m');
	}
	@FXML
	private void handleHard() {
		paneWithCols.toFront();
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		startTime= System.nanoTime();
		game= new Game(row,col,true,'h');
		computerPlay();
	}
	
	private void computerPlay() {
		drawBoard(-1);
	}
	
	private void drawBoard(int chosenCol) {
		youStart.setVisible(false);
		
		if ((game.getDifficulty()=='e' || game.getDifficulty()=='m') && game.getNumberOfTurns()==0) {
			yellowStart.setVisible(false);
			startTime= System.nanoTime();
			}

		game.nextTurn(chosenCol);
		drawCircle();

		if (game.isPlayer1won()) {
			paneInFront.toFront();
			drawGameOverText("YELLOW WON!");
			if (game.getOnePlayer()) {
				long endTime=System.nanoTime();
				long timeElapsed= endTime-startTime;
				updateFile(timeElapsed);
				drawYourTime(timeElapsed);
				showHighscores.setVisible(true);
			}
			
		}
		else if (game.isPlayer2won()) {
			paneInFront.toFront();
			drawGameOverText("RED WON!");
			if (game.getOnePlayer()) {
				showHighscores.setVisible(true);
			}
		}
		else if (game.isGameTie()) {
			drawGameOverText("IT IS A TIE!");
			paneInFront.toFront();
			if (game.getOnePlayer()) {
				showHighscores.setVisible(true);
			}
		}
		
		else if (game.getOnePlayer() && game.getPlayer1sTurn()) {
			computerPlay();
		}
		
	}
	
	private void drawCircle() {
		Circle circle = new Circle();
		circle.setRadius(40);
		circle.setCenterX(50+game.getBoard().getLastChosenCell().getX()*100);
		circle.setCenterY(50+game.getBoard().getLastChosenCell().getY()*100);
		if (game.getPlayer1sTurn()) {
			circle.setFill(Color.YELLOW);
		}
		else if (!game.getPlayer1sTurn()){
			circle.setFill(Color.FIREBRICK);
		}
		boardPane.getChildren().add(circle);
	}
	
	
	private void drawYourTime(long time) {
		timeText=new Text("   Your time:\n" + getReadableTime(time));
		timeText.setY(300);
		timeText.setX(20);
		timeText.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		rightPane.getChildren().add(timeText);
	}
	
	private void drawGameOverText(String gameOverText) {
		Text gameOver=new Text(gameOverText);
		gameOver.setFont(Font.font("verdana", FontWeight.BOLD, 70));
		gameOver.setY(150);
		gameOver.layoutXProperty().bind(paneInFront.widthProperty().subtract(gameOver.prefWidth(-1)).divide(2));
		paneInFront.getChildren().add(gameOver);		
		
	}
	
	private void drawErrorText(String errorText ,int yPlacement) {
		Text error= new Text(errorText);
		error.setY(yPlacement);
		error.layoutXProperty().bind(paneInFront.widthProperty().subtract(error.prefWidth(-1)).divide(2));
		paneInFront.getChildren().add(error);		
	}
	
		
	private String convertFileToReadable(String filename) throws FileNotFoundException {
		String highScoreList="";
		int num=1;
		for (long score: fileHandler.readFromFile(filename)){
			highScoreList+=num+"."+getReadableTime(score)+"\n";
			num++;
		}
		return highScoreList;
	}
	


	private void updateFile(long time)  {
		try {
		if (game.getDifficulty()=='e'){
				fileHandler.writeToFile("EasyHighScores", time);
			}
			else if (game.getDifficulty()=='m'){
				fileHandler.writeToFile("MediumHighScores", time);
			}
			else {
				fileHandler.writeToFile("HardHighScores", time);
			}
		}
		catch(IOException e) {
			drawErrorText("Error: Cannot update the highscoretables with your time", 300);
		}
	}
	
	private String getReadableTime(Long nanoSec){
        Duration duration = Duration.ofNanos(nanoSec);
        return String.format(" %dm:%ds:%dms",
               duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart());
	}
	

	@FXML
	private void handleHighScores()  {
		try {
			Text EasyHighScores= new Text("EASY MODE\n" + convertFileToReadable("EasyHighScores"));
			Text MediumHighScores=new Text("MEDIUM MODE\n" + convertFileToReadable("MediumHighScores"));
			Text HardHighScores= new Text("HARD MODE\n" + convertFileToReadable("HardHighScores"));
		
		
		Rectangle r = new Rectangle();
		r.setX(0);
		r.setY(230);
		r.setWidth(700);
		r.setHeight(220);
		r.setFill(Color.CORNFLOWERBLUE);
		r.setArcWidth(20);
		r.setArcHeight(20);
		
		EasyHighScores.setX(75);
		EasyHighScores.setY(300);
		MediumHighScores.setX(275);
		MediumHighScores.setY(300);
		HardHighScores.setX(475);
		HardHighScores.setY(300);
		EasyHighScores.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		MediumHighScores.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		HardHighScores.setFont(Font.font("verdana", FontWeight.BOLD, 15));
		
		paneInFront.getChildren().add(r);
		paneInFront.getChildren().add(EasyHighScores);
		paneInFront.getChildren().add(MediumHighScores);
		paneInFront.getChildren().add(HardHighScores);
		}
		catch(FileNotFoundException e) {
			drawErrorText("Error: Cannot find highscoretables", 320);
		}
		
	}
	
	
	@FXML
	private void col0Chosen() {
		drawBoard(0);
	}
	@FXML
	private void col1Chosen() {
		drawBoard(1);
	}
	@FXML
	private void col2Chosen() {
		drawBoard(2);
	}
	@FXML
	private void col3Chosen() {
		drawBoard(3);
	}
	@FXML
	private void col4Chosen() {
		drawBoard(4);
	}
	@FXML
	private void col5Chosen() {
		drawBoard(5);
	}
	@FXML
	private void col6Chosen() {
		drawBoard(6);
	}
	
	@FXML
	private void resetGameClicked() {
		if (timeText!=null) {
			timeText.setVisible(false);
		}
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		yellowStart.setVisible(false);
		youStart.setVisible(false);
		paneInFront.getChildren().clear();
		boardPane.getChildren().clear();
		paneWithCols.toFront();
		showHighscores.setVisible(false);
		initialize();
	}
	
	
}
