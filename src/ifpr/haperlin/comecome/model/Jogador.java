package ifpr.haperlin.comecome.model;

import ifpr.haperlin.comecome.utils.Move;
import ifpr.haperlin.comecome.utils.NomeInvalido;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Jogador extends Circle{



    private String nome;
    private Move direction = Move.None;
    private boolean vivo = true;


    public Jogador(String nome, double x, double y, double tamanho, Color cor) throws NomeInvalido {
        if(nome.length() > 3){
            throw new NomeInvalido();
        }

        this.nome = nome;
        this.setRadius(tamanho);
        setTranslateX(x);
        setTranslateY(y);
        this.setFill(cor);


    }

    public void comeu(Comida comida){
        if(comida.isEnvenenada()){
            vivo = false;
        }else{
            setRadius(getRadius()+comida.getRadius()*0.1);
        }
    }

    public boolean estaVivo(){
        return vivo;
    }


    public void setMove(Move direction){
        this.direction = direction;
    }

    public void doMove(double speed){
        switch (this.direction){
            case Left:
                moveLeft(speed);
                break;
            case Rigth:
                moveRight(speed);
                break;
            case Up:
                moveUp(speed);
                break;
            case Down:
                moveDown(speed);
                break;
        }
        //this.direction = Move.None;
    }

    void moveLeft(double speed) {
        setTranslateX(getTranslateX() - 5*speed);
    }

    void moveRight(double speed) {
        setTranslateX(getTranslateX() + 5*speed);
    }

    void moveUp(double speed) {
        setTranslateY(getTranslateY() - 5*speed);
    }

    void moveDown(double speed) {
        setTranslateY(getTranslateY() + 5*speed);
    }



}
