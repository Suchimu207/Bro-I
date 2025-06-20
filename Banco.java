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
    private Caractere bloco;
    private String blocoAparência;
    private boolean blocoBloqueio;
	
    private static int posJogador_x, posJogador_y;
    private static int posJogadorAnterior_x, posJogadorAnterior_y;
    
    public Banco(){
        posJogador_x = 3;
        posJogador_y = 9;
        posJogadorAnterior_x = 0;
        posJogadorAnterior_y = 0;
        
        caracteres = new ArrayList<Caractere>();
        
        Caractere hastagBloco = new Caractere("#", true, false);
        caracteres.add(hastagBloco);
        
        Caractere chaveBloco_1 = new Caractere("]", true, false);
        caracteres.add(chaveBloco_1);
        
        Caractere chaveBloco_2 = new Caractere("[", true, false);
        caracteres.add(chaveBloco_2);
        
        Caractere águaBloco = new Caractere("~", true, false);
        caracteres.add(águaBloco);
		
		Caractere báuBloco = new Caractere("b", false, true);
        caracteres.add(báuBloco);
		
		Caractere lojaBloco = new Caractere("$", false, true);
        caracteres.add(lojaBloco);
		
		Caractere tavernaBloco = new Caractere("T", false, true);
        caracteres.add(tavernaBloco);
		
		Caractere placaBloco = new Caractere("*", false, true);
        caracteres.add(placaBloco);
		
		Caractere transiçãoBloco = new Caractere("_", false, true);
        caracteres.add(transiçãoBloco);
    }
    
	public static void resetaInformações(){
		posJogador_x = 3;
        posJogador_y = 9;
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