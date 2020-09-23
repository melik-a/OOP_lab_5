import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class FractalExplorer {
    private int screenSize;
    
    private ArrayList<FractalGenerator> fracGen;
    private Double complRange;
   
    private JFrame window;
    private JButton resetButton;
    private JButton saveButton;
    private JImageDisplay display;
    private JComboBox combo;
    private JLabel descr;
    private JPanel northPanel;
    private JPanel southPanel;

    FractalExplorer(int size){
        this.screenSize = size;
        this.fracGen = new ArrayList<FractalGenerator>();
        fracGen.add(new Mandelbrot());
        fracGen.add(new Tricorn());
        fracGen.add(new BurningShip());
        this.complRange = new Double();
    }

    public void createAndShowGUI(){
        this.window = new JFrame("Fractals");
        this.window.setSize(screenSize, screenSize);
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.display = new JImageDisplay(this.screenSize, this.screenSize);
        window.add(this.display,BorderLayout.CENTER);
        display.addMouseListener(new mouseClicksListener());
        
        this.descr = new JLabel("Select a fractal: ");
        //window.add(this.display,BorderLayout.NORTH);
        this.combo = new JComboBox();
        for(int i = 0; i < fracGen.size(); i++){
            combo.addItem(fracGen.get(i).toString());
        }
        //combo.addItem(this.fracGen.toString());
        //window.add(this.combo);

        this.northPanel = new JPanel();
        northPanel.add(descr);
        northPanel.add(combo);
        window.add(northPanel, BorderLayout.NORTH);

        this.resetButton = new JButton("Reset");
        this.saveButton = new JButton("Save");
        this.southPanel = new JPanel();
        southPanel.add(resetButton);
        southPanel.add(saveButton);
        window.add(southPanel, BorderLayout.SOUTH);
        //window.add(this.button,BorderLayout.SOUTH);
        resetButton.addActionListener(new resetButtonListener());

        window.pack();
        window.setVisible(true);
        window.setResizable(false);
    }

    public void drawFractal(){
        for(int x = 0; x < this.screenSize; x++)
            for(int y = 0; y < this.screenSize; y++)
            {
                double xCoord = FractalGenerator.getCoord (this.complRange.x, this.complRange.x + this.complRange.width,
                                                            this.display.getWidth(), x);
                double yCoord = FractalGenerator.getCoord (this.complRange.y, this.complRange.y + this.complRange.height,
                                                            this.display.getHeight(), y);
                int numOfIterations = this.fracGen.numIterations(xCoord, yCoord);
                int color = Color.HSBtoRGB(0, 0, 0);
                if (numOfIterations != -1)
                {
                    float hue = 0.7f + (float) numOfIterations / 200f;
                    color = Color.HSBtoRGB(hue, 1f, 1f);
                }
                this.display.drawPixel(x, y, color);
            }
    }

    private class resetButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            fracGen.getInitialRange(complRange);
            FractalExplorer.this.drawFractal();
            //display.repaint();
        }
    }

    private class mouseClicksListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            double xCoord = FractalGenerator.getCoord (complRange.x, complRange.x + complRange.width,
                                                        display.getWidth(), e.getX());
            double yCoord = FractalGenerator.getCoord (complRange.y, complRange.y + complRange.height,
                                                        display.getHeight(), e.getY());

            fracGen.recenterAndZoomRange(complRange, xCoord, yCoord, 0.5);
            FractalExplorer.this.drawFractal();                                    
            //display.repaint();
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
    }

    public class comboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int i = combo.getSelectedIndex();
            if (i >= fracGen.size()){
                return;
            }
            
        }
    }

    public static void main(String[] args) {
        FractalExplorer mandelbrotFrac = new FractalExplorer(400);
        mandelbrotFrac.createAndShowGUI();
        mandelbrotFrac.drawFractal();
    }
}
