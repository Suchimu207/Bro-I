import java.io.IOException;
import java.nio.file.Files;   
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.List;

public final class Visual{
    private String quadro;
    private String[][] mapaAtual;
    
    private String os, título;
    private int saída;
    private final String verde, branco, vermelho, laranja, azul, roxo, amarelo, rosa, reseta;
    
    public Visual(){
        os = System.getProperty("os.name").toLowerCase();
        título = "Bró I";
        
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
            System.out.println(vermelho+"Ocorreu um erro: "+e.getMessage()+"."+reseta); 
            espera(2000);
            }
        }
    }
    
    public void desenhaMenu(String menu){
        if (menu == "Título"){
            desenhaTítulo(); 
        }else if (menu == "Comandos"){
            desenhaComandos();
        }else if (menu == "Seta"){
            desenhaSeta();
        }else if(menu == "Sair"){
            desenhaSair();
        }else if (menu == "LimpaTela"){
			limpaTela();
		}	
      //=== 
    }
    
    public void desenhaMapa(String mapa, int pos_x, int pos_y){
        try{
			List<String> linhas = Files.readAllLines(Paths.get("Mapas\\"+mapa+".txt"));
            
            mapaAtual = new String[linhas.size()][];
            for (int i = 0; i < linhas.size(); i++) mapaAtual[i] = linhas.get(i).split("");
        
			System.out.println("");
            for (int linha = 0; linha < mapaAtual.length; linha++) {
            for (int coluna = 0; coluna < mapaAtual[linha].length; coluna++){
                if (mapaAtual[linha][coluna] == mapaAtual[pos_x][pos_y]){
                    mapaAtual[pos_x][pos_y] = "@";
                }
				System.out.print(mapaAtual[linha][coluna]);
            }
            System.out.println("");
        }
        }catch(IOException e){
            System.out.println(vermelho+"Falha ao desenhar mapa: "+e.getMessage()+"."+reseta);
        }
    }
    
    private void limpaTela(){
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
    
    private void espera(int segundos){
        try{
            TimeUnit.MILLISECONDS.sleep(segundos);
        }catch (InterruptedException e){
            System.out.println(vermelho+"Falha no comando esperar: "+e.getMessage()+"."+reseta);
        }
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
        System.out.print(branco+"Desenvolvido por "+verde+"Carlos S. Rehem"+branco+" - 2025"+reseta+"\n");
		espera(100);
		System.out.println("");
		desenhaBarra();
		System.out.println("1. Novo jogo");
        System.out.println("2. Continuar");
        System.out.println("3. Sair");
		desenhaBarra();
    }

    private void desenhaComandos(){
        System.out.println(branco+"============================="+reseta);
        System.out.println("1. Esquerda       2. Direita");
        System.out.println("3. Cima           4. Baixo  ");
        System.out.println("5. Inventário     6. Título ");
        System.out.println(branco+"============================="+reseta);
    }
    
    private void desenhaSeta(){
        System.out.print(branco+">"+reseta);
    }

    private void desenhaBarra(){
        System.out.print(branco+"============================================"+reseta+"\n");
      }

    private void desenhaSair(){
        System.out.print("\n"+branco+"Fechando");
        espera(1000); System.out.print(branco+".");
        espera(870); System.out.print(branco+".");
        espera(780); System.out.print(branco+".");
        espera(100); limpaTela();
    }
    
  //===    
}