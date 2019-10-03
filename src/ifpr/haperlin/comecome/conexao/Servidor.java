package ifpr.haperlin.comecome.conexao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {

    private ServerSocket servidor;
    private int PORT=12700;
    private String HOST="localhost";

    public Servidor() throws IOException {

        servidor = new ServerSocket(PORT);

    }

    public void run(){
        while (true){
            try{
                Socket cliente  = servidor.accept();

            }catch (IOException e){

            }

        }



    }






}
