import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by emiliacabrera on 1/19/17.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                Simulator simulator = new Simulator(500,400);

                frame.add(simulator, BorderLayout.CENTER);

                simulator.repaint();
                frame.setSize(1000, 1000);
                frame.setVisible(true);
            }
        });
    }
}
