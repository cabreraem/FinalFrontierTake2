import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Simulator extends JPanel implements ActionListener{

    Universe world;
    int initWidth;
    int initHeight;


    public Simulator(int x, int y) {
        setBackground(Color.BLACK);

        setFocusable(true);

        initWidth = x;
        initHeight = y;

        /*Timer timer = new Timer(1000 / 60, this);
        timer.start();*/

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

            radius = 2*stats.get(2);
        }

        world = new Universe(radius, planets);

    }

    public void actionPerformed(ActionEvent e){
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
        /*for(int i=1; i < world.getBodies().size(); i++){
            Body temp = world.getBodies().get(i);
            temp.force(world.getBodies().get(0));
            temp.move(86400);
        }*/
        repaint();
    }

    public void paintComponent(Graphics g){
        int radius;

        super.paintComponent(g);

        for(int i=0; i < world.getBodies().size(); i++){
            Body temp = world.getBodies().get(i);
            System.out.println(temp.getName() +"'s PosX "+ Math.cbrt(temp.getPosX())/3.0);
            System.out.println(temp.getName() +"'s PosY "+ (int)(initHeight+Math.cbrt(temp.getPosY())/3));

            radius = (int) Math.sqrt(temp.getRadius())/10;
            System.out.println(radius);

            if(i==0)
                g.setColor(Color.YELLOW);
            else
                g.setColor(Color.RED);

            g.fillOval((int)(initWidth+Math.cbrt(temp.getPosX())/3), (int)(initHeight+Math.cbrt(temp.getPosY())/3), radius, radius);
        }

    }

}