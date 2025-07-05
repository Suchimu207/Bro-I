import java.util.Hashtable;
import java.util.ArrayList;

public class Eventos{
	private static Hashtable tipoDosEventos;
	
	private int id;
	private String tipo;
	private static String tipoEvento;

    private int pos_x, pos_y;
	
    private Object dados; 
	private static Object dadosEvento;
	
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
	
	public static Eventos criarEvento(String blocoAtual, int linha, int coluna, int id, Hashtable tipoEventos){
		tipoDosEventos = tipoEventos;
		tipoEvento = "";
		dadosEvento = "Este é um evento.";
      
		//TODO: Melhorar este troço abaixo.
		if (tipoDosEventos.containsValue(blocoAtual)){
			if(blocoAtual.equals("b")){
				tipoEvento = "Báu";
			}else if(blocoAtual.equals("*")){
				tipoEvento = "Placa";
			}else if (blocoAtual.equals("$")){
				tipoEvento = "Loja";
			}else if(blocoAtual.equals("T")){
				tipoEvento = "Taverna";
			}else if(blocoAtual.equals("_")){
				tipoEvento = "Transição";
			}
		}
        return new Eventos(id, tipoEvento, linha, coluna, dadosEvento);
	}
	
  //===
}