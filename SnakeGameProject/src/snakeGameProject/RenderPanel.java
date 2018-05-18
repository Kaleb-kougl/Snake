package snakeGameProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

//This is where we have everything painted at
public class RenderPanel extends JPanel {

	private static final String PAUSED = "Paused";
	public static int curColor = 0;

	// overriding a protected method(which can only be accesssed from anything in
	// the same package)
	@Override
	protected void paintComponent(Graphics g) {
		String endMessage = "Game Over Score: " + Snake.score;

		// calls the superclass (JPanel) and paints that component using Graphics
		// at the top so that it works properly, not fully sure why this makes a diff
		// here
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Snake.WIDTH, Snake.HEIGHT);

		// Colors the snake green
		Snake snake = Snake.snake;
		g.setColor(Color.GREEN);
		for (Point point : snake.snakeParts) {
			g.setColor(Color.GREEN);
			g.fillRect(point.x * snake.SCALE, point.y * snake.SCALE, snake.SCALE, snake.SCALE);
		}

		// This creates the red cherry on the map
		g.setColor(Color.RED);
		g.fillRect(snake.cherry.x * snake.SCALE, snake.cherry.y * snake.SCALE, snake.SCALE, snake.SCALE);

		// This is to make sure that our head gets colored
		g.setColor(Color.GREEN);
		g.fillRect(snake.head.x * snake.SCALE, snake.head.y * snake.SCALE, snake.SCALE, snake.SCALE);

		// This will show that the game is paused
		g.setColor(Color.WHITE);
		if (Snake.paused == true) {
			g.drawString(PAUSED, (Snake.WIDTH / 2) - (PAUSED.length() / 2), Snake.HEIGHT / 2 - (PAUSED.length() - 2));
		}

		// This will show that the game is over
		if (snake.gameOver == true) {
			g.drawString(endMessage, (Snake.WIDTH / 2) - (g.getFontMetrics().stringWidth(endMessage) / 2),
					Snake.HEIGHT / 2);
		}
	}
}
