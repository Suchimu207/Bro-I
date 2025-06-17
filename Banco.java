import java.util.ArrayList;

public final class Banco{
    private class Caractere{
        private String aparência;
        private boolean bloqueio;
        public Caractere(String aparência, boolean bloqueio){
            this.aparência = aparência;
            this.bloqueio = bloqueio;
        }
        public String getAparência(){ 
            return this.aparência;
        }
        public boolean getBloqueio(){
            return this.bloqueio;
        } 
    }
    
    private ArrayList<Caractere> caracteres;
    private Caractere bloco;
    private String blocoAparência;
    private boolean blocoBloqueio;
    
    private int posJogador_x, posJogador_y;
    private int posJogadorAnterior_x, posJogadorAnterior_y;
    
    public Banco(){
        posJogador_x = 3;
        posJogador_y = 9;
        posJogadorAnterior_x = 0;
        posJogadorAnterior_y = 0;
        
        caracteres = new ArrayList<Caractere>();
        
        Caractere hastagBloco = new Caractere("#", true);
        caracteres.add(hastagBloco);
        
        Caractere chaveBloco_1 = new Caractere("]", true);
        caracteres.add(chaveBloco_1);
        
        Caractere chaveBloco_2 = new Caractere("[", true);
        caracteres.add(chaveBloco_2);
        
        Caractere ÁguaBloco = new Caractere("~", true);
        caracteres.add(ÁguaBloco);
    }
    
    public int getJogador_x(){
        return this.posJogador_x;
    }
    
    public int getJogador_y(){
        return this.posJogador_y;
    }
    
    public void setJogador_x(int posJogador_x){
        this.posJogador_x = posJogador_x;
    }
    
    public void setJogador_y(int posJogador_y){
        this.posJogador_y = posJogador_y;
    }
    
    public void setJogadorAnterior_x(int posJogador_x){
        this.posJogadorAnterior_x = posJogador_x;
    }
    
    public int getJogadorAnterior_x(){
        return this.posJogadorAnterior_x;
    }
    
    public int getJogadorAnterior_y(){
        return this.posJogadorAnterior_y;
    }
    
    public void setJogadorAnterior_y(int posJogador_y){
        this.posJogadorAnterior_y = posJogador_y;
    }

    public boolean getBloqueio(String blocoAtual){
        for (int i = 0; i <= caracteres.size()-1; i++){
            bloco = caracteres.get(i);
            blocoBloqueio = bloco.getBloqueio(); 
            blocoAparência = bloco.getAparência();
            if (blocoAparência.equals(blocoAtual)){
                blocoBloqueio = true; break;
            }blocoBloqueio = false;
        }
        return blocoBloqueio;
    }

  //===
}