import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Simulator extends JPanel implements ActionListener {

    Universe world;
    ArrayList<Shape> images;
    int initWidth;
    int initHeight;
    boolean sideBar;
    Body tagged;

    public Simulator(JFrame dimensions) {
        setBackground(Color.BLACK);
        setFocusable(true);

        sideBar = false;
        initWidth = dimensions.getWidth() / 2;
        initHeight = dimensions.getHeight() / 2;

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
            String image = bodyParts.get(bodyParts.size()-1);
            for(int i = 1; i < bodyParts.size()-1; i++){
                Double temp = Double.parseDouble(bodyParts.get(i));
                stats.add(temp);
            }

            planet = new Body(name, stats.get(0), stats.get(1), stats.get(2), stats.get(3), image);
            planets.add(planet);
            radius = 2*stats.get(2);
        }

        world = new Universe(radius, planets);

        Timer timer = new Timer(1000/60, this);
        timer.start();
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
            //temp.updateAccelFrom(world.getBodies().get(0));
            temp.move(75000, world.getBodies().get(0));
        }
        repaint();
    }

    public void paintComponent(Graphics g){
        int radius;

        super.paintComponent(g);

        images = new ArrayList<>();
        for(int i=0; i < world.getBodies().size(); i++){
            Body temp = world.getBodies().get(i);

            radius = temp.getScaledRadius();


            Rectangle shape = new Rectangle(initWidth - radius + temp.getScaledX(), initHeight - radius + temp.getScaledY(), radius * 2 +20, radius * 2 +20);
            //g.setColor(Color.DARK_GRAY);
            //g.drawRect((int)shape.getX(),(int) shape.getY(), radius * 2, radius * 2);
            images.add(shape);
            try{
                BufferedImage img = ImageIO.read(new File(temp.getImage()));
                g.drawImage(img,(int)shape.getX(),(int) shape.getY(), radius * 2, radius * 2, null);
            } catch(IOException e){
                if(i==0)
                    g.setColor(Color.YELLOW);
                else
                    g.setColor(Color.RED);

                g.fillOval(initWidth - radius + temp.getScaledX(), initHeight - radius + temp.getScaledY(), radius * 2, radius * 2);
            }
        }

        if(sideBar){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, initWidth/2, initHeight*2);

            try{
                BufferedImage img = ImageIO.read(new File(tagged.getImage()));
                g.drawImage(img,initWidth/8,50,initWidth/4,initWidth/4, null);
            } catch(IOException e){

            }

            ArrayList<String> texts = new ArrayList<>();

            DecimalFormat df = new DecimalFormat("#.###");
            texts.add(tagged.getName());
            texts.add("Mass: " + tagged.getMass() + " kg");
            texts.add("Radius: " + tagged.getRadius()/1000 + " km");
            texts.add("Axis: " + tagged.getAxis()/1000 + " km");
            texts.add("Orbital velocity: " + df.format(tagged.getOrbitV()/1000) + " km/s");
            texts.add("Period: " + df.format(tagged.getPeriod()) + " Earth years");

            drawCenteredString(g, texts);
        }
    }

    public void drawCenteredString(Graphics g, ArrayList<String> s){

        Font f = new Font("Serif", Font.BOLD, 50);
        FontMetrics metrics = g.getFontMetrics(f);

        String text;
        int x;
        int y = 300;
        for(int i = 0; i < s.size(); i++){
            text = s.get(i);

            // Determine the X coordinate for the text
            x = (initWidth/2 - metrics.stringWidth(text)) / 2;


            // Set the font
            g.setFont(f);

            g.setColor(Color.black);
            // Draw the String
            g.drawString(text, x, y);


            y += metrics.getAscent() + 30;

            //Change fonts for the non title text
            f = new Font("Serif", Font.PLAIN, 25);
            metrics = g.getFontMetrics(f);
        }


    }

    public void paintSidebar(Graphics g, Body b){
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        //g.fillRect(initWidth/2 + initWidth, initHeight/2 + initHeight, initWidth /2, initHeight /2);
        g.fillRect(750,0, 400, 800);
    }

    public void info(int x, int y){
        for(int i = 0; i < images.size(); i++){
            System.out.println("seen");
            if(images.get(i).contains(x,y)) {
                sideBar = true;
                tagged = world.getBodies().get(i);
                System.out.println("contained");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
