package Frame;

import Ball.BouncingBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Field extends JPanel {
    private ArrayList<BouncingBall> balls;
    private boolean paused;

    public Field(){
        setBackground(Color.white);
        repaintTimer.start();
    }
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Задача обработчика события ActionEvent одна - перерисовка окна
            repaint();
        }
    });

    public void addBall() {
        balls.add(new BouncingBall(this));
    }

    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
// Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
// Последовательно запросить прорисовку от всех мячей, хранимых в списке
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }
}