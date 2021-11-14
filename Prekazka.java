package autoHra;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Trida reprezentujici prekazku.
 */
public class Prekazka {
    private HerniPanel panel;   // reference na panel
    private Image prekazkaObr;
    private int x;
    private int y;
    private int dy = 1;         // smer na ose y (dolu) a rychlost (1px za vykresleni)
    private boolean viditelny;  // prekazka je na panelu true, mimo panel false
    private Random generator;   // generator nahodnych cisel
    
    /**
     * Konstruktor
     */
    public Prekazka(HerniPanel panel) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("prekazka.png"));
        prekazkaObr = ii.getImage();
        
        this.generator = new Random();  // vytvoreni generatoru nahodnych cisel
        this.panel = panel;             // reference na panel
        this.x = generator.nextInt(panel.getWidth() - prekazkaObr.getWidth(null));
        this.y = -10;   // umisteni prekazky na ose y pri vytvoreni
        
        viditelny = true;
    }
    
    /**
     * Vykresli obrazek na aktualni souradnice
     */
    public void vykresliSe(Graphics g) {
        
        g.drawImage(prekazkaObr, x, y, null);
    }
    
    /**
     * Zmena y-ove souradnice. Pokud dojede na konec panelu, zmení se hodnota
     * viditelny na false.
     */
    public void provedPohyb() {
        y += dy;
        if (y > panel.getHeight()) {
            viditelny = false;
        }
    }
    
    /**
     * Vraci obrys obrazku ve forme obdelniku.
     */
    public Rectangle getOkreje() {
        Rectangle r = new Rectangle(x, y, prekazkaObr.getWidth(panel), prekazkaObr.getHeight(panel));
        return r;
    }
   
    /**
     * Vraci, zda je objekt visible ci nikoli.
     */
    public boolean isViditelny() {
        return viditelny;
    }  
}
