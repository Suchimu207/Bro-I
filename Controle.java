import java.util.Scanner;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.IOException;

public final class Controle{
	private enum EstadosJogo{
		TITULO("Título"),
		MAPA("Mapa");
		
		private String estadoJogo;
		
		EstadosJogo(String estadoJogo){
			this.estadoJogo = estadoJogo;
		}
		
		public String getEstadoJogo(){
			return estadoJogo;
		}
	}
	
	private Visual visualg;
    private Banco arquivista;
	private Scanner teclado;
	private Eventos evento;
	private Hashtable tipoEventos;
	private EstadosJogo estadoAtualJogo;

	private ArrayList<String> caracteres;
    private ArrayList<Eventos> ocorrências;
	private ArrayList<String> mapas;
	
	private final String VAZIO;
	
    private boolean rodarJogo, debug, bloqueioBloco, bloqueioLimite, isEventoAtivo;
    private int entrada, posJogador_x, posJogador_y, mapaTamanhoX, mapaTamanhoY, idContador, 
	posEvento_x, posEvento_y, eventoAtualId;
    
	private String mapaAtual, mapaInicial, blocoAtual, direçãoJogador, tipoEventoAtual, os, blocoVerificado, textoPlaca;
	private boolean mapaEventos;
	
    public Controle(Visual visualg, Banco arquivista, Scanner teclado, Hashtable tipoEventos, String mapaInicial, ArrayList<String> mapas, String VAZIO, String os){
		this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
		this.tipoEventos = tipoEventos;
		this.mapaInicial = mapaInicial;
		this.mapas = mapas;
		this.os = os;
		this.VAZIO = VAZIO;
		
        caracteres = new ArrayList<String>();
		ocorrências = new ArrayList<Eventos>();
		
        estadoAtualJogo = estadoAtualJogo.TITULO;
		mapaAtual = VAZIO;
		tipoEventoAtual = VAZIO;
		eventoAtualId = -1;
	
        rodarJogo = true;
        debug = true;
		bloqueioBloco = false;
		bloqueioLimite = false;
		mapaEventos = false;
		isEventoAtivo = false;
		
		iniciarJogo();
    }
		
