package ifpr.haperlin.comecome.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controlador {

    private Lock lock;
    private ArrayList<Jogador> jogadores;
    private ArrayList<Comida> comidas;
    private Pane mundo=null;

    private static Controlador instance = new Controlador();

    private Controlador(){
        jogadores = new ArrayList<>(0);
        comidas = new ArrayList<>();
        lock = new ReentrantLock();
    }

    public void setMundo(Pane mundo){
        this.mundo = mundo;
    }

    public List<Comida> getComidas(){
        return comidas;
    }

    public ArrayList<Jogador> getJogadores(){
        return jogadores;
    }


    public void removeJogador(Jogador j){
        lock.lock();
        jogadores.remove(j);
        lock.unlock();
    }

    public void adicionaJogador(Jogador j){
        lock.lock();
        jogadores.add(j);
        lock.unlock();
    }

    public void removeComida(Comida c){
        comidas.remove(c);
    }

    public void adicionaComida(Comida c){
        comidas.add(c);
    }





}
