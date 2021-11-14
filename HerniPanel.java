package autoHra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Trida predstavujici herni panel. Dedi z JPanelu a implementuje rozhrani
 * pro naslouchani udalostem ActionEvent
 */
public class HerniPanel extends JPanel implements ActionListener {
    private int sirkaPanelu = 400;  // preferovana sirka panelu
    private int vyskaPanelu = 650;  // preferovana vyska panelu
    private Timer casovac;    
    private Auto auto;
    private boolean hrajeSe;    // true kdyz se hraje, false kdyz hra skoncila
    private int citac;          // pocita body, cim dele se hraje, tim vice
    private int prodlevaMeziPrekazkami; // jaka je prodleva, nez se prida dalsi prekazka
    private List<Prekazka> prekazky;    // seznam prekazek
    
    /**
     * Konstruktor HernihoPanelu.
     */
    public HerniPanel() {
        init();
        start();   
    }
    
    /**
     * Slouzi k inicializaci HernihoPanelu
     */
    private void init() {
        this.setPreferredSize(new Dimension(sirkaPanelu, vyskaPanelu));
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        this.setFocusable(true);
    }
    
    /**
     * Provede inicializaci promennych nutnych ke hre a spusti timer.
     */
    private void start() {
        hrajeSe = true;
        auto = new Auto(this);
        citac = 0;
        prodlevaMeziPrekazkami = 100;
        prekazky = new ArrayList<Prekazka>();
        this.addKeyListener(auto);
        
        casovac = new Timer(10, this);
        casovac.start();
    }
    
    /**
     * Zde definujeme, co se ma dit pri vykreslovani HernihoPanelu.
     */
    public void paintComponent(Graphics g) {
        // pokud se hraje
        if (hrajeSe) {
            super.paintComponent(g);
            
            vypisCitac(g); 
            auto.vykresliSe(g);
            
            for (int i = 0; i < prekazky.size(); i++) {
                Prekazka prek = prekazky.get(i);
                prek.vykresliSe(g);
            }
        }
        // pokud hra skoncila
        else {
            vypisKonec(g);
        }
        
    }
    
    /**
     * Vypise na panel stav citace
     */
    private void vypisCitac(Graphics g) {
        g.drawString(String.valueOf(citac), 10, 30);
    }
    
    /**
     * V pripade ukonceni hry vypise zaverecny text
     */
    private void vypisKonec(Graphics g) {
        String textKonec = "G A M E   O V E R"; // text, ktery se vypise pri skonceni hry
        Font pismo = new Font(Font.MONOSPACED, Font.BOLD, 28);
        g.setFont(pismo);
        FontMetrics fm = g.getFontMetrics(pismo);
        int sirkaTextu = fm.stringWidth(textKonec);
        
        g.setColor(Color.red);
        g.drawString(textKonec, (this.getWidth() - sirkaTextu) / 2, this.getHeight() / 2);
        
    }
    
    /**
     * Definuje, co se ma stat pri vzniku udalosti ActionEvent, kterou generuje Timer.
     */
    public void actionPerformed(ActionEvent ae) {
        
        citac++;        // zvysi citac o jednicku
        
        auto.provedPohyb();       
        pridejPrekazku();
        pohniPrekazkami();
        
        if (isSrazka()) {
            hrajeSe = false;
            casovac.stop();
        }
        
        odstranPrekazkyKtereJsouMimo();
          
        this.repaint(); // metoda, ktera prekresli panel 
    }
    
    /**
     * Odstrani prekazky, ktere jiz nejsou viditelne.
     */
    private void odstranPrekazkyKtereJsouMimo() {
        for (int i = 0; i < prekazky.size(); i++) {
            Prekazka prek = prekazky.get(i);
            if (prek.isViditelny() == false) {
                prekazky.remove(prek);
            }
        }
    }
    
    /**
     * Zjisti, zda doslo ke srazce.
     */
    private boolean isSrazka() {
        for (int i = 0; i < prekazky.size(); i++) {
            Prekazka prek = prekazky.get(i);
            if (auto.getOkraje().intersects(prek.getOkreje())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Provede pohyb vsech prekazek, ktere jsou v seznamu prekazky.
     */
    private void pohniPrekazkami() {
        for (int i = 0; i < prekazky.size(); i++) {
            Prekazka prek = prekazky.get(i);
            prek.provedPohyb();
        }
    }
    
    /**
     * Zkontroluj prodlevu a v pripade, ze je cas, tak prida dalsi prekazku.
     * S pokrocilosti hry se prodleva zmensuje a prekazek pribyva.
     */
    private void pridejPrekazku() {
        if (citac == 1) {
            System.out.println("VITEJ VE HRE, KDE SE NAUCIS RIDIT AUTO LEPE, NEZ V AUTOSKOLE! PREJI HODNE STESTI!");
        }
        if (citac == 1500) {
            hrajeSe = false;
            System.out.println("GRATULUJI, USPESNE JSI ABSOLVOVAL TUTORIAL, JEN TAK DAL");
            start();
            citac = 1501;
            prodlevaMeziPrekazkami = 80;
        }
        if (citac == 5000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 1, STARTUJE LEVEL 2");
            start();
            citac = 5001;
            prodlevaMeziPrekazkami = 75;
        }
        if (citac == 10000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 2, STARTUJE LEVEL 3");
            start();
            citac = 10001;
            prodlevaMeziPrekazkami = 70;
        }
        if (citac == 17500) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 3, STARTUJE LEVEL 4");
            start();
            citac = 17501;
            prodlevaMeziPrekazkami = 65;
        }
        if (citac == 25000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 4, STARTUJE LEVEL 5");
            start();
            citac = 25001;
            prodlevaMeziPrekazkami = 60;
        }
        if (citac == 35000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 5, STARTUJE LEVEL 6");
            start();
            citac = 35001;
            prodlevaMeziPrekazkami = 55;
        }
        if (citac == 50000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 6, STARTUJE LEVEL 7");
            start();
            citac = 50001;
            prodlevaMeziPrekazkami = 50;
        }
        if (citac == 75000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 7, STARTUJE LEVEL 8");
            start();
            citac = 75001;
            prodlevaMeziPrekazkami = 45;
        }
        if (citac == 100000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 8, STARTUJE LEVEL 9");
            start();
            citac = 100001;
            prodlevaMeziPrekazkami = 40;
        }
        if (citac == 150000) {
            hrajeSe = false;
            System.out.println("USPESNE JSI SPLNIL LEVEL 9, STARTUJE POSLEDNI LEVEL!");
            start();
            citac = 150001;
            prodlevaMeziPrekazkami = 35;
        }
        if (citac == 200000) {
            hrajeSe = false;
            System.out.println("VYHRAL JSI CELOU HRU!");
        }
        
        // pokud je zbytek po deleni 0, prida dalsi prekazku
        if ((citac % prodlevaMeziPrekazkami) == 0) {
            Prekazka prek = new Prekazka(this);
            prekazky.add(prek);
        }
    }   
}