    private void iniciarJogo(){
        do{
			visualg.limpaTela();
			if (debug == true) mostrarDebug();
            if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
				visualg.desenhaTítulo();
            }else if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
				posJogador_x = Banco.getJogador_x();
				posJogador_y = Banco.getJogador_y();
				
				visualg.desenhaNomeMapa(mapaAtual);
			    visualg.desenhaMapa(mapaAtual, posJogador_x, posJogador_y);
                visualg.desenhaComandos(isEventoAtivo, mapaAtual, posJogador_x, posJogador_y, tipoEventoAtual, eventoAtualId);
            }
			receberComandos();
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
            açãoCinco();
            break;
            case 6:
            açãoSeis();
            break;
            case 7:
			açãoSete();
            break;
            case 207:
            ativarDebug();
            break;
			case 0:
			if (debug) reiniciarJogo();
			break;
            }
    }
	
    private void tratarEntrada(){
		try{
            visualg.desenhaSeta(); entrada = teclado.nextInt();
        }catch(InputMismatchException e){
            visualg.desenhaErro("Entrada", e.getMessage());
            entrada = -1;
        }finally{
            teclado.nextLine();
        }
    }
    
    private void tratarMovimento(){
        try{
            moverJogador();
        }catch(ArrayIndexOutOfBoundsException e){
            if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
				visualg.desenhaErro("Movimento", e.getMessage());
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
	
	private void reiniciarJogo(){
        try{
            if (os.contains("win")){
                new ProcessBuilder("cmd", "/c", "java Main.java").inheritIO().start().waitFor();
				System.exit(0);
            }else if (os.contains("linux") || os.contains("unix")){
                new ProcessBuilder("java Main").inheritIO().start().waitFor();
            }
        }catch (Exception e){
				visualg.desenhaErro("Reiniciar", e.getMessage());
        }
      //===
    }
	
	private void resetaInformaçõesJogatina(){
		isEventoAtivo = false;
		tipoEventoAtual = VAZIO;
		eventoAtualId = -1;
		estadoAtualJogo = estadoAtualJogo.MAPA;
		mapaAtual = mapaInicial;
		ocorrências.clear();
		mapaEventos = false;
	}
	
    private void açãoUm(){
        if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			direçãoJogador="Esquerda"; tratarMovimento();
		}else if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
			resetaInformaçõesJogatina();
			Banco.resetaInformaçõesJogador();
			if (debug == false) visualg.desenhaCarregamento();
		}
    }
    
    private void açãoDois(){
        if(estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			direçãoJogador="Direita"; tratarMovimento();
		}else if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
			estadoAtualJogo = estadoAtualJogo.MAPA;
			mapaAtual = mapaInicial;
		}
    }
    
    private void açãoTrês(){
        if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
            visualg.desenhaSair();
            rodarJogo = false;
			estadoAtualJogo = null;
        }else if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)) direçãoJogador="Cima"; tratarMovimento();
    }
    
    private void açãoQuatro(){
        if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			direçãoJogador="Baixo"; tratarMovimento();
		}
    }
    
    private void açãoCinco(){
		//TODO: Sistema de inventário.
    }
    
	private void açãoSeis(){
		if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			estadoAtualJogo = estadoAtualJogo.TITULO;
			mapaAtual = VAZIO;
		}
	}
	
    private void açãoSete(){
        if (estadoAtualJogo.equals(estadoAtualJogo.MAPA) && isEventoAtivo == true){
			ativarEvento();
		}
    }
    
	private void iniciarEventos(){
		mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
		mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.
		
        if(mapaEventos == false && !VAZIO.equals(mapaAtual)){   
            montarEventos();
            mapaEventos = true;
        }
	  //===
    }
	
	private void montarEventos(){
		idContador = 0;
		
		for (int linha = 0; linha < mapaTamanhoX+1; linha++){
            for (int coluna = 0; coluna < mapaTamanhoY+1; coluna++){
				blocoAtual = visualg.getBlocoAtual(linha, coluna);
				evento = Eventos.criarEvento(blocoAtual, linha, coluna, -1, tipoEventos);	
				for (int i = 0; i <= tipoEventos.size(); i++){
					if(tipoEventos.containsValue(blocoAtual)){
						evento = Eventos.criarEvento(blocoAtual, linha, coluna, ++idContador, tipoEventos);
						ocorrências.add(evento);
						break;
					}
				}
            }
		}		
	  //===
	}
	
	private void verificarEvento(){
		isEventoAtivo = false;
		eventoAtualId = -1;
		tipoEventoAtual = VAZIO;
		
		for (Eventos evento : ocorrências){
			if (posJogador_x == evento.getPosEvento_x() && posJogador_y == evento.getPosEvento_y()){
            tipoEventoAtual = evento.getTipo();
            eventoAtualId = evento.getId();
            isEventoAtivo = true;
            break;
			}
		}
	}
	
	private void ativarEvento(){
		for (Eventos evento : ocorrências){
            if (evento.getId() == eventoAtualId){
                switch (evento.getTipo()){
                    case "Báu":
                        break;
                    case "Placa":
						textoPlaca = Eventos.chamarPlaca(mapaAtual, eventoAtualId);
						visualg.desenhaPlaca(textoPlaca);
						visualg.desenhaSeta(); teclado.nextLine();
                        break;
                    case "Loja":
						break;
					case "Taverna":
						break;
					case "Transição":
						//Atualiza a posição do jogador.
						Eventos.chamarTransição(mapaAtual, eventoAtualId, posJogador_x, posJogador_y);
						posJogador_x = Eventos.getPosJogador_x();
						posJogador_y = Eventos.getPosJogador_y();
						Banco.setJogador_x(posJogador_x);
						Banco.setJogador_y(posJogador_y);
						
						mapaAtual = Eventos.getMapaNome();
						ocorrências.clear();
						isEventoAtivo = false;
						tipoEventoAtual = VAZIO;
						eventoAtualId = -1;
						mapaEventos = false;
						break;
                }
                break;
            }
        }
	  //===
	}
	
    private void ativarDebug(){
        if (debug == true){
            debug = false;
        }else debug = true;
    }
    
    private void mostrarDebug(){
		visualg.desenhaBarra();
        System.out.println("Jogador_X: "+posJogador_x+"\nJogador_Y: "+posJogador_y);
		System.out.println("TamanhoMapa_X: "+mapaTamanhoX+"\nTamanhoMapa_Y: "+mapaTamanhoY);
		System.out.println("isEventoAtivo: "+isEventoAtivo);
		System.out.println("TipoEventoAtual: "+tipoEventoAtual+"\nEventoAtualId: "+eventoAtualId);
		System.out.println("BloqueioBloco: "+bloqueioBloco+" | BloqueioLimite: "+bloqueioLimite);
		System.out.println("EstadoJogo: "+estadoAtualJogo.getEstadoJogo()+" | MapaAtual: "+mapaAtual);
		System.out.println("Ocorrências: "+ocorrências.size());
		System.out.println("MapaEventos: "+mapaEventos);
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
		iniciarEventos();
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

  //===
}