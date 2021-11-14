package autoHra;

import javax.swing.JFrame;

/**
 * Trida obsahujici hlavni okno programu a metodu main.
 */
public class AutoHra extends JFrame {
    
    /**
     * Konstruktor hlavniho okna programu.
     */
    public AutoHra() {
        this.setTitle("AUTOSKOLA");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        HerniPanel panel = new HerniPanel();
        this.add(panel);     
        
        this.setResizable(false);
        this.pack();
    }
    
     /**
      * Vstupni bod programu. 
      * Vytvori hlavni okno s programem.
      */
    public static void main(String[] args) {
        AutoHra hlavniOkno = new AutoHra();
        hlavniOkno.setVisible(true);
    }
}
