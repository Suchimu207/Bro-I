import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public final class Controle{
    private Visual visualg;
    private Banco arquivista;
    private Scanner teclado;
    
    private boolean rodarJogo, debug, bloqueioBloco, bloqueioLimite;
    private int entrada, pos_x, pos_y, mapaTamanhoX, mapaTamanhoY;
    private String estadoJogo, mapaAtual, blocoAtual, direçãoJogador;
    private ArrayList<String> caracteres;
    
    public Controle(Visual visualg, Banco arquivista, Scanner teclado){
        this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
        caracteres = new ArrayList<String>();
        
        estadoJogo = "Título";
        mapaAtual = "Teste";
        rodarJogo = true;
        debug = false;
		bloqueioBloco = false;
		bloqueioLimite = false;
        iniciarJogo();
    }
    
    private void iniciarJogo(){
        do{
			visualg.desenhaMenu("LimpaTela");
            if (estadoJogo == "Título"){
				açãoTítulo();
            }else if (estadoJogo == "Mapa"){
			    pos_x = Banco.getJogador_x();
			    pos_y = Banco.getJogador_y();
			    if (debug == true) mostrarDebug();
			    visualg.desenhaMapa(mapaAtual, pos_x, pos_y);
                visualg.desenhaMenu("Comandos");
                receberComandos();
            }
        }while(rodarJogo == true);
    }
    
    private void receberComandos(){
        tratarEntrada(); System.out.print("\n");
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
            estadoJogo = "Título";
            break;
            case 7:
            break;
            case 207:
            ativarDebug();
            break;
            }
    }
    
    private void tratarEntrada(){
        try{
            visualg.desenhaMenu("Seta"); entrada = teclado.nextInt();
        }catch(InputMismatchException e){
            visualg.desenhaErro("Entrada");
            entrada = 0;
        }finally{
            teclado.nextLine();
        }
    }
    
    private void tratarMovimento(){
        try{
            moverJogador();
        }catch(ArrayIndexOutOfBoundsException e){
            if (estadoJogo == "Mapa"){
				visualg.desenhaErro("Movimento");
				bloqueioLimite=true;
			}
        }finally{
			if (direçãoJogador == "Esquerda" && bloqueioLimite == true || 
            direçãoJogador == "Direita" && bloqueioLimite == true){
            pos_y = Banco.getJogadorAnterior_y();
            Banco.setJogador_y(pos_y);
            }
            if (direçãoJogador == "Cima" && bloqueioLimite == true ||
            direçãoJogador == "Baixo" && bloqueioLimite == true){
            pos_x = Banco.getJogadorAnterior_x();
            Banco.setJogador_x(pos_x);
            }
			bloqueioLimite=false;
        }
    }

    private void açãoUm(){
        if (estadoJogo == "Mapa") direçãoJogador="Esquerda"; tratarMovimento();
    }
    
    private void açãoDois(){
        if(estadoJogo == "Mapa") direçãoJogador="Direita"; tratarMovimento();
    }
    
    private void açãoTrês(){
        if (estadoJogo == "Título"){
            visualg.desenhaMenu("Sair");
            rodarJogo = false;
			estadoJogo = "";
        }else if (estadoJogo == "Mapa") direçãoJogador="Cima"; tratarMovimento();
    }
    
    private void açãoQuatro(){
        direçãoJogador="Baixo"; tratarMovimento();
    }
    
    private void açãoInventário(){
        
    }
    
    private void açãoTítulo(){
        visualg.desenhaMenu("Título");
        tratarEntrada();
        switch (entrada){
            case 1:
			Banco.resetaInformações();
            estadoJogo = "Mapa";
            break;
            case 2:
            estadoJogo = "Mapa";
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
		mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
		mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.
        System.out.println("Jogador_X: "+pos_x+"\nJogador_Y: "+pos_y);
		System.out.println("TamanhoMapa_X: "+mapaTamanhoX+"\nTamanhoMapa_Y: "+mapaTamanhoY);
    }
    
    private void moverJogador(){
		Banco.setJogadorAnterior_x(pos_x);
        Banco.setJogadorAnterior_y(pos_y);
			
        if (direçãoJogador == "Esquerda"){
            pos_y = Banco.getJogador_y();
            pos_y--;
            Banco.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Direita"){
            pos_y = Banco.getJogador_y();
            pos_y++;
            Banco.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Cima"){
            pos_x = Banco.getJogador_x();
            pos_x--;
            Banco.setJogador_x(pos_x);
        }
        if (direçãoJogador == "Baixo"){
            pos_x = Banco.getJogador_x();
            pos_x++;
            Banco.setJogador_x(pos_x);
        }
        bloquearJogador(); //Desfaz o último movimento se não for transponível.
	}
    
    private void bloquearJogador(){
        blocoAtual = visualg.getBlocoAtual(pos_x, pos_y);
        bloqueioBloco = arquivista.getBloqueio(blocoAtual);
        
        if (direçãoJogador == "Esquerda" && bloqueioBloco == true || 
        direçãoJogador == "Direita" && bloqueioBloco == true){
            pos_y = Banco.getJogadorAnterior_y();
            Banco.setJogador_y(pos_y);
        }
        if (direçãoJogador == "Cima" && bloqueioBloco == true ||
        direçãoJogador == "Baixo" && bloqueioBloco == true){
            pos_x = Banco.getJogadorAnterior_x();
            Banco.setJogador_x(pos_x);
        }
		bloqueioBloco=false;
    }
    
    /* A verificação é feita um bloco acima e abaixo, e um pros lados, além da posição atual.
    private void verificarEvento(){
        blocoAtual = visualg.getBlocoAtual(pos_x, pos_y);
        boolean evento = arquivista
    }
    */

  //===
}