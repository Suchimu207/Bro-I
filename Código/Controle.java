import java.util.Scanner;
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
	private EstadosJogo estadoAtualJogo;

	private ArrayList<String> caracteres;
    private ArrayList<Eventos> ocorrências;
	private ArrayList<String> mapas;
	
	private final String VAZIO;
	
    private boolean rodarJogo, debug, bloqueioBloco, bloqueioLimite, isEventoAtivo, debugComandos;
    private int entrada, posJogador_x, posJogador_y, mapaTamanhoX, mapaTamanhoY, idContador, 
	posEvento_x, posEvento_y, eventoAtualId;
    
	private String mapaAtual, mapaInicial, blocoAtual, os, blocoVerificado, textoPlaca;
	private Direcao direçãoJogador;
	private TipoBloco tipoEventoAtual;
	private boolean mapaEventos, blocoEventoAchado;
	
    public Controle(Visual visualg, Banco arquivista, Scanner teclado, String mapaInicial, ArrayList<String> mapas, String VAZIO, String os){
		this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
		this.mapaInicial = mapaInicial;
		this.mapas = mapas;
		this.os = os;
		this.direçãoJogador = null;
		this.tipoEventoAtual = null;
		this.VAZIO = VAZIO;
		
        caracteres = new ArrayList<String>();
		ocorrências = new ArrayList<Eventos>();
		
        estadoAtualJogo = estadoAtualJogo.TITULO;
		mapaAtual = VAZIO;
		eventoAtualId = -1;
	
        rodarJogo = true;
        debug = true;
		debugComandos = false;
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
				visualg.desenhaNomeMapa(mapaAtual);
			    visualg.desenhaMapa(mapaAtual, Banco.getJogador_x(), Banco.getJogador_y());
                visualg.desenhaComandos(isEventoAtivo, mapaAtual, Banco.getJogador_x(), Banco.getJogador_y(), tipoEventoAtual, eventoAtualId);
            }
			receberComandos();
        }while(rodarJogo == true);
    }
	
    private void receberComandos(){
        tratarEntrada(); System.out.print("\n");
        
		if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			Direcao direcao = Direcao.procuraDirecao(entrada);
			if (direcao != null){
				direçãoJogador = direcao;
				tratarMovimento();
				return;
			}
		}
		
        switch (entrada){
            case 1:
			if(!estadoAtualJogo.equals(estadoAtualJogo.MAPA)) açãoUm(); break;
			case 2:
			if(!estadoAtualJogo.equals(estadoAtualJogo.MAPA)) açãoDois(); break;
			case 3:
			if(!estadoAtualJogo.equals(estadoAtualJogo.MAPA)) açãoTrês(); break;
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
            case -207:
            if (debug) ativarDebugComandos();
            break;
            case -1:
            if (debug) teletransporte();
            break;
        }
    }
	
    private void tratarEntrada(){
		try{
            visualg.desenhaSeta(); entrada = teclado.nextInt();
        }catch(InputMismatchException e){
            visualg.desenhaErro("Entrada", e.getMessage());
            entrada = -99999;
        }finally{
            teclado.nextLine();
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
		tipoEventoAtual = null;
		eventoAtualId = -1;
		estadoAtualJogo = estadoAtualJogo.MAPA;
		mapaAtual = mapaInicial;
		ocorrências.clear();
		mapaEventos = false;
	}
	
	private void açãoUm(){
		if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
			resetaInformaçõesJogatina();
			Banco.resetaInformaçõesJogador();
			//if (debug == false) visualg.desenhaCarregamento();
		}
    }
	
	private void açãoDois(){
		if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
			estadoAtualJogo = estadoAtualJogo.MAPA;
			mapaAtual = mapaInicial;
		}
    }
	
	private void açãoTrês(){
        if (estadoAtualJogo.equals(estadoAtualJogo.TITULO)){
            visualg.desenhaSair();
            rodarJogo = false;
			estadoAtualJogo = null;
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
				
				blocoEventoAchado = TipoBloco.isExisteBloco(blocoAtual);
					if(blocoEventoAchado){
						evento = Eventos.criarEvento(blocoAtual, linha, coluna, ++idContador);
						if (evento != null) ocorrências.add(evento);
					}
				}
            }
	  //===
	}
	
	private void verificarEvento(){
		isEventoAtivo = false;
		eventoAtualId = -1;
		tipoEventoAtual = null;
		
		for (Eventos evento : ocorrências){
			if (evento != null && posJogador_x == evento.getPosEvento_x() && posJogador_y == evento.getPosEvento_y()){
            tipoEventoAtual = evento.getTipo();
            eventoAtualId = evento.getId();
            isEventoAtivo = true;
            break;
			}
		}
	}
	
	private void ativarEvento(){
		for (Eventos evento : ocorrências){
            if (evento != null && evento.getId() == eventoAtualId){
                switch (evento.getTipo()){
                    case BAU:
                        break;
                    case PLACA:
						textoPlaca = Eventos.chamarPlaca(mapaAtual, eventoAtualId);
						visualg.desenhaPlaca(textoPlaca);
						visualg.desenhaSeta(); teclado.nextLine();
                        break;
                    case LOJA:
						break;
					case TAVERNA:
						break;
					case TRANSICAO:
						//Atualiza a posição do jogador.
						Eventos.chamarTransição(mapaAtual, eventoAtualId, Banco.getJogador_x(), Banco.getJogador_y());
						Banco.setJogador_x(Eventos.getPosJogador_x());
						Banco.setJogador_y(Eventos.getPosJogador_y());
						mapaAtual = Eventos.getMapaNome();
						
						resetaEventos();
						break;
                }
                break;
            }
        }
	  //===
	}
	
	private void resetaEventos(){
		ocorrências.clear();
		isEventoAtivo = false;
		tipoEventoAtual = null;
		eventoAtualId = -1;
		mapaEventos = false;
	}
	
    private void ativarDebug(){
        if (debug == true){
            debug = false;
        }else debug = true;
    }
	
	private void ativarDebugComandos(){
		if (debugComandos == true){
            debugComandos = false;
        }else debugComandos = true;
	}
    
	private void teletransporte(){
		if(estadoAtualJogo.equals(estadoAtualJogo.MAPA)){
			System.out.println("");
			System.out.println("Mudar mapa:");
			tratarEntrada();
			if (entrada <= mapas.size() && entrada >= 0){
				mapaAtual = mapas.get(entrada); resetaEventos();
			}else visualg.desenhaErro("ProcurarMapa",VAZIO);
			
			System.out.println("");
			System.out.println("Jogador_X:");
			tratarEntrada();
			posJogador_x = Banco.getJogador_x();
			posJogador_x = entrada;
       
			System.out.println("");
			System.out.println("Jogador_Y:");
			tratarEntrada();
			posJogador_y = Banco.getJogador_y();
			posJogador_y = entrada;
				
			mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
			mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.
			
			if (posJogador_x <= mapaTamanhoX && posJogador_x >= 0
			&& posJogador_y <= mapaTamanhoY && posJogador_y >= 0){
				Banco.setJogador_x(posJogador_x);
				Banco.setJogador_y(posJogador_y);
			}else{
				visualg.desenhaErro("ProcurarPosição",VAZIO);
				
				posJogador_x = Banco.getJogadorAnterior_x();
				Banco.setJogador_x(posJogador_x);
				posJogador_y = Banco.getJogadorAnterior_y();
				Banco.setJogador_y(posJogador_y);
			}
		 //===
		}
	}
	
    private void mostrarDebug(){
		visualg.desenhaBarra();
        System.out.println("Jogador_X: "+Banco.getJogador_x()+"\nJogador_Y: "+Banco.getJogador_y());
		System.out.println("DireçãoJogador: " + (direçãoJogador != null ? direçãoJogador.name() : "Vazio"));
		System.out.println("TamanhoMapa_X: "+mapaTamanhoX+"\nTamanhoMapa_Y: "+mapaTamanhoY);
		System.out.println("isEventoAtivo: "+isEventoAtivo);
		System.out.println("TipoEventoAtual: "+tipoEventoAtual+"\nEventoAtualId: "+eventoAtualId);
		System.out.println("BloqueioBloco: "+bloqueioBloco+" | BloqueioLimite: "+bloqueioLimite);
		System.out.println("EstadoJogo: "+estadoAtualJogo.getEstadoJogo()+" | MapaAtual: "+mapaAtual);
		System.out.println("Ocorrências: "+ocorrências.size());
		System.out.println("MapaEventos: "+mapaEventos);
		visualg.desenhaBarra();
		if(debugComandos == true){
			System.out.println(">>Comandos especiais:");
			System.out.println("Reiniciar jogo: 0");
			System.out.println("Teletransporte: -1 (Somente em mapas)");
			System.out.println("Esconder/Mostrar comandos especiais: -207");
			visualg.desenhaBarra();
		}
    }
    
	private void tratarMovimento(){
        try {
            moverJogador();
        } catch (ArrayIndexOutOfBoundsException e) {
            if (estadoAtualJogo.equals(estadoAtualJogo.MAPA)) {
                visualg.desenhaErro("Movimento", e.getMessage());
                bloqueioBloco = true;
				bloquearJogador();
            }
        }
    }
	
    private void moverJogador(){
		Banco.setJogadorAnterior_x(posJogador_x);
        Banco.setJogadorAnterior_y(posJogador_y);
        
        posJogador_x = Banco.getJogador_x() + direçãoJogador.getDeltaX();
        posJogador_y = Banco.getJogador_y() + direçãoJogador.getDeltaY();
        
        Banco.setJogador_x(posJogador_x);
        Banco.setJogador_y(posJogador_y);
    
        bloquearJogador(); //Desfaz o último movimento se não for transponível.
		iniciarEventos();
		verificarEvento();
	}
    
    private void bloquearJogador(){
		if (!bloqueioBloco){
			blocoAtual = visualg.getBlocoAtual(posJogador_x, posJogador_y);
			bloqueioBloco = arquivista.getBloqueio(blocoAtual);
		}
        
		if (bloqueioBloco){
			posJogador_x = Banco.getJogadorAnterior_x();
			posJogador_y = Banco.getJogadorAnterior_y();
			Banco.setJogador_x(posJogador_x);
			Banco.setJogador_y(posJogador_y);
		}
		
		bloqueioBloco = false;
		blocoAtual = "";
    }

  //===
}