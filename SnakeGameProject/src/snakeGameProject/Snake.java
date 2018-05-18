package snakeGameProject;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

//implements an interface which gives abstract methods or snake inherients abstract methods
public class Snake implements ActionListener, KeyListener {

	public JFrame jframe;
	public RenderPanel renderPanel;
	// This is so that we can access the snake statically at anytime
	public static Snake snake;
	public Timer timer = new Timer(20, this);
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10, WIDTH = 800, HEIGHT = 700;
	// direction is a public int as well
	public int ticks = 0, direction = DOWN, tailLength = 1;
	public static int score = 0;
	public Point head, cherry;
	public Random random;
	public Dimension dim;
	public boolean gameOver = false;// , paused = false;
	public static boolean paused = false;

	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		// This is the frame that we can see the game in
		jframe = new JFrame("Snake");
		// This makes the window visible
		jframe.setVisible(true);
		// sets the size of the window
		jframe.setSize(WIDTH, HEIGHT);
		// player can't change the window size
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		// This makes the program end when you close the window.
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adding this instance as a keyListener
		jframe.addKeyListener(this);
		startGame();
	}

	public void startGame() {
		// This resets the game
		gameOver = false;
		paused = false;
		score = 0;
		tailLength = 1;
		direction = DOWN;
		// this resets the screen
		snakeParts.clear();
		// sets the head to place 0,-1 at the beginning of the game
		// the y being -1 makes the head been seen at 0,0 after moving and being painted
		head = new Point(0, -1);
		// creates a random object
		random = new Random();
		// creates cherry on the board
		cherry = new Point(random.nextInt(WIDTH / SCALE), random.nextInt(HEIGHT / SCALE));
		// for loop that adds a head at the point of the tail
		for (int i = 0; i < tailLength; i++) {
			snakeParts.add(new Point(head.x - 1, head.y - 1));
		}
		// actually starts the timer, w/out this the timer will never work.
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Now it will repaint this every tick
		renderPanel.repaint();
		// increments the tick counter every tick
		ticks++;

		// updates every half second
		if (ticks % 5 == 0 && head != null && !gameOver && !paused) {

			snakeParts.add(new Point(head.x, head.y));

			if (direction == UP && noTailAt(head.x, head.y - 1)) {
				if (head.y - 1 >= 0) {
					head = new Point(new Point(head.x, head.y - 1));
				} else {
					gameOver = true;
				}
			}
			if (direction == DOWN && noTailAt(head.x, head.y + 1)) {
				if (head.y < (HEIGHT / SCALE) - 3) {
					head = new Point(new Point(head.x, head.y + 1));
				} else {
					gameOver = true;
				}
			}
			if (direction == LEFT && noTailAt(head.x - 1, head.y)) {
				if (head.x - 1 >= 0) {
					head = new Point(new Point(head.x - 1, head.y));
				} else {
					gameOver = true;
				}
			}
			if (direction == RIGHT && noTailAt(head.x + 1, head.y)) {
				if (head.x < (WIDTH / SCALE) - 1) {
					head = new Point(new Point(head.x + 1, head.y));
				} else {
					gameOver = true;
				}
			}
			// removes the last part of the snake so it appears to move
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}

			if (cherry != null) {
				if (head.equals(cherry)) {
					score += 10;
					tailLength++;
					cherry.setLocation(random.nextInt((WIDTH / SCALE) - 1), random.nextInt((HEIGHT / SCALE) - 3));
				}
			}
		}
	}

	private boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				gameOver = true;
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		snake = new Snake();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		// gets the direction from the keyboard && prevents player from turning in on
		// themselves
		if (i == KeyEvent.VK_A && direction != RIGHT && !paused) {
			direction = LEFT;
		} else if (i == KeyEvent.VK_LEFT && direction != RIGHT && !paused) {
			direction = LEFT;
		} else if (i == KeyEvent.VK_D && direction != LEFT && !paused) {
			direction = RIGHT;
		} else if (i == KeyEvent.VK_RIGHT && direction != LEFT && !paused) {
			direction = RIGHT;
		} else if (i == KeyEvent.VK_S && direction != UP && !paused) {
			direction = DOWN;
		} else if (i == KeyEvent.VK_DOWN && direction != DOWN && !paused) {
			direction = DOWN;
		} else if (i == KeyEvent.VK_W && direction != DOWN && !paused) {
			direction = UP;
		} else if (i == KeyEvent.VK_UP && direction != DOWN && !paused) {
			direction = UP;
		}
		// This is to pause the game
		else if (i == KeyEvent.VK_SPACE) {
			if (gameOver == true) {
				startGame();
			} else {
				// sets paused to the opposite value of current paused
				paused = !paused;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
