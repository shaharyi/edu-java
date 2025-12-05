package com.shaharyi.snake;

import com.shaharyi.node.Node;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SnakeFX extends Application {

    private static final int CELL = 30;
    private static final int W = 60;
    private static final int H = 40;
    private static final int FONT_HEIGHT = CELL;
    private static final int FONT_WIDTH = (int)(FONT_HEIGHT * 0.6);

    private Snake snake;
    private Point food;
    private int dx = 1, dy = 0;
    private boolean gameOver = false;
    private int apples = 0;

    private Random rand = new Random();

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(W * CELL, H * CELL);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(Font.font("Monospaced", CELL));
        
        resetGame();

        Scene scene = new Scene(new StackPane(canvas));

        scene.setOnKeyPressed(e -> {
            if (gameOver) {
                if (e.getCode() == KeyCode.R) 
                    resetGame();
                if (e.getCode() == KeyCode.ESCAPE) 
                    stage.close();
                return;
            }

            if (e.getCode() == KeyCode.UP && dy != 1) { 
                dx = 0;
                dy = -1; 
            } else if (e.getCode() == KeyCode.DOWN && dy != -1) {
                dx = 0;
                dy = 1; 
            } else if (e.getCode() == KeyCode.LEFT && dx != 1) { 
                dx = -1;
                dy = 0; 
            }
            else if (e.getCode() == KeyCode.RIGHT && dx != -1) {
                dx = 1;
                dy = 0; 
            }
        });

        Timeline loop = new Timeline(new KeyFrame(Duration.millis(120), e -> update(gc)));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

        stage.setScene(scene);
        stage.setTitle("Snake (Linked List + JavaFX)");
        stage.show();
    }

    private void resetGame() {
        snake = new Snake(new Point(W / 2, H / 2));
        food = randomFood();
        dx = 1; dy = 0;
        apples = 0;
        gameOver = false;
    }

    private Point randomFood() {
        Point p;
        do {
            p = new Point(rand.nextInt(W), rand.nextInt(H));
        } while (snake.contains(p));
        return p;
    }

    private void update(GraphicsContext gc) {
        if (gameOver) {
            drawGameOver(gc);
            return;
        }

        Point head = snake.getHeadPos();
        Point newHead = new Point(head.x + dx, head.y + dy);

        if (newHead.x < 0 || newHead.x >= W ||
            newHead.y < 0 || newHead.y >= H ||
            snake.contains(newHead)) {
            gameOver = true;
            drawGameOver(gc);
            return;
        }

        boolean ate = newHead.equals(food);
        snake.moveTo(newHead, ate);
        snake.printState();

        if (ate) {
            apples++;
            food = randomFood();
        }

        draw(gc);
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W * CELL, H * CELL);

        // Draw food
        gc.setFill(Color.RED);
        gc.fillRect(food.x * CELL, food.y * CELL, CELL, CELL);

        // Draw snake manually traversing linked list
        gc.setFill(Color.LIMEGREEN);
        Node<Point> curr = snake.getHead();
        while (curr != null) {
            Point p = curr.getValue();
            gc.fillRect(p.x * CELL, p.y * CELL, CELL, CELL);
            curr = curr.getNext();
        }

        // Draw score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + apples, 10, CELL);
    }

    private void drawGameOver(GraphicsContext gc) {
        String msg;
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W * CELL, H * CELL);

        gc.setFill(Color.WHITE);
        msg = "GAME OVER";
        gc.fillText(msg, W * CELL / 2 - msg.length()/2 * FONT_WIDTH, H * CELL / 2 - 2*FONT_HEIGHT);
        msg = "Score:  " + apples;
        gc.fillText(msg, W * CELL / 2 - msg.length()/2 * FONT_WIDTH, H * CELL / 2);
        msg = "Press R to restart, ESC to exit";
        gc.fillText(msg, W * CELL / 2 - msg.length()/2 * FONT_WIDTH, H * CELL / 2 + 2*FONT_HEIGHT);
    }

    public static void main(String[] args) {
        launch();
    }
}

