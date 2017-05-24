import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Simulator extends JPanel implements ActionListener {

    Universe world;
    int initWidth;
    int initHeight;
    int start;


    public Simulator(JFrame dimensions) {
        setBackground(Color.BLACK);

        //setFocusable(true);

        initWidth = dimensions.getWidth() / 2;
        initHeight = dimensions.getHeight() / 2;

        //Timer timer = new Timer(1000 / 60, this);
        //timer.start();

        ArrayList<Body> planets = new ArrayList<>();

        Body planet;

        String[] lines;

        try {
            lines = readFile("planets.txt");
        } catch (Exception ignored) {
            lines = new String[0];
        }

        java.util.List<String> bodyParts;
        java.util.List<Double> stats;

        Double radius = 0.0;

        for(String line: lines){
            bodyParts = Arrays.asList(line.split(","));

            stats = new ArrayList();

            String name = bodyParts.get(0);
            for(int i = 1; i < bodyParts.size(); i++){
                Double temp = Double.parseDouble(bodyParts.get(i));
                stats.add(temp);
            }

            planet = new Body(name, stats.get(0), stats.get(1), stats.get(2), stats.get(3));
            planets.add(planet);
            System.out.println(planet);
            radius = 2*stats.get(2);
        }

        world = new Universe(radius, planets);

        //start = 1;
    }

    public void next(){
        update();
    }

    public static int getFileSize(String fileName)throws IOException {
        Scanner input = new Scanner(new FileReader(fileName));
        int size=0;
        while(input.hasNextLine()){
            size++;
            input.nextLine();
        }

        input.close();
        return size;
    }

    public static String[] readFile(String fileName)throws IOException{
        int size = getFileSize(fileName);
        String[] list = new String[size];

        Scanner input = new Scanner(new FileReader(fileName));
        int i = 0;
        String line;
        while(input.hasNextLine()){
            line = input.nextLine();
            list[i] = line;
            i++;
        }

        input.close();
        return list;
    }

    public void update(){
        for(int i=1; i < world.getBodies().size(); i++){
            Body temp = world.getBodies().get(i);
            temp.force(world.getBodies().get(0));
            temp.move(50000);
        }

        repaint();
    }

    public void paintComponent(Graphics g){
        int radius;

        super.paintComponent(g);

        for(int i=0; i < world.getBodies().size(); i++){
            Body temp = world.getBodies().get(i);

            radius = (int) Math.sqrt(temp.getRadius())/300;

            if(i==0)
                g.setColor(Color.YELLOW);
            else
                g.setColor(Color.RED);

            g.fillOval((int) (initWidth - radius + Math.cbrt(temp.getPosX()) /45), (int) (initHeight - radius + Math.cbrt(temp.getPosY())/45), radius * 2, radius * 2);

            //System.out.println(temp.getName() + " xvelocity: " + temp.getVelocityX());
            //System.out.println(temp.getName() + " yvelocity: " + temp.getVelocityY());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
