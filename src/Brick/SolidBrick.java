package Brick;
import Frame.Field;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SolidBrick implements Runnable {
    private static boolean[][] matrix = new boolean[12][16];
    private static int HEIGHT;
    private static int WIDTH;
    private int iNumber;
    private int jNumber;
    private int x;
    private int y;
    private static final Color color = Color.orange;
    private static final int MAX_STRENGTH = 5;
    private int strength;

    public SolidBrick(Field field){
        WIDTH = field.getWidth()/16;
        HEIGHT = field.getHeight()/12;

        //занятие уникального места
        while(true){
            boolean p = true;
            for (int i=0; i<12; i++)
                for(int j=0; j<16; j++){
                if (!matrix[i][j]) p = false;
                }
            if(p) break;

            iNumber = (int) Math.random()*12;
            jNumber = (int) Math.random()*16;
            x = WIDTH/2 + jNumber;
            y = HEIGHT/2 + iNumber;
            if (!matrix[iNumber][jNumber]){
                matrix[iNumber][jNumber] = true;
                break;
            }
        }

        strength = (int) Math.random()*MAX_STRENGTH;

        Thread thisThread = new Thread(this);
        thisThread.start();
    }





    @Override
    public void run() {
        try {

        }
        catch (InterruptedException ex){

        }
//TODO: столкновения с шарами
    }

    public void paint(Graphics2D canvas){
        canvas.setPaint(color);
        canvas.setColor(color);
        Rectangle2D.Double brick = new Rectangle2D.Double(x-WIDTH/2, y-HEIGHT/2, WIDTH, HEIGHT);
        canvas.draw(brick);
        canvas.fill(brick);
    }
}
