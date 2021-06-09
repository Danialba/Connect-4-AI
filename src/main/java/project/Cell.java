package project;

public class Cell {

	
	private Colors color=Colors.AIR;
	private int x;
	private int y;
	
	public Cell(int y, int x) {
		checkPositive(y);
		checkPositive(x);
		this.x = x;
		this.y = y;
				
	}

	public Colors getColor() {
		return this.color;
	}
	
	public void setColor(Colors color) {
		this.color=color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		checkPositive(x);
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		checkPositive(y);
		this.y = y;
	}
	
	private void checkPositive(int z) {
		if (z<0) {
			throw new IllegalArgumentException("The value of " + z +" must be positive");
		}
		else {
			return;
		}
	}
	
	
	
	
}
