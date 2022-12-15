import javax.management.timer.Timer;
import javax.swing.*;

import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.*;



public class App extends TimerTask implements Action{

    JFrame frame;
    JPanel panel;
    JLabel l1, l2;
    JTextField input;
    JButton b1;
    int pressCount = 0;
    double total = 0;
    boolean isRunning = false;
    private Thread t1;
    double secondRate;
    final int MAX_TIME = 604800; //a week of seconds


    public void addInterface() {

        frame = new JFrame("Time Is Money");

        panel = new JPanel();
        panel.setBounds(0, 0, 300, 200);
        panel.setBackground(Color.black);

        l1 = new JLabel("Enter hourly rate: ");
        l1.setForeground(Color.white);
        panel.add(l1);

        input = new JTextField();
        input.setEditable(true);
        input.setPreferredSize(new DimensionUIResource(70, 20));
        panel.add(input);

        b1 = new JButton("start");
        b1.addActionListener(this);
        panel.add(b1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(290, 200);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(panel);

        //frame.pack();
       
    }

    public synchronized void countBalance() throws InterruptedException{

            //Clock clock = Clock.systemDefaultZone();

            // double hourlyRate = Double.parseDouble(input.getText());
            // secondRate = hourlyRate/3600;
            // JLabel l2 = new JLabel("$ 0");
            
            isRunning = true;
            this.t1 = new Thread(this, "output");
            this.t1.start();
            
            // panel.add(test);
            //updateLabel(secondRate, l2);

            panel.revalidate();
            panel.repaint();
    }

    @Override
    public void run() {

        double hourlyRate = Double.parseDouble(input.getText());
        secondRate = hourlyRate/(3600*60);
        JLabel l2 = new JLabel("$ 0");
        panel.add(l2);

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0/60; //16,666ns or .016666s
        double delta = 0;
        int fps = 0;

        while(isRunning) {
            long curTime = System.nanoTime();
            delta += (curTime - lastTime)/ns;
            lastTime = curTime;
            while(delta >= 1) {
                updateLabel(secondRate, l2);
                delta--;
            }
            fps++;

            if(System.currentTimeMillis() - timer >1000) { //how often the fps will update
                timer+= 1000;
                //this.frame.setTitle(" "+ fps + "fps");
                fps=0;
            }


        }

        //stopEngine();
    }

    public void updateLabel(double rate, JLabel label){
            
            
            total = total + rate;
            label.setText("$ "+String.format("%.2f", total));
            label.setFont(new FontUIResource("Serif", Font.BOLD, 32));
            label.setForeground(Color.green);
            //panel.add(label);   
            panel.revalidate();
            panel.repaint();
            
        
    }

    
    public static void main(String[] args) {
        
        App myApp = new App();
        myApp.addInterface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==b1) {
            if(pressCount < 1){
                try {
                    countBalance();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                pressCount++;
            }
        }
        
    }

    @Override
    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void setEnabled(boolean b) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        
    }

    // @Override
    // public void run() {
    //     // TODO Auto-generated method stub
        
    // } 
}
