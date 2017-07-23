package SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Game extends JFrame{
	public static void main(String[] args) {
		Game g = new Game(600, 600);
		try{
			g.loop();
		}catch(Exception e){}
	}
	java.util.ArrayList<SnakeCell> snake;
	
	JFrame frame;
	JPanel panel;
	Direction d;		//Direction that the head of the snake is going
	SnakeCell food;
	SnakeCell head;
	
	/*
	 * Possible values of xInc and yInc are:
	 * 0 - constant, nonchanging component
	 * 10 - increasing component (increasing y is down, increasing x is right)
	 * -10 - decreasing component (decreasing y is up, decreasing x is left)
	 */
	protected int xInc, yInc, score;
	public int w, h;
	
	Game(int width, int height){
		snake = new java.util.ArrayList<SnakeCell>(2);
		food = new SnakeCell(Coordinate.getRandomCoordinate());
		frame = new JFrame("Snake");
		panel = new JPanel();
		d = Direction.RIGHT;
		xInc = 10; 
		yInc = score = 0;
		w = width;
		h = height;
		
		//The body of the snake starts with 3 cells
		head = new SnakeCell(Coordinate.getRandomCoordinate());
		SnakeCell second = new SnakeCell(head.x() - 10, head.y());
		SnakeCell third = new SnakeCell(head.x() - 20, second.y());
		snake.add(head);
		snake.add(second);
		snake.add(third);
		
		frame.addKeyListener(new KeyButtonHandler());
		frame.setContentPane(panel);
		frame.setSize(w, h);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	void loop() throws InterruptedException {
		PanelPainter pp = null;
		while(true){
			Thread.sleep(75);
			
			//At each iteration of the loop (aka when the frame gets renewed),
			//we need the location of the head in order to do work on the snake.
			//Each cell of the snake must follow in the same path the head takes.
			//The head LEADS the snake.
			head = snake.get(0);
			pp = new PanelPainter();
			panel = new JPanel();
			panel.add(pp);
	
			frame.setContentPane(panel);
			frame.validate();
			frame.repaint();
			
			//Shifts the ArrayList to the right, so the position of the cell before it
			//becomes its new position, as the snake is moving forward.
			for(int i = snake.size()-1; i > 0; i--) {
				snake.set(i, snake.get(i-1));
			}
			
			//The head is updated here, which is what the rest of the ArrayList
			//Will follow
			int X = head.x() + xInc;
			int Y = head.y() + yInc;
			if(X >= w) { X = 0; }
			if(X < 0) { X = 590; }
			if(Y >= h) { Y = 0; }
			if(Y < 0) { Y = 590; }
			snake.set(0, new SnakeCell(X, Y));
		}
	}
	private boolean gameOver(java.util.ArrayList<SnakeCell> cells) {
		SnakeCell head = cells.get(0);
		for(int i = 1; i < cells.size(); i++) {
			if(head.equals(cells.get(i)))
				return true;
		}
		return false;
	}
	class PanelPainter extends JPanel{
		
		@Override
		protected void paintComponent(Graphics g){
			//loop through the snake arraylist
			super.paintComponent(g);
			//Draws the grid lines
			/*for(int i = 0; i < 600; i+=10) {
				g.drawLine(0, i, 600, i);
				g.drawLine(i, 0, i, 600);
			}*/
			for(int i = 0; i < snake.size(); i++) {
				g.setColor(Color.BLACK);
				SnakeCell sc = snake.get(i);
				int x = sc.x();
				int y = sc.y();
				g.drawRect(x, y, 10, 10);
				g.setColor(Color.GREEN);
				g.fillRect(x, y, 10, 10);
			}
			if(gameOver(snake)) {
				//This means the snake ate its own tail, so GAME OVER
				JFrame exit = new JFrame("Results");
				exit.setVisible(true);
				String endMessage = "Game over. Your end score is: " + Integer.toString(score);
				JOptionPane.showMessageDialog(exit, endMessage);
				System.exit(0);
			}
			if(food != null) {
	
				int x = food.x();
				int y = food.y();
				g.setColor(Color.BLACK);
				g.fillRect(x, y, 10, 10);
				g.setColor(Color.RED);
				g.fillRect(x, y, 10, 10);
				if(snake.contains(food)) {
					score++;
					//Move the head forward, add a SnakeCell right where it was
					SnakeCell cell = head;
					int X = head.x() + xInc;
					int Y = head.y() + yInc;
					if(X >= w) { X = 0; }
					if(X < 0) { X = 590; }
					if(Y >= h) { Y = 0; }
					if(Y < 0) { Y = 590; }
					//New position of the head incremented based on its position
					snake.set(0, new SnakeCell(X, Y));
					//Old position of the head is preserved.
					snake.add(1, cell);
					food = new SnakeCell(Coordinate.getRandomCoordinate());
					frame.repaint();
				}
			}
		}
		@Override
		public Dimension getPreferredSize(){
			return new Dimension(w, h);
		}
	}	
	class KeyButtonHandler implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_UP && d != Direction.DOWN) {
				yInc = -10;
				xInc = 0;
				d = Direction.UP;
			}
			if(key == KeyEvent.VK_DOWN && d != Direction.UP) {
				yInc = 10;
				xInc = 0;
				d = Direction.DOWN;
			}
			if(key == KeyEvent.VK_LEFT && d != Direction.RIGHT) {
				xInc = -10;
				yInc = 0;
				d = Direction.LEFT;
			}
			if(key == KeyEvent.VK_RIGHT && d != Direction.LEFT) {
				xInc = 10;
				yInc = 0;
				d = Direction.RIGHT;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
		
	}
}

