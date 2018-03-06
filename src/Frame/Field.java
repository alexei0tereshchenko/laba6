package Frame;

import Ball.BouncingBall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Field extends JPanel {
    static private int friction;

    public int getFriction() {
        return friction;
    }

    public void setFriction(int friction) {
        Field.friction = friction;
    }

    private boolean paused;

    private ArrayList<BouncingBall> balls = new ArrayList<>(10);


    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for(BouncingBall ball:balls)
            ball.paint(canvas);
    }


    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }
    public void addBall(){
        balls.add(new BouncingBall(this));
    }

    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {
        if (paused) {
            wait();
        }
    }
}