import java.util.Hashtable;
import java.util.ArrayList;

public final class Banco{
    private class Caractere{
        private String aparência;
        private boolean bloqueio, evento;
        public Caractere(String aparência, boolean bloqueio, boolean evento){
            this.aparência = aparência;
            this.bloqueio = bloqueio;
			this.evento = evento;
        }
        public String getAparência(){ 
            return this.aparência;
        }
        public boolean getBloqueio(){
            return this.bloqueio;
        } 
		public boolean getEvento(){
			return this.evento;
		}
    }
    
    private ArrayList<Caractere> caracteres;
	private Hashtable tipoEventos;
    private Caractere bloco;
    private String blocoAparência;
    private boolean blocoBloqueio;
	
    private static int posJogador_x, posJogador_y, 
	posInicialJogador_x, posInicialJogador_y,
	posJogadorAnterior_x, posJogadorAnterior_y;
    
    public Banco(Hashtable tipoEventos, int posJogador_x, int posJogador_y){
        this.posJogador_x = posJogador_x;
        this.posJogador_y = posJogador_y;
		this.posInicialJogador_x = posJogador_x;
        this.posInicialJogador_y = posJogador_y;
		this.tipoEventos = tipoEventos;
		
        posJogadorAnterior_x = 0;
        posJogadorAnterior_y = 0;
        
        caracteres = new ArrayList<Caractere>();
		criarBlocos();		
    }
    
	private void criarBlocos(){
		bloco = new Caractere("#", true, false); 
        caracteres.add(bloco);
        
        bloco = new Caractere("]", true, false);
        caracteres.add(bloco);
        
        bloco = new Caractere("[", true, false);
        caracteres.add(bloco);
        
        bloco = new Caractere("~", true, false);
        caracteres.add(bloco);
		
		bloco = new Caractere("&", true, false);
        caracteres.add(bloco);
		
		blocoAparência = tipoEventos.get("Báu").toString();
		bloco = new Caractere(blocoAparência, false, true);
        caracteres.add(bloco);
		
		blocoAparência = tipoEventos.get("Loja").toString();
		bloco = new Caractere(blocoAparência, false, true);
        caracteres.add(bloco);
		
		blocoAparência = tipoEventos.get("Taverna").toString();
		bloco = new Caractere(blocoAparência, false, true);
        caracteres.add(bloco);
		
		blocoAparência = tipoEventos.get("Transição").toString();
		bloco = new Caractere(blocoAparência, false, true);
        caracteres.add(bloco);
		
		blocoAparência = tipoEventos.get("Placa").toString();
		bloco = new Caractere(blocoAparência, false, true);
        caracteres.add(bloco);
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

    public boolean getBloqueio(String blocoAtual){
        for (int i = 0; i <= caracteres.size()-1; i++){
            bloco = caracteres.get(i);
			blocoAparência = bloco.getAparência();
			if (blocoAparência.equals(blocoAtual)){
                blocoBloqueio = bloco.getBloqueio(); break;
            }else blocoBloqueio = false;
        }
        return blocoBloqueio;
    }
		
  //===
}