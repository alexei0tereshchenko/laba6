package Frame;

import Ball.BouncingBall;
import Brick.SolidBrick;

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

    private boolean paused;

    private BouncingBall ball;

    private ArrayList<SolidBrick> bricks = new ArrayList<SolidBrick>(10);

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
        ball = new BouncingBall(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        ball.paint(canvas);
        for(SolidBrick brick: bricks)
        {
            brick.paint(canvas);
        }
    }

    public void addBrick(){
        bricks.add(new SolidBrick(this));
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {
        if (paused) {
            wait();
        }
    }
}