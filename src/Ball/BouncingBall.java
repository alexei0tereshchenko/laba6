package Ball;

import Frame.Field;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
public class BouncingBall implements Runnable {
    private static final int MAX_RADIUS = 40;
    private static final int MIN_RADIUS = 3;
    private static final int MAX_SPEED = 10;
    private static Field field;
    private int radius;
    private Color color;
    private double x;
    private double y;
    private int speed;
    private double speedX;
    private double speedY;
    private int cost;
    public BouncingBall(Field field) {
        this.field = field;
        radius = new Double(Math.random()*(MAX_RADIUS -
                MIN_RADIUS)).intValue() + MIN_RADIUS;

        speed = new Double(Math.round(5*MAX_SPEED / radius)).intValue();
        if (speed>MAX_SPEED) {
            speed = MAX_SPEED;
        }

        double angle = Math.random()*2*Math.PI;

        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);
        color = new Color((float)Math.random(), (float)Math.random(),
                (float)Math.random());

        x = Math.random()*(field.getSize().getWidth()-2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight()-2*radius) + radius;

        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    private void recalculateSpeed(double speed){
        if(speed>0) {
            speed-=field.getFriction()/radius;
            if (speed<0) speed=0;
        }
        else if(speed<0){
            speed+=field.getFriction()/radius;
            if (speed>0) speed=0;
        }
        else speed=0;
    }
    public void run() {
        try {
            while(true) {
                field.canMove(this);
                speed-=field.getFriction()/radius;
                if (speed < 0) {
                    speed = 0;
                    break;
                }
                recalculateSpeed(speedY);
                recalculateSpeed(speedX);
                if (x + speedX <= radius) {
                    // Достигли левой стенки, отскакиваем вправо
                    speedX = -speedX;
                    x = radius;
                } else
                if (x + speedX >= field.getWidth() - radius) {
                    // Достигли правой стенки, отскок влево
                    speedX = -speedX;
                    x=new Double(field.getWidth()-radius).intValue();
                } else
                if (y + speedY <= radius) {
                    // Достигли верхней стенки
                    speedY = -speedY;
                    y = radius;
                } else
                if (y + speedY >= field.getHeight() - radius) {
                    // Достигли нижней стенки
                    speedY = -speedY;
                    y=new Double(field.getHeight()-radius).intValue();
                } else {
                    // Просто смещаемся
                    x += speedX;
                    y += speedY;
                }
                if(field.getFriction()!=0&&speed!=0) {
                    Thread.sleep((field.getFriction() + cost)/(speed*radius));
                    cost += speed;
                }
                /* Засыпаем на X миллисекунд, где X определяется
                исходя из скорости
                Скорость = 1 (медленно), засыпаем на 15 мс.
                Скорость = 15 (быстро), засыпаем на 1 мс.*/
                Thread.sleep(16-speed);
            }
        } catch (InterruptedException ex) {

        }
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius,
                2*radius, 2*radius);

        canvas.draw(ball);
        canvas.fill(ball);
    }
}