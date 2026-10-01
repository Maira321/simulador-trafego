package multithread;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class Mapa extends JPanel {
    private List<Carro> carros;
    private Random random;
    
    private int luzHorizontal = 2;
    private int luzVertical = 0;

    public Mapa() {
        carros = new CopyOnWriteArrayList<>();
        random = new Random();
        Semaphore semaforoCruzamento = new Semaphore(1); 

        Thread controleSemaforos = new Thread(() -> {
            while (true) {
                try {
                    luzHorizontal = 2; luzVertical = 0;
                    Thread.sleep(4000); 
                    
                    luzHorizontal = 1;
                    Thread.sleep(1500); 
                    
                    luzHorizontal = 0; luzVertical = 2; 
                    Thread.sleep(4000);
                    
                    luzVertical = 1; 
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        controleSemaforos.start();

        
        Thread geradorDeTrafego = new Thread(() -> {
            while (true) {
                int direcao = random.nextInt(2) + 1; 
                int velocidade = random.nextInt(5) + 6; 
                int intencao = random.nextInt(2); 
                int faixa = random.nextInt(2);    
                
                Carro novoCarro;
                if (direcao == 1) { 
                    int posicaoY = (faixa == 0) ? 260 : 310;
                    novoCarro = new Carro(-40, posicaoY, velocidade, direcao, intencao, semaforoCruzamento, Mapa.this);
                } else { 
                    int posicaoX = (faixa == 0) ? 360 : 410;
                    novoCarro = new Carro(posicaoX, -40, velocidade, direcao, intencao, semaforoCruzamento, Mapa.this);
                }
                
                carros.add(novoCarro);
                novoCarro.start(); 

                try {
                    Thread.sleep(random.nextInt(2000) + 1000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        geradorDeTrafego.start();

        Timer timer = new Timer(16, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
       
        g.setColor(new Color(34, 139, 34));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 250, getWidth(), 100);
        g.fillRect(350, 0, 100, getHeight());

        g.setColor(Color.YELLOW);
        for(int i = 0; i < getWidth(); i += 40) {
            g.fillRect(i, 298, 20, 4); 
        }
        for(int i = 0; i < getHeight(); i += 40) {
            g.fillRect(398, i, 4, 20); 
        }

        for (Carro c : carros) {
            g.setColor(Color.RED);
            g.fillRect(c.getX(), c.getY(), 30, 30); 
        }

        desenharSemaforo(g, 320, 180, luzVertical);   
        desenharSemaforo(g, 280, 360, luzHorizontal); 
    }

    private void desenharSemaforo(Graphics g, int x, int y, int estado) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, 20, 60);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, 20, 60);

        g.setColor(estado == 0 ? Color.RED : Color.DARK_GRAY);
        g.fillOval(x + 4, y + 4, 12, 12);
        
        g.setColor(estado == 1 ? Color.YELLOW : Color.DARK_GRAY);
        g.fillOval(x + 4, y + 24, 12, 12);
        
        g.setColor(estado == 2 ? Color.GREEN : Color.DARK_GRAY);
        g.fillOval(x + 4, y + 44, 12, 12);
    }

    public int getLuzHorizontal() { return luzHorizontal; }
    public int getLuzVertical() { return luzVertical; }
    public List<Carro> getCarros() { return carros; }
}