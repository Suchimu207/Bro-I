public final class Banco{
    private int posJogador_x, posJogador_y;
    
    public Banco(){
        posJogador_x = 3;
        posJogador_y = 3;
    }
    
    public int getJogador_x(){
        return this.posJogador_x;
    }
    
    public int getJogador_y(){
        return this.posJogador_y;
    }
    
    public void setJogador_x(int posJogador_x ){
        this.posJogador_x = posJogador_x;
    }
    
    public void setJogador_y(int posJogador_y ){
        this.posJogador_y = posJogador_y;
    }

  //=== 
}