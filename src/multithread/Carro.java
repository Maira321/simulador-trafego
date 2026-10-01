package multithread;

import java.util.concurrent.Semaphore;

public class Carro extends Thread {
    private int x;
    private int y;
    private int velocidade;
    private int direcao;
    private int intencao;
    private boolean rodando = true;
    private Semaphore semaforoCruzamento;
    private boolean noCruzamento = false;
    private Mapa mapa;

    public Carro(int x, int y, int velocidade, int direcao, int intencao, Semaphore semaforo, Mapa mapa) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.direcao = direcao;
        this.intencao = intencao;
        this.semaforoCruzamento = semaforo;
        this.mapa = mapa;
    }

    private boolean temCarroNaFrente() {
        for (Carro outro : mapa.getCarros()) {
            if (outro == this) continue; 

            if (this.direcao == 1 && outro.getY() == this.y) {
                if (outro.getX() > this.x && (outro.getX() - this.x) < 40) {
                    return true;
                }
            } else if (this.direcao == 2 && outro.getX() == this.x) {

                if (outro.getY() > this.y && (outro.getY() - this.y) < 40) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (rodando) {
            if (direcao == 1) { 
                if ((x >= 280 && x <= 310 && mapa.getLuzHorizontal() != 2) || temCarroNaFrente()) {
 
                } else {
                    x += velocidade;
                    if (x > 800) x = -40;

                    if (x >= 320 && x <= 460 && !noCruzamento) {
                        entrarNoCruzamento();
                    }

                    if (intencao == 1 && x >= 400 && x <= 410) {
                        x = 410;
                        direcao = 2; 
                        intencao = 0;
                    } else if (x > 460 && noCruzamento) {
                        sairDoCruzamento();
                    }
                }

            } else if (direcao == 2) { 
                if ((y >= 180 && y <= 210 && mapa.getLuzVertical() != 2) || temCarroNaFrente()) {
                } else {
                    y += velocidade;
                    if (y > 600) y = -40;

                    if (y >= 220 && y <= 360 && !noCruzamento) {
                        entrarNoCruzamento();
                    }

                    if (intencao == 1 && y >= 300 && y <= 310) {
                        y = 310;
                        direcao = 1;
                        intencao = 0;
                    } else if (y > 360 && noCruzamento) {
                        sairDoCruzamento();
                    }
                }
            }

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void entrarNoCruzamento() {
        try {
            semaforoCruzamento.acquire();
            noCruzamento = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sairDoCruzamento() {
        semaforoCruzamento.release();
        noCruzamento = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}