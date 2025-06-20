import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public final class Controle{
	private Visual visualg;
    private Banco arquivista;
	private Eventos evento;
	private Scanner teclado;
    
    private boolean rodarJogo, debug, bloqueioBloco, bloqueioLimite, isEventoAtivo;
	private boolean mapaEventos_0;
    private int entrada, posJogador_x, posJogador_y, mapaTamanhoX, mapaTamanhoY;
	private int posEvento_x, posEvento_y;
    private String estadoJogo, mapaAtual, blocoAtual, direçãoJogador, tipoEvento;
    private ArrayList<String> caracteres;
    private ArrayList<Eventos> ocorrências;
	
    public Controle(Visual visualg, Banco arquivista, Scanner teclado, Eventos evento){
        this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
		this.evento = evento;
		
        caracteres = new ArrayList<String>();
		ocorrências = new ArrayList<Eventos>();
        
        estadoJogo = "Título";
		tipoEvento = "Vazio";
        rodarJogo = true;
        debug = false;
		bloqueioBloco = false;
		bloqueioLimite = false;
		mapaEventos_0 = false;
		isEventoAtivo = false;
		
		iniciarJogo();
    }
	
    private void iniciarJogo(){
        do{
			visualg.limpaTela();
			if (debug == true) mostrarDebug();
            if (estadoJogo == "Título"){
				açãoTítulo();
            }else if (estadoJogo == "Mapa"){
				posJogador_x = Banco.getJogador_x();
				posJogador_y = Banco.getJogador_y();
				mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
				mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.
			    visualg.desenhaMapa(mapaAtual, posJogador_x, posJogador_y);
				iniciarEventos();
                visualg.desenhaMenu("Comandos", isEventoAtivo, mapaAtual, posEvento_x, posEvento_y, tipoEvento);
                receberComandos();
            }
        }while(rodarJogo == true);
    }
	
	private void iniciarEventos(){
		if(mapaEventos_0 == false && mapaAtual == "Teste"){
			for (int linha = 0; linha < mapaTamanhoX; linha++){
			for (int coluna = 0; coluna < mapaTamanhoY; coluna++){
				blocoAtual = visualg.getBlocoAtual(linha, coluna);
				evento = Eventos.criarEventos(blocoAtual, linha, coluna);
				if (evento.getTipo() != "Vazio") ocorrências.add(evento);
				}
			}
			mapaEventos_0 = true;
		}
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
			mapaAtual = "Vazio";
            break;
            case 7:
			açãoEspecífica();
            break;
            case 207:
            ativarDebug();
            break;
            }
    }
    
    private void tratarEntrada(){
        try{
            visualg.desenhaSeta(); entrada = teclado.nextInt();
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
            posJogador_y = Banco.getJogadorAnterior_y();
            Banco.setJogador_y(posJogador_y);
            }
            if (direçãoJogador == "Cima" && bloqueioLimite == true ||
            direçãoJogador == "Baixo" && bloqueioLimite == true){
            posJogador_x = Banco.getJogadorAnterior_x();
            Banco.setJogador_x(posJogador_x);
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
            visualg.desenhaSair();
            rodarJogo = false;
			estadoJogo = "";
        }else if (estadoJogo == "Mapa") direçãoJogador="Cima"; tratarMovimento();
    }
    
    private void açãoQuatro(){
        direçãoJogador="Baixo"; tratarMovimento();
    }
    
    private void açãoInventário(){
		//TODO: Sistema de inventário.
    }
    
    private void açãoTítulo(){
		tipoEvento = "Vazio";
        visualg.desenhaMenu("Título", isEventoAtivo, mapaAtual, posEvento_x, posEvento_y, tipoEvento);
        tratarEntrada();
        switch (entrada){
            case 1:
			Banco.resetaInformações();
            estadoJogo = "Mapa";
			mapaAtual = "Teste";
            break;
            case 2:
            estadoJogo = "Mapa";
			mapaAtual = "Teste";
            break;
            case 3:
            açãoTrês();
			break;
			case 207:
            ativarDebug();
            break;
            }
    }
    
    private void açãoEspecífica(){
        if (estadoJogo == "Mapa" && isEventoAtivo == true){
			ativarEvento();
		}
    }
    
	private void ativarEvento(){
		//Usar a classe própria Eventos.
	}
	
    private void ativarDebug(){
        if (debug == true){
            debug = false;
        }else debug = true;
    }
    
    private void mostrarDebug(){
		visualg.desenhaBarra();
        posJogador_x = arquivista.getJogador_x();
		posJogador_y = arquivista.getJogador_y();
		mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
		mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.

        System.out.println("Jogador_X: "+posJogador_x+"\nJogador_Y: "+posJogador_y);
		System.out.println("Evento_X: "+posEvento_x+"\nEvento_Y: "+posEvento_y);
		System.out.println("TamanhoMapa_X: "+mapaTamanhoX+"\nTamanhoMapa_Y: "+mapaTamanhoY);
		System.out.println("isEventoAtivo: "+isEventoAtivo+"\nTipoEvento: "+tipoEvento);
		System.out.println("BloqueioBloco: "+bloqueioBloco+"\nBloqueioLimite: "+bloqueioLimite);
		System.out.println("EstadoJogo: "+estadoJogo+"\nMapaAtual: "+mapaAtual);
		System.out.println("Ocorrências: "+ocorrências.size());
		visualg.desenhaBarra();
    }
    
    private void moverJogador(){
		Banco.setJogadorAnterior_x(posJogador_x);
        Banco.setJogadorAnterior_y(posJogador_y);
			
        if (direçãoJogador == "Esquerda"){
            posJogador_y = Banco.getJogador_y();
            posJogador_y--;
            Banco.setJogador_y(posJogador_y);
        }
        if (direçãoJogador == "Direita"){
            posJogador_y = Banco.getJogador_y();
            posJogador_y++;
            Banco.setJogador_y(posJogador_y);
        }
        if (direçãoJogador == "Cima"){
            posJogador_x = Banco.getJogador_x();
            posJogador_x--;
            Banco.setJogador_x(posJogador_x);
        }
        if (direçãoJogador == "Baixo"){
            posJogador_x = Banco.getJogador_x();
            posJogador_x++;
            Banco.setJogador_x(posJogador_x);
        }
        bloquearJogador(); //Desfaz o último movimento se não for transponível.
		verificarEvento();
	}
    
    private void bloquearJogador(){
        blocoAtual = visualg.getBlocoAtual(posJogador_x, posJogador_y);
        bloqueioBloco = arquivista.getBloqueio(blocoAtual);
        
        if (direçãoJogador == "Esquerda" && bloqueioBloco == true || 
        direçãoJogador == "Direita" && bloqueioBloco == true){
            posJogador_y = Banco.getJogadorAnterior_y();
            Banco.setJogador_y(posJogador_y);
        }
        if (direçãoJogador == "Cima" && bloqueioBloco == true ||
        direçãoJogador == "Baixo" && bloqueioBloco == true){
            posJogador_x = Banco.getJogadorAnterior_x();
            Banco.setJogador_x(posJogador_x);
        }
		bloqueioBloco=false;
		blocoAtual = "";
    }
		
	private void verificarEvento(){
		blocoAtual = visualg.getBlocoAtual(posJogador_x, posJogador_y); 
	
		posJogador_y = Banco.getJogador_y();
		posJogador_x = Banco.getJogador_x();
		
		if (isEventoAtivo == false){
			for (int i = 0; i < ocorrências.size(); i++){
			evento = ocorrências.get(i);
			posEvento_x = evento.getPos_x();
			posEvento_y = evento.getPos_y();
				if (posJogador_x == posEvento_x &&
						posJogador_y == posEvento_y){
						tipoEvento = evento.getTipo();
						isEventoAtivo = true;
						break;
						}
				}
		}else isEventoAtivo = false; tipoEvento = "Vazio";
	}
	
  //===
}