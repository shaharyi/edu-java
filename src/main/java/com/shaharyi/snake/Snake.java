package com.shaharyi.snake;

import com.shaharyi.node.Node;


public class Snake {

    private Node<Point> head;

    public Snake(Point start) {
        head = new Node<>(start);
    }

    public Node<Point> getHead() {
        return head;
    }

    public Point getHeadPos() {
        return head.getValue();
    }

    public boolean contains(Point p) {
        Node<Point> curr = head;
        while (curr != null) {
            if (curr.getValue().equals(p)) return true;
            curr = curr.getNext();
        }
        return false;
    }

    public void moveTo(Point newHeadPos, boolean grow) {     
        // prepend to head in 1 line:
        //head = new Node<>(newHeadPos, head);
        
        // prepend to head in 3 lines:
        Node<Point> newHead = new Node<>(newHeadPos);
        newHead.setNext(head);
        head = newHead;
        
        if (!grow) {
            removeTail();
        }
    }

    private void removeTail() {
        if (head == null || head.getNext() == null) return;

        Node<Point> curr = head;
        while (curr.getNext().getNext() != null) {
            curr = curr.getNext();
        }
        curr.setNext(null);
    }
    
    public void printState() {
        System.out.println("Snake: " + head);
    }    
}

