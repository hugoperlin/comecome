package ifpr.haperlin.comecome;

import ifpr.haperlin.comecome.model.Comida;
import ifpr.haperlin.comecome.model.Jogador;
import ifpr.haperlin.comecome.utils.Move;
import ifpr.haperlin.comecome.utils.NomeInvalido;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {


    @FXML
    private Pane pane;

    private Jogador jogador=null;

    private List<Comida> comidas = new ArrayList<>();


    private Text coordenadas;
    private Text gameOver;

    public void initialize(){

        gameOver = new Text();
        gameOver.setText("Game Over!!!\nN para iniciar novamente");
        gameOver.setStroke(Color.RED);
        gameOver.setVisible(false);

        pane.getChildren().add(gameOver);

        coordenadas = new Text();
        coordenadas.setX(10);
        coordenadas.setY(10);

        pane.getChildren().add(coordenadas);

        pane.setOnMouseMoved(e->{
            coordenadas.setText(e.getX()+","+e.getY());
        });

        pane.setOnMouseClicked(e->{
            try{
                if(jogador==null){
                    jogador = new Jogador("AAA",e.getX(),e.getY(),10, Color.BEIGE);
                    pane.getChildren().add(jogador);
                }

            }catch (NomeInvalido n){

            }
        });

        pane.setOnKeyPressed(e->{
            System.out.println(e.getCode());
            try {

                switch (e.getCode()) {
                    case A:
                        jogador.setMove(Move.Left);
                        break;
                    case D:
                        jogador.setMove(Move.Rigth);
                        break;
                    case W:
                        jogador.setMove(Move.Up);
                        break;
                    case S:
                        jogador.setMove(Move.Down);
                        break;
                    case N:
                        pane.getChildren().remove(jogador);
                        comidas.forEach(elem->{
                            pane.getChildren().remove(elem);
                        });
                        jogador=null;
                        comidas.clear();
                        gameOver.setVisible(false);
                        break;

                }
            }catch (Exception x){

            }
        });
        Platform.runLater(()->pane.requestFocus());

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate ;

            private double speed = 50 ; // pixels per second

            @Override
            public void start() {
                lastUpdate = System.nanoTime();
                super.start();
            }

            @Override
            public void handle(long now) {
                long elapsedNanoSeconds = now - lastUpdate ;
                double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0 ;
                update(elapsedSeconds*speed);
                lastUpdate = now;
            }
        };

        animationTimer.start();


    }


    private void update(double speed){
        if(jogador!=null){
            if(!jogador.estaVivo()){
                gameOver();
                return;
            }



            jogador.doMove(speed);

            if(jogador.getTranslateX()>pane.getWidth()){
                jogador.setTranslateX(0);
            }else if(jogador.getTranslateX()<0){
                jogador.setTranslateX(pane.getWidth());
            }
            if(jogador.getTranslateY()>pane.getHeight()){
                jogador.setTranslateY(0);
            }else if(jogador.getTranslateY()<0){
                jogador.setTranslateY(pane.getHeight());
            }


            if(comidas.size()<50 && Math.random()<0.05){
                double tamanho=0;
                while(tamanho<5){
                    tamanho=Math.random()*10;
                }
                Comida c = new Comida(Math.random()*pane.getWidth(),
                                      Math.random()*pane.getHeight(),
                                      tamanho,Math.random()<0.1);
                comidas.add(c);
                pane.getChildren().add(c);
            }


            comidas.forEach(comida->{
                if(jogador.getBoundsInParent().intersects(comida.getBoundsInParent())){
                    System.out.println("Comeu...");
                    comida.setComida();
                    jogador.comeu(comida);
                }
            });


            pane.getChildren().removeIf(elem->{

                if(elem instanceof Comida){
                    return ((Comida)elem).foiComida();
                }
                return false;
            });
            comidas.removeIf(elem->elem.foiComida());

        }

    }

    private void gameOver(){

        gameOver.setX(pane.getWidth()/2.0);
        gameOver.setY(pane.getHeight()/2.0);

        gameOver.setVisible(true);
    }




}
