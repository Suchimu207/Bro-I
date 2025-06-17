import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public final class Controle{
    private Visual visualg;
    private Banco arquivista;
    private Scanner teclado;
    
    private boolean rodarJogo, debug, bloqueio;
    private int entrada, pos_x, pos_y;
    private String estado, mapaAtual, blocoAtual;
    private ArrayList<String> caracteres;
    
    public Controle(Visual visualg, Banco arquivista, Scanner teclado){
        this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
        caracteres = new ArrayList<String>();
        
        estado = "Título";
        mapaAtual = "Teste";
        rodarJogo = true;
        debug = false;
        iniciarJogo();
    }
    
    private void iniciarJogo(){
        do{
            if (estado == "Título"){
                açãoTítulo();
            }else if (estado == "Mapa"){
                visualg.desenhaMenu("LimpaTela");
			    pos_x = arquivista.getJogador_x();
			    pos_y = arquivista.getJogador_y();
			    if (debug == true) mostrarDebug();
                visualg.desenhaMapa(mapaAtual, pos_x, pos_y);
                visualg.desenhaMenu("Comandos");
                receberComandos();
            }
        }while(rodarJogo == true);
    }
    
    private void receberComandos(){
        visualg.desenhaMenu("Seta"); entrada = teclado.nextInt(); System.out.print("\n");
        switch (entrada){
            case 1:
            açãoUm();
            break;
            case 2:
            açãoDois();
            break;
            case 3:
            açãoTrês();
            break;
            case 4:
            açãoQuatro();
            break;
            case 5:
            açãoInventário();
            break;
            case 6:
            estado = "Título";
            açãoTítulo();
            break;
            case 7:
            break;
            case 207:
            ativarDebug();
            break;
            }
    }
    
    private void açãoUm(){
        if (estado == "Título"){
            estado = "Mapa";
        }else if (estado == "Mapa") moverJogador("Esquerda");
    }
    
    private void açãoDois(){
        if (estado == "Título"){
            estado = "Mapa";
        }else if (estado == "Mapa") moverJogador("Direita");
    }
    
    private void açãoTrês(){
        if (estado == "Título"){
            visualg.desenhaMenu("Sair");
            rodarJogo = false;
        }else if (estado == "Mapa") moverJogador("Cima");
    }
    
    private void açãoQuatro(){
        moverJogador("Baixo");
    }
    
    private void açãoInventário(){
        
    }
    
    private void açãoTítulo(){
        visualg.desenhaMenu("LimpaTela");
        visualg.desenhaMenu("Título");
        visualg.desenhaMenu("Seta"); entrada = teclado.nextInt();
        switch (entrada){
            case 1:
            açãoUm();
            break;
            case 2:
            açãoDois();
            break;
            case 3:
            açãoTrês();
            }
    }
    
    private void açãoEspecífica(){
        
    }
    
    private void ativarDebug(){
        if (debug == true){
            debug = false;
        }else debug = true;
    }
    
    private void mostrarDebug(){
        pos_x = arquivista.getJogador_x();
		pos_y = arquivista.getJogador_y();
        System.out.println("Jogador_X: "+pos_x+"\nJogador_Y: "+pos_y);
    }
    
    private void moverJogador(String direçãoJogador){
        arquivista.setJogadorAnterior_x(pos_x);
        arquivista.setJogadorAnterior_y(pos_y);
        
        if (direçãoJogador == "Esquerda"){
            pos_y = arquivista.getJogador_y();
            pos_y--;
            arquivista.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Direita"){
            pos_y = arquivista.getJogador_y();
            pos_y++;
            arquivista.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Cima"){
            pos_x = arquivista.getJogador_x();
            pos_x--;
            arquivista.setJogador_x(pos_x);
        }
        if (direçãoJogador == "Baixo"){
            pos_x = arquivista.getJogador_x();
            pos_x++;
            arquivista.setJogador_x(pos_x);
        }
        bloquearJogador(direçãoJogador); //Desfaz o último movimento se não for transponível.
    }
    
    private void bloquearJogador(String direçãoJogador){
        blocoAtual = visualg.getBlocoAtual(pos_x, pos_y);
        
        bloqueio = arquivista.getBloqueio(blocoAtual);
        
        if (direçãoJogador == "Esquerda" && bloqueio == true){
            pos_y = arquivista.getJogadorAnterior_y();
            arquivista.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Direita" && bloqueio == true){
            pos_y = arquivista.getJogadorAnterior_y();
            arquivista.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Cima" && bloqueio == true){
            pos_x = arquivista.getJogadorAnterior_x();
            arquivista.setJogador_x(pos_x);
        }
        if (direçãoJogador == "Baixo" && bloqueio == true){
            pos_x = arquivista.getJogadorAnterior_x();
            arquivista.setJogador_x(pos_x);
        }
    }

  //===
}