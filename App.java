import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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

        ImageIcon img = new ImageIcon("image.png");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setIconImage(img.getImage());
        frame.setSize(300, 150);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.getContentPane().add(panel);
       
    }

    public synchronized void countBalance() throws InterruptedException{

            l1.setVisible(false); 
            input.setVisible(false);
            b1.setVisible(false);
            frame.setResizable(true);

            isRunning = true;
            this.t1 = new Thread(this, "output");
            this.t1.start();

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
        final double ns = 1000000000.0/60; //0.016666s
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
                //this.frame.setTitle(" "+ fps + " fps"); //
                fps=0;
            }

        }

    }

    public void updateLabel(double rate, JLabel label){
            
            
            total = total + rate;
            label.setText("$ "+String.format("%.2f", total));
            label.setFont(new FontUIResource("Serif", Font.BOLD, 32));
            label.setForeground(Color.green);

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

}
