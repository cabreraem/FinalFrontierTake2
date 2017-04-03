import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Main {

    public static Simulator simulator;

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        simulator = new Simulator(500,400);

        frame.add(simulator, BorderLayout.CENTER);

        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.addKeyListener(new listen());

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
            simulator.next(e.getKeyCode());
            System.out.println(e.getKeyCode());
        }
    }
}
