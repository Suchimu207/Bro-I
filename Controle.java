import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public final class Controle{
	private Visual visualg;
    private Banco arquivista;
	private Scanner teclado;
	private Eventos evento;
	
	private final String VAZIO = "Vazio";
    
    private boolean rodarJogo, debug, bloqueioBloco, bloqueioLimite, isEventoAtivo;
	private boolean mapaEventos_0;
    private int entrada, posJogador_x, posJogador_y, mapaTamanhoX, mapaTamanhoY;
	
	private int posEvento_x, posEvento_y, eventoAtualId;
    private String estadoJogo, mapaAtual, blocoAtual, direçãoJogador, tipoEventoAtual;
	private String[] tipoEventos;

    private ArrayList<String> caracteres;
    private ArrayList<Eventos> ocorrências;
	
    public Controle(Visual visualg, Banco arquivista, Scanner teclado, String[] tipoEventos){
        this.visualg = visualg;
        this.arquivista = arquivista;
        this.teclado = teclado;
		this.tipoEventos = tipoEventos;
		
        caracteres = new ArrayList<String>();
		ocorrências = new ArrayList<Eventos>();
		
        estadoJogo = "Título";
		mapaAtual = VAZIO;
		tipoEventoAtual = VAZIO;
		eventoAtualId = -1;
		
        rodarJogo = true;
        debug = true;
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
			    visualg.desenhaMapa(mapaAtual, posJogador_x, posJogador_y);
                visualg.desenhaMenu("Comandos", isEventoAtivo, mapaAtual, posEvento_x, posEvento_y, tipoEventoAtual);
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
			mapaAtual = VAZIO;
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
			estadoJogo = VAZIO;
        }else if (estadoJogo == "Mapa") direçãoJogador="Cima"; tratarMovimento();
    }
    
    private void açãoQuatro(){
        direçãoJogador="Baixo"; tratarMovimento();
    }
    
    private void açãoInventário(){
		//TODO: Sistema de inventário.
    }
    
    private void açãoTítulo(){
		tipoEventoAtual = VAZIO;
        visualg.desenhaMenu("Título", isEventoAtivo, mapaAtual, posEvento_x, posEvento_y, tipoEventoAtual);
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
			case 0:
			visualg.reiniciarJogo();
			break;
            }
    }
    
    private void açãoEspecífica(){
        if (estadoJogo == "Mapa" && isEventoAtivo == true){
			ativarEvento();
		}
    }
    
	private void iniciarEventos(){
		mapaTamanhoX = visualg.getQuantidadeLinhasX()-1;
		mapaTamanhoY = visualg.getQuantidadeColunasY(); //Número exato.
		
        if(mapaEventos_0 == false && "Teste".equals(mapaAtual)){   
            montarEventos();
            mapaEventos_0 = true;
        }
	  //===
    }
	
	private void montarEventos(){
		int idContador = 0;
		for (int linha = 0; linha < mapaTamanhoX; linha++){
            for (int coluna = 0; coluna < mapaTamanhoY+1; coluna++){
				blocoAtual = visualg.getBlocoAtual(linha, coluna);
				evento = Eventos.criarEvento(blocoAtual, 0, 0, 0);	
				for (int i = 0; i <= tipoEventos.length-1; i++){
					if(blocoAtual.equals(tipoEventos[i])){
						evento = Eventos.criarEvento(blocoAtual, linha, coluna, ++idContador);
						ocorrências.add(evento);
					}
				}
            }
		}
	  //===
	}
	
	private void verificarEvento(){
		isEventoAtivo = false;
		
		for (Eventos evento : ocorrências){
			if (posJogador_x == evento.getPos_x() && posJogador_y == evento.getPos_y()){
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
                    case "báu":
                        abrirBáu(evento);
                        break;
                    case "placa":
                        lerPlaca(evento);
                        break;
                    case "loja":
						break;
					case "taverna":
						break;
                }
                break;
            }
        }
        isEventoAtivo = false;
        tipoEventoAtual = VAZIO;
	  //===
	}
	
	private void abrirBáu(Eventos evento) {
        String textO = (String) evento.getDados();
        System.out.println(textO);
        // Remover o evento do mapa após interação
        ocorrências.remove(evento);
    }
	
	private void lerPlaca(Eventos evento) {
        String texto = (String) evento.getDados();
        System.out.println(texto);
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
		System.out.println("EstadoJogo: "+estadoJogo+" | MapaAtual: "+mapaAtual);
		System.out.println("Ocorrências: "+ocorrências.size());
		System.out.println("MapaEventos_0: "+mapaEventos_0);
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