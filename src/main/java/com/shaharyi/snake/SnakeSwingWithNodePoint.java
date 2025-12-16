package com.shaharyi.snake;

import javax.swing.*;

public class SnakeSwingWithNodePoint {

    public static void main(String[] args) {
        JFrame f = new JFrame("Snake - Swing + Node<Point>");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new SnakePanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

