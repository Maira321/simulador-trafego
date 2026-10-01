package multithread;

import javax.swing.JFrame;

public class Simulador {
    public static void main(String[] args) {
        
        JFrame janela = new JFrame("Simulador de Tráfego 2D");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(800, 600); 
        
        Mapa mapa = new Mapa();
        janela.add(mapa);
        
        janela.setLocationRelativeTo(null); 
        janela.setVisible(true); 
    }
}
