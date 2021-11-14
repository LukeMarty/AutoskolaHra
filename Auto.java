package autoHra;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

public class Auto implements KeyListener {
    private HerniPanel panel;   // reference na panel
    private Image autoObr;
    private int x;      // x-ova souradnice auta
    private int y;      // y-ova souradnice auta
    private int dx;     // smer auta po ose x (+ doprava, - doleva)
    
    /**
     * Kontruktor tridy Auto
     */
    public Auto(HerniPanel panel) {
        
        ImageIcon ii = new ImageIcon(this.getClass().getResource("auto.png"));
        autoObr = ii.getImage();
        
        this.panel = panel;
        this.x = 185;   // pocatecni souradnice x
        this.y = 558;   // souradnice y
        this.dx = 0;
    }
    
    /**
     * Vykresli obrazek na aktualni souradnice
     */
    public void vykresliSe(Graphics g) {
        g.drawImage(autoObr, x, y, null);
    }
    
    /**
     * Zmena x-ove souradnice v danem smeru.
     */
    public void provedPohyb() {
        x += dx;
        if (x < 0) {
            x = 0;
        } else if (x > (panel.getWidth() - autoObr.getWidth(null) - 1) && (panel.getWidth() >0)) {
            x = panel.getWidth() - autoObr.getWidth(null) - 1;
        }
    }
    
    /**
     * Vraci obrys obrazku ve forme obdelnika.
     */
    public Rectangle getOkraje() {
        Rectangle r = new Rectangle(x, y, autoObr.getWidth(null), autoObr.getHeight(null));
        return r;
    }
    
    /**
     * Definuje cinnost, ktera se provede po stisku klavesy na klavesnici.
     */
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -1;    // smer doleva, rychlost 2px za vykresleni    
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;     // smer doprava, rychlost 2px za vykresleni
        }
    }
    
    /**
     * Definuje cinnost, ktera se provede po spusteni stisknute klavesy.
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        dx = 0;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    } 
}
