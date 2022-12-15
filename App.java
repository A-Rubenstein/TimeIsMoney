import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.Color;
import java.util.*;


public class App {

    public void addInterface() {

        JFrame frame = new JFrame("Time Is Money");

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 300, 200);
        panel.setBackground(Color.black);

        JLabel l1 = new JLabel("Enter hourly rate: ");
        l1.setForeground(Color.white);
        panel.add(l1);

        JTextField input = new JTextField();
        input.setEditable(true);
        input.setPreferredSize(new DimensionUIResource(100, 20));
        panel.add(input);

        JButton b1 = new JButton("start");
        panel.add(b1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(panel);

        //frame.pack();
       
    }

    
    public static void main(String[] args) {
        
        App myApp = new App();
        myApp.addInterface();
    } 
}