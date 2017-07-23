package SnakeGame;

public class Coordinate{
	private int x,y;
	
	public Coordinate(int coorX, int coorY){
		x = coorX;
		y = coorY;
	}
	public static Coordinate getRandomCoordinate() {
		return new Coordinate(random10(), random10());
	}
	private static int random10(){
		int randInt;
		java.util.Random rand = new java.util.Random();
		randInt = rand.nextInt(500) + 10;
		float f = randInt/10;
		randInt = Math.round(f) * 10;
		return randInt;
	}
	
	public int x(){ return x; }
	public int y(){ return y; }

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) o;
		if(this.x == c.x() && this.y == c.y())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}
}
