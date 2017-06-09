import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Main {

    public static Simulator simulator;

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1250, 800);
        simulator = new Simulator(frame);

        frame.add(simulator, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.addKeyListener(new listen());
        frame.addMouseListener(new mouse());

        /*SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                simulator = new Simulator(500,400);

                frame.add(simulator, BorderLayout.CENTER);

                simulator.repaint();
                frame.setSize(1000, 1000);
                frame.setVisible(true);
                frame.addKeyListener(new listen());
            }
        });*/
    }

    public static class listen implements KeyListener{
        public void keyTyped(KeyEvent e){
        }

        public void keyPressed(KeyEvent e){
        }

        public void keyReleased(KeyEvent e){
            simulator.next();
            System.out.println(e.getKeyCode());
        }
    }

    public static class mouse implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clicked");
            simulator.info(e.getX(),e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //simulator.hover(e.getX(),e.getY());
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
