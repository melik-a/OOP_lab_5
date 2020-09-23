import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;

import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class FractalExplorer {
    private int screenSize;
    
    private FractalGenerator fracGen;
    private Double complRange;
   
    private JFrame window;
    private JButton button;
    private JImageDisplay display;

    FractalExplorer(int size){
        this.screenSize = size;
        this.fracGen = new Mandelbrot();
        this.complRange = new Double();
        fracGen.getInitialRange(complRange);
    }

    public void createAndShowGUI(){
        this.window = new JFrame("Mandelbrot fractal");
        this.window.setSize(screenSize, screenSize);
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.display = new JImageDisplay(this.screenSize, this.screenSize);
        window.add(this.display,BorderLayout.CENTER);
        display.addMouseListener(new mouseClicksListener());
        
        this.button = new JButton("Reset");
        window.add(this.button,BorderLayout.SOUTH);
        button.addActionListener(new resetButtonListener());

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
    public static void main(String[] args) {
        FractalExplorer mandelbrotFrac = new FractalExplorer(400);
        mandelbrotFrac.createAndShowGUI();
        mandelbrotFrac.drawFractal();
    }
}
