package SnakeGame;

public class SnakeCell {
	private int x, y;
	private Coordinate c;
	
	public SnakeCell(int X, int Y) {
		x = X;
		y = Y;
		c = new Coordinate(X, Y);
	}
	public SnakeCell(Coordinate coor){
		x = coor.x();
		y = coor.y();
		c = coor;
	}
	public Coordinate getCoordinate() {
		return c;
	}
	public int x() { return x; }
	public int y() { return y; }
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof SnakeCell))
			return false;
		SnakeCell other = (SnakeCell) o;
		if(this.x == other.x() && this.y == other.y())
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return x + ", " + y;
	}
}
