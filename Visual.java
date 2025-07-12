import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;   
import java.nio.file.Paths;
import java.io.IOException;

public final class Visual{
	private Hashtable tipoEventos;
	private ArrayList<String> mapas;
	
	private String[][] mapaAtual;
	private String[] opçõesTítulo;
	
    private String os, título, texto, quadro, nomeVersão, nomeDoMapa;
    private int saída, quantidadeLinhasX, quantidadeColunasY;
	
    private final String VERDE, VERDE_MUSGO, BRANCO, VERMELHO, VERMELHO_FOGO, LARANJA, AZUL, ROXO, AMARELO, ROSA,
	PRETO, CINZA, RESETA,
	FundoVerde, FundoBranco, FundoVermelho, FundoPadrão;
	private final String VAZIO;

	private boolean eventoAqui;
    
    public Visual(Hashtable tipoEventos, String nomeVersão, String título, String[] opçõesTítulo, ArrayList<String> mapas, String VAZIO){
		this.tipoEventos = tipoEventos;
		this.nomeVersão = nomeVersão;
		this.título = título;
		this.opçõesTítulo = opçõesTítulo;
		this.mapas = mapas;
		this.VAZIO = VAZIO;
		
        os = System.getProperty("os.name").toLowerCase();
		nomeDoMapa = "";
        
        VERDE = "\033[92m";
		VERDE_MUSGO = "\033[32m";
        BRANCO = "\033[37m";
		VERMELHO = "\033[31m";
		VERMELHO_FOGO = "\033[91m";
        LARANJA = "\033[33m";
        AZUL = "\033[34m";
        ROXO = "\033[35m";
        AMARELO = "\033[93m";
        ROSA = "\033[95m";
		PRETO = "\033[30m";
		CINZA = "\033[90m";
		
		FundoVerde = "\033[42m";
		FundoBranco = "\033[107m";
		FundoVermelho = "\033[41m";
		
		FundoPadrão = "\033[49m";
        RESETA = "\u001B[0m";
        
        if (os.contains("win")){
            try{
            new ProcessBuilder("cmd", "/c", "title " + título).inheritIO().start();
        }catch (Exception e){
            System.out.println(VERMELHO+"Ocorreu um erro ao definir título: "+e.getMessage()+"."+RESETA); 
            espera(1500);
            }
        }
    }
    	
	public void desenhaCarregamento(){
		limpaTela();
		System.out.print(BRANCO+">>Carregando...\n"+RESETA);
		System.out.println("");
     	
		for (int i = 0; i <= 66; i++){
			System.out.print(FundoBranco+""+BRANCO+"."+FundoPadrão);
			espera(100);
		}
		
		System.out.println(""); System.out.println("");
		System.out.print(VERDE+"Authorise!!!"+RESETA);
		espera(2900); //TODO: Exato tempo do efeito sonoro de mesmo nome.
	}
	
    public void desenhaMapa(String mapa, int pos_x, int pos_y){
        try{
			List<String> linhas = Files.readAllLines(Paths.get("Mapas\\"+mapa+".txt"));
            
			quantidadeLinhasX = 0;
			quantidadeColunasY = 0;
			
            mapaAtual = new String[linhas.size()][];
			for (int i = 0; i < linhas.size(); i++) mapaAtual[i] = linhas.get(i).split("");
			quantidadeLinhasX = linhas.size();
			
			System.out.println("");
			desenhaCaractere(pos_x, pos_y);
        }catch(IOException e){
            System.out.println(VERMELHO+"Falha ao desenhar mapa: "+e.getMessage()+"."+RESETA);
        }
    }
	
	public void desenhaNomeMapa(String mapaAtual){
		System.out.print(CINZA+"============================================"+RESETA+"\n");
		if (mapas != null){
			if (mapas.get(0).equals(mapaAtual)) nomeDoMapa = "Teste";
			if (mapas.get(1).equals(mapaAtual)) nomeDoMapa = "Vilarejo Bosqueverde - Centro";
			if (mapas.get(2).equals(mapaAtual)) nomeDoMapa = "Vilarejo Bosqueverde - Plantação";
			if (mapas.get(3).equals(mapaAtual)) nomeDoMapa = "Vilarejo Bosqueverde - Arredores";
		}else nomeDoMapa = VAZIO;
		
		System.out.println(AMARELO+">>"+nomeDoMapa+RESETA);
		desenhaBarra();
	}
    
    public void desenhaErro(String tipoErro, String textoErro){
        if (tipoErro == "Entrada"){
            System.out.print("\n"+VERMELHO+"Insira uma entrada válida."+RESETA);
        }else if (tipoErro == "Movimento"){
            System.out.print(VERMELHO+"Movimento inválido."+RESETA);
        }else if (tipoErro == "Reiniciar"){
			System.out.print("\n"+VERMELHO+"Falha ao reiniciar jogo: "+textoErro+RESETA);
		}
        espera(1490);
      //===
    }
	
	public void desenhaSeta(){
        System.out.print(BRANCO+">"+RESETA);
    }
	
	public void desenhaBarra(){
        System.out.print(CINZA+"============================================"+RESETA+"\n");
    }

    public void limpaTela(){
        try {
            if (os.contains("win")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else if (os.contains("linux") || os.contains("unix")){
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }catch (Exception e){
            System.out.println(VERMELHO+"Falha ao limpar a tela: "+e.getMessage()+"."+RESETA);
        }
      //===
    }

    private void espera(int segundos){
        try{
            TimeUnit.MILLISECONDS.sleep(segundos);
        }catch (InterruptedException e){
            System.out.println(VERMELHO+"Falha no comando esperar: "+e.getMessage()+"."+RESETA);
        }
    }
    
	private void desenhaCaractere(int pos_x, int pos_y){
		for (int linha = 0; linha < mapaAtual.length; linha++){
			for (int coluna = 0; coluna < mapaAtual[linha].length; coluna++){
				for (int i = 0; i <= tipoEventos.size(); i++){
					if(tipoEventos.containsValue(mapaAtual[linha][coluna])){
						eventoAqui = true;
						break;
					}else eventoAqui = false;
				}
				
				if (mapaAtual[linha][coluna] == mapaAtual[pos_x][pos_y]){
                    mapaAtual[pos_x][pos_y] = "@";
					System.out.print(mapaAtual[linha][coluna]);
                }else if (mapaAtual[linha][coluna].equals("~")){ //Água
                    System.out.print(AZUL+mapaAtual[linha][coluna]+RESETA);
                }else if(mapaAtual[linha][coluna].equals(".") || mapaAtual[linha][coluna].equals("#")){
					System.out.print(BRANCO+mapaAtual[linha][coluna]+RESETA);
				}else if(mapaAtual[linha][coluna].equals("¨")){ //Grama
					System.out.print(VERDE+mapaAtual[linha][coluna]+RESETA);
				}else if(mapaAtual[linha][coluna].equals("&")){ //Tocha
					System.out.print(VERMELHO_FOGO+mapaAtual[linha][coluna]+RESETA);
				}else if (eventoAqui == true){
					System.out.print(AMARELO+mapaAtual[linha][coluna]+RESETA);
				}else System.out.print(CINZA+mapaAtual[linha][coluna]+RESETA);
				quantidadeColunasY = coluna;
            }
            System.out.println(""); //Pula para a próxima linha.
        }
      //===
    }
	
    private void desenhaArte(String arte){
        try{
            quadro = Files.readString(Paths.get("Artes\\"+arte+".txt"));
        }catch(IOException e){
            System.out.println(VERMELHO+"Falha ao desenhar arte: "+e.getMessage()+"."+RESETA);
        }
    }
    
    public void desenhaTítulo(){
        System.out.println("");
        desenhaArte("Título");
        System.out.println(AMARELO+quadro+RESETA);
        System.out.println("");
        System.out.print(CINZA+"Autor: "+VERDE_MUSGO+"Carlos S. Rehem"+RESETA+"\n");
		System.out.print(CINZA+"Versão: "+""+BRANCO+nomeVersão+RESETA+"\n");
		espera(100);
		System.out.println("");
		desenhaBarra();
		for (int i = 0; i <= opçõesTítulo.length-1; i++){
			System.out.println(BRANCO+opçõesTítulo[i]+RESETA);
		}
		desenhaBarra();
    }

    public void desenhaComandos(boolean eventoAtivo, String mapaNome, int pos_x, int pos_y, String tipoEvento, int eventoAtualId){		
		System.out.println(CINZA+"============================="+RESETA);
        System.out.println(BRANCO+"1. Esquerda       2. Direita"+RESETA);
        System.out.println(BRANCO+"3. Cima           4. Baixo  "+RESETA);
        System.out.println(BRANCO+"5. Inventário     6. Título "+RESETA);
		if(eventoAtivo) desenhaNomeEvento(eventoAtivo, mapaNome, pos_x, pos_y, tipoEvento, eventoAtualId);
        System.out.println(CINZA+"============================="+RESETA);
    }
	
	private void desenhaNomeEvento(boolean eventoAtivo, String mapaNome, int pos_x, int pos_y, String tipoEvento, int eventoAtualId){
		if(mapas != null && mapas.size() > 0 && mapaNome != "Vazio" && eventoAtivo == true){
			if (tipoEvento.equals("Placa")) System.out.println("7. "+AMARELO+"Ler placa"+RESETA);
			if (tipoEvento.equals("Báu")) System.out.println("7. "+AMARELO+"Abrir báu"+RESETA);
			
			//Vilarejo Bosqueverde - Centro
			if (mapaNome.equals(mapas.get(1))){
				if (eventoAtualId <= 2 && tipoEvento.equals("Transição") && eventoAtualId != -1){
					System.out.println("7. "+AMARELO+"Ir para plantação"+RESETA);
				}
				if (eventoAtualId == 3 && tipoEvento.equals("Loja")){
					System.out.println("7. "+AMARELO+"Loja de armas"+RESETA);
				}
				if (eventoAtualId == 4 && tipoEvento.equals("Loja")){
					System.out.println("7. "+AMARELO+"Loja de consumíveis"+RESETA);
				}
				if (eventoAtualId == 5 && tipoEvento.equals("Transição") || eventoAtualId == 7 && tipoEvento.equals("Transição")){
					System.out.println("7. "+AMARELO+"Bosqueverde_4"+RESETA);
				}
				if (eventoAtualId == 6 && tipoEvento.equals("Transição") || eventoAtualId == 8 && tipoEvento.equals("Transição")){
					System.out.println("7. "+AMARELO+"Ir para arredores"+RESETA);
				}
				if (eventoAtualId == 9 && tipoEvento.equals("Casa")){
					System.out.println("7. "+AMARELO+"Casa da Elisabeth Rehem"+RESETA);
				}
			}
			//Vilarejo Bosqueverde - Plantação
			if (mapaNome.equals(mapas.get(2))){
				if (eventoAtualId == 4 && tipoEvento.equals("Transição") || eventoAtualId == 5 && tipoEvento.equals("Transição")){
					System.out.println("7. "+AMARELO+"Ir para centro"+RESETA);
				}
			}
			//>>Vilarejo Bosqueverde - Arredores
			if (mapaNome.equals(mapas.get(3))){
				if (eventoAtualId <= 3 && tipoEvento.equals("Transição") && eventoAtualId != -1 && eventoAtualId != 1){
					System.out.println("7. "+AMARELO+"Ir para centro"+RESETA);
				}
			}
			
		}
	  //===
	}

    public void desenhaSair(){
        System.out.print(BRANCO+"Fechando");
        espera(1000); System.out.print(BRANCO+".");
        espera(870); System.out.print(BRANCO+".");
        espera(780); System.out.print(BRANCO+".");
        espera(100); limpaTela();
    }
    
    public String getBlocoAtual(int pos_x, int pos_y){
        if (mapaAtual != null){
            texto = mapaAtual[pos_x][pos_y];
        }else texto = VAZIO;
        return texto;
    }
	
	public int getQuantidadeLinhasX(){
		if (mapaAtual == null){
            quantidadeLinhasX = 1;
        }
		return quantidadeLinhasX;
	}
	
	public int getQuantidadeColunasY(){
		if (mapaAtual == null){
            quantidadeColunasY = 1;
        }
		return quantidadeColunasY;
	}
	
  //===
}