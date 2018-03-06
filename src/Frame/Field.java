package Frame;

import Ball.BouncingBall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Field extends JPanel {
    private static boolean[][] brickField = new boolean[12][16];

    private boolean paused;

    private BouncingBall ball;


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
        for (int i=0; i<12; i++)
            for (int j=0; j<16; j++)
                brickField[i][j]=false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        ball.paint(canvas);
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