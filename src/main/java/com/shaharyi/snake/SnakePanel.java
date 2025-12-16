package com.shaharyi.snake;

import com.shaharyi.node.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class SnakePanel extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;

    private static final int ROWS = 25;
    private static final int COLS = 30;
    private static final int CELL = 20;

    private static final int WIDTH = COLS * CELL;
    private static final int HEIGHT = ROWS * CELL;

    private Timer timer = new Timer(16, this); // ~60 FPS
    private int moveDelay = 8;                // snake moves every 8 frames
    private int frameCounter = 0;

    private Node<Point> head; // snake head (linked list)
    private int dx = 1, dy = 0;

    private int appleX, appleY;
    private boolean gameOver = false;

    private BufferedImage offscreen = 
        new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

    public SnakePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        initGame();
        timer.start();
    }

    private void initGame() {
        head = new Node<>(new Point(15, 12),
                new Node<>(new Point(14, 12),
                        new Node<>(new Point(13, 12), null)));

        dx = 1; dy = 0;
        placeApple();
        gameOver = false;
        frameCounter = 0;
    }

    private void placeApple() {
        appleX = (int)(Math.random() * COLS);
        appleY = (int)(Math.random() * ROWS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            frameCounter++;

            if (frameCounter >= moveDelay) {
                frameCounter = 0;
                moveSnake();
            }
        }

        repaint();
    }

    private void moveSnake() {
        Point oh = head.getValue();

        int nx = oh.x + dx;
        int ny = oh.y + dy;

        if (nx < 0) nx = COLS - 1;
        if (nx >= COLS) nx = 0;
        if (ny < 0) ny = ROWS - 1;
        if (ny >= ROWS) ny = 0;

        if (collides(nx, ny)) {
            gameOver = true;
            return;
        }

        // new head
        head = new Node<>(new Point(nx, ny), head);

        if (nx == appleX && ny == appleY) {
            placeApple(); // grow
        } else {
            removeTail(); // keep size
        }
    }

    private boolean collides(int x, int y) {
        for (Node<Point> cur = head; cur != null; cur = cur.getNext()) {
            Point p = cur.getValue();
            if (p.x == x && p.y == y)
                return true;
        }
        return false;
    }

    private void removeTail() {
        if (head == null || head.getNext() == null) return;

        Node<Point> cur = head;
        while (cur.getNext().getNext() != null) {
            cur = cur.getNext();
        }
        cur.setNext(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D og = offscreen.createGraphics();

        og.setColor(Color.BLACK);
        og.fillRect(0, 0, WIDTH, HEIGHT);

        og.setColor(Color.RED);
        og.fillOval(appleX * CELL, appleY * CELL, CELL, CELL);

        // draw snake
        og.setColor(Color.GREEN);
        for (Node<Point> cur = head; cur != null; cur = cur.getNext()) {
            Point p = cur.getValue();
            og.fillRect(p.x * CELL, p.y * CELL, CELL, CELL);
        }

        if (gameOver) {
            og.setColor(Color.WHITE);
            og.setFont(new Font("Arial", Font.BOLD, 32));
            og.drawString("GAME OVER", 80, HEIGHT / 2);
            og.setFont(new Font("Arial", Font.PLAIN, 18));
            og.drawString("Press R to restart", 110, HEIGHT / 2 + 40);
        }

        og.dispose();
        g.drawImage(offscreen, 0, 0, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (gameOver && k == KeyEvent.VK_R) {
            initGame();
            return;
        }

        if (k == KeyEvent.VK_UP    && dy != 1)  { dy = -1; dx = 0; }
        if (k == KeyEvent.VK_DOWN  && dy != -1) { dy = 1;  dx = 0; }
        if (k == KeyEvent.VK_LEFT  && dx != 1)  { dy = 0;  dx = -1; }
        if (k == KeyEvent.VK_RIGHT && dx != -1) { dy = 0;  dx = 1; }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
