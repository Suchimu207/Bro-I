import java.util.ArrayList;

public class Eventos{
	private int id;
    private String tipo;
    private int pos_x, pos_y;
    private Object dados; // Dados específicos do evento
	
	public Eventos(int id, String tipo, int pos_x, int pos_y, Object dados) {
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
		this.tipo = tipo;
        this.dados = dados;
    }
	
	public int getId(){
		return this.id;
	}
	
	public int getPos_x(){
		return this.pos_x;
	}
	
	public int getPos_y(){
		return this.pos_y;
	}
		
	public String getTipo(){
		return this.tipo;
	}
	
	public Object getDados(){
		return this.dados;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public static Eventos criarEvento(String blocoAtual, int linha, int coluna, int id){
		Object dadosEvento = null;
        String tipoEvento = "";
        
        if(blocoAtual.equals("b")){
            tipoEvento = "báu";
            dadosEvento = "Este é um báu.";
        }else if(blocoAtual.equals("*")){
            tipoEvento = "placa";
            dadosEvento = "Na natureza, a luz cria a cor..."; 
        }else if (blocoAtual.equals("$")){
			tipoEvento = "loja";
            dadosEvento = "Esta é uma loja."; 
		}else if(blocoAtual.equals("T")){
			tipoEvento = "taverna";
			dadosEvento = "Esta é uma taverna.";
		}else if(blocoAtual.equals("_")){
			tipoEvento = "transição";
			dadosEvento = "Esta é uma transição.";
		}
        
        return new Eventos(id, tipoEvento, linha, coluna, dadosEvento);
	}
	
  //===
}