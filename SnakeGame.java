import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel {
    // Define constants
    private static final int SCALE = 10;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int DELAY = 100;
    private static final int INITIAL_LENGTH = 3;

    // Define game variables
    private ArrayList<Point> snake;
    private Point food;
    private char direction;
    private boolean isGameOver;
    private int score;

    public SnakeGame() {
        snake = new ArrayList<>();
        initializeSnake();
        generateFood();
        direction = 'R';
        isGameOver = false;
        score = 0;

        // Start the game loop
        new Thread(() -> {
            while (!isGameOver) {
                try {
                    Thread.sleep(DELAY);
                    move();
                    checkCollision();
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initializeSnake() {
        snake.clear();
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Point(WIDTH / 2 - i, HEIGHT / 2));
        }
    }

    private void generateFood() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH / SCALE) * SCALE;
        int y = rand.nextInt(HEIGHT / SCALE) * SCALE;
        food = new Point(x, y);
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = (Point) head.clone();

        switch (direction) {
            case 'U':
                newHead.y -= SCALE;
                break;
            case 'D':
                newHead.y += SCALE;
                break;
            case 'L':
                newHead.x -= SCALE;
                break;
            case 'R':
                newHead.x += SCALE;
                break;
        }

        snake.add(0, newHead);

        if (!food.equals(newHead)) {
            snake.remove(snake.size() - 1);
        } else {
            score++;
            generateFood();
        }
    }

    private void checkCollision() {
        Point head = snake.get(0);

        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            isGameOver = true;
            return;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isGameOver = true;
                return;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x, point.y, SCALE, SCALE);
        }
        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, SCALE, SCALE);

        // Draw score
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);

        // Draw game over message
        if (isGameOver) {
            g.drawString("Game Over!", WIDTH / 2 - 50, HEIGHT / 2);
        }
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            SnakeGame snakeGame = new SnakeGame();
            frame.add(snakeGame);
            frame.setVisible(true);

            // Allow user to control the direction of the snake using arrow keys
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    char key = e.getKeyChar();
                    if (key == 'w' || key == 'W' || e.getKeyCode() == KeyEvent.VK_UP) {
                        snakeGame.setDirection('U');
                    } else if (key == 's' || key == 'S' || e.getKeyCode() == KeyEvent.VK_DOWN) {
                        snakeGame.setDirection('D');
                    } else if (key == 'a' || key == 'A' || e.getKeyCode() == KeyEvent.VK_LEFT) {
                        snakeGame.setDirection('L');
                    } else if (key == 'd' || key == 'D' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        snakeGame.setDirection('R');
                    }
                }
            });
        });
    }
}
