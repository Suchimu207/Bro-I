import java.util.ArrayList;

public final class Banco{
    private TipoBloco[] blocosImportantes = {
        TipoBloco.PAREDE, TipoBloco.PAREDE_DIREITA, TipoBloco.PAREDE_ESQUERDA,
        TipoBloco.AGUA, TipoBloco.TOCHA, TipoBloco.BAU, TipoBloco.LOJA,
        TipoBloco.TAVERNA, TipoBloco.TRANSICAO, TipoBloco.PLACA, TipoBloco.CASA
    };

    public boolean getBloqueio(String blocoAtual) {
        TipoBloco tipo = TipoBloco.procuraBloco(blocoAtual);
        return tipo.isBloqueante();
    }
    
    private String blocoAparência;
    private boolean blocoBloqueio;
	
	//Informações do jogador.
    private static int posJogador_x, posJogador_y, 
	posInicialJogador_x, posInicialJogador_y,
	posJogadorAnterior_x, posJogadorAnterior_y;
    
    public Banco(int posJogador_x, int posJogador_y){
        this.posJogador_x = posJogador_x;
        this.posJogador_y = posJogador_y;
		this.posInicialJogador_x = posJogador_x;
        this.posInicialJogador_y = posJogador_y;
		
        this.posJogadorAnterior_x = 0;
        this.posJogadorAnterior_y = 0;	
    }
	
	public static void resetaInformaçõesJogador(){
		posJogador_x = posInicialJogador_x;
        posJogador_y = posInicialJogador_y;
	}
	
    public static int getJogador_x(){
        return posJogador_x;
    }
    
    public static int getJogador_y(){
        return posJogador_y;
    }
    
    public static void setJogador_x(int posiçãoJogador_x){
        posJogador_x = posiçãoJogador_x;
    }
    
    public static void setJogador_y(int posiçãoJogador_y){
        posJogador_y = posiçãoJogador_y;
    }
    
    public static void setJogadorAnterior_x(int posiçãoJogador_x){
        posJogadorAnterior_x = posiçãoJogador_x;
    }
    
    public static int getJogadorAnterior_x(){
        return posJogadorAnterior_x;
    }
    
    public static int getJogadorAnterior_y(){
        return posJogadorAnterior_y;
    }
    
    public static void setJogadorAnterior_y(int posiçãoJogador_y){
        posJogadorAnterior_y = posiçãoJogador_y;
    }
	
  //===
}