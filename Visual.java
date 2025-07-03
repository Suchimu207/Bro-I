import java.io.IOException;
import java.nio.file.Files;   
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.List;

public final class Visual{
    private String quadro;
    private String[][] mapaAtual;
	private String[] tipoEventos;
    
    private String os, título, texto;
    private int saída, quantidadeLinhasX, quantidadeColunasY;
    private final String verde, branco, vermelho, laranja, azul, roxo, amarelo, rosa, reseta;
	
	private boolean eventoAqui;
    
    public Visual(String[] tipoEventos){
		título = "Bró I";
        os = System.getProperty("os.name").toLowerCase();
        this.tipoEventos = tipoEventos;
		
        verde = "\033[92m";
        branco = "\033[37m";
        vermelho = "\033[31m";
        laranja = "\033[33m";
        azul = "\033[34m";
        roxo = "\033[35m";
        amarelo = "\033[93m";
        rosa = "\033[95m";
        reseta = "\u001B[0m";
        
        if (os.contains("win")){
            try{
            new ProcessBuilder("cmd", "/c", "title " + título).inheritIO().start();
        }catch (Exception e){
            System.out.println(vermelho+"Ocorreu um erro ao limpar a tela: "+e.getMessage()+"."+reseta); 
            espera(1500);
            }
        }
    }
    
    public void desenhaMenu(String menu, boolean eventoAtivo, String mapaAtual, int pos_x, int pos_y, String tipoEvento){
        if (menu == "Título"){
            tipoEvento = "";
			desenhaTítulo(); 
        }else if (menu == "Comandos"){
            desenhaComandos(eventoAtivo, mapaAtual, pos_x, pos_y, tipoEvento);
        }
      //===
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
            System.out.println(vermelho+"Falha ao desenhar mapa: "+e.getMessage()+"."+reseta);
        }
    }
    
    public void desenhaErro(String erro){
        if (erro == "Entrada"){
            System.out.println(vermelho+"Insira uma entrada válida."+reseta);
            espera(1500);
        }else if (erro == "Movimento"){
            System.out.println(vermelho+"Movimento inválido."+reseta);
        }
        espera(1500);
      //===
    }
	
	public void desenhaSeta(){
        System.out.print(branco+">"+reseta);
    }
	
	public void desenhaBarra(){
        System.out.print(branco+"============================================"+reseta+"\n");
      }

    public void limpaTela(){
        try {
            if (os.contains("win")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else if (os.contains("linux") || os.contains("unix")){
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }catch (Exception e){
            System.out.println(vermelho+"Falha ao limpar a tela: "+e.getMessage()+"."+reseta);
        }
      //===
    }
    
	public void reiniciarJogo(){
        try {
            if (os.contains("win")){
                new ProcessBuilder("cmd", "/c", "java Main.java").inheritIO().start().waitFor();
				System.exit(0);
            }else if (os.contains("linux") || os.contains("unix")){
                new ProcessBuilder("java Main.java").inheritIO().start().waitFor();
            }
        }catch (Exception e){
            System.out.println(vermelho+"Falha ao reiniciar jogo: "+e.getMessage()+"."+reseta);
        }
      //===
    }
	
    private void espera(int segundos){
        try{
            TimeUnit.MILLISECONDS.sleep(segundos);
        }catch (InterruptedException e){
            System.out.println(vermelho+"Falha no comando esperar: "+e.getMessage()+"."+reseta);
        }
    }
    
	private void desenhaCaractere(int pos_x, int pos_y){
        for (int linha = 0; linha < mapaAtual.length; linha++) {
			for (int coluna = 0; coluna < mapaAtual[linha].length; coluna++){
				for (int i = 0; i <= tipoEventos.length-1; i++){
					if(mapaAtual[linha][coluna].equals(tipoEventos[i])){
						eventoAqui = true;
						break;
					}else eventoAqui = false;
				}
				
				if (mapaAtual[linha][coluna] == mapaAtual[pos_x][pos_y]){
                    mapaAtual[pos_x][pos_y] = "@";
					System.out.print(mapaAtual[linha][coluna]);
                }else if (mapaAtual[linha][coluna].equals("~")){
                    System.out.print(azul+mapaAtual[linha][coluna]+reseta);
                }else if (eventoAqui == true){
					System.out.print(amarelo+mapaAtual[linha][coluna]+reseta);
				}else System.out.print(branco+mapaAtual[linha][coluna]+reseta);
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
            System.out.println(vermelho+"Falha ao desenhar arte: "+e.getMessage()+"."+reseta);
        }
    }
    
    private void desenhaTítulo(){
        System.out.println("");
        desenhaArte("Título");
        System.out.println(amarelo+quadro+reseta);
        System.out.println("");
        System.out.print(branco+"Desenvolvido por "+verde+"Carlos S. Rehem"+reseta+"\n");
		System.out.print(branco+"Versão: 0.2"+reseta+"\n");
		espera(100);
		System.out.println("");
		desenhaBarra();
		System.out.println("1. Novo jogo");
        System.out.println("2. Continuar");
        System.out.println("3. Sair");
		desenhaBarra();
    }

    private void desenhaComandos(boolean eventoAtivo, String mapaNome, int pos_x, int pos_y, String tipoEvento){
        System.out.println(branco+"============================="+reseta);
        System.out.println("1. Esquerda       2. Direita");
        System.out.println("3. Cima           4. Baixo  ");
        System.out.println("5. Inventário     6. Título ");
		if(eventoAtivo) desenhaNomeEvento(eventoAtivo, mapaNome, pos_x, pos_y, tipoEvento);
        System.out.println(branco+"============================="+reseta);
    }
	
	private void desenhaNomeEvento(boolean eventoAtivo, String mapaNome, int pos_x, int pos_y, String tipoEvento){
		if (eventoAtivo == true && tipoEvento.equals("báu") && mapaNome == "Teste"){
			System.out.println("7. Abrir báu");
		}else if (eventoAtivo == true && tipoEvento.equals("loja") && mapaNome == "Teste"){
			System.out.println("7. Entrar na loja");
		}else if (eventoAtivo == true && tipoEvento.equals("taverna") && mapaNome == "Teste"){
			System.out.println("7. Entrar na taverna");
		}else if (eventoAtivo == true && tipoEvento.equals("transição") && mapaNome == "Teste"){
			System.out.println("7. Transição mapa");
		}else if (eventoAtivo == true && tipoEvento.equals("placa") && mapaNome == "Teste"){
			System.out.println("7. Ler placa");
		}
	}

    public void desenhaSair(){
        System.out.print("\n"+branco+"Fechando");
        espera(1000); System.out.print(branco+".");
        espera(870); System.out.print(branco+".");
        espera(780); System.out.print(branco+".");
        espera(100); limpaTela();
    }
    
    public String getBlocoAtual(int pos_x, int pos_y){
        if (mapaAtual == null){
            texto = "";
        }else texto = mapaAtual[pos_x][pos_y];
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