import java.util.ArrayList;

public class Eventos{
	private String mapa, tipo;
	private int pos_x, pos_y;
	private static Eventos evento;

	public int getPos_x(){
		return this.pos_x;
	}
	
	public int getPos_y(){
		return this.pos_y;
	}
		
	public String getTipo(){
		return this.tipo;
	}
	
	public void setPos_x(int pos_x){
		this.pos_x = pos_x;
	}
	
	public void setPos_y(int pos_y){
		this.pos_y = pos_y;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public static Eventos criarEventos(String blocoAtual, int linha, int coluna){
			evento = new Eventos();
			evento.setTipo("Vazio");
			evento.setPos_x(linha);
			evento.setPos_y(coluna);
			if(blocoAtual.equals("b")){
				evento.setTipo("báu");
			}else if (blocoAtual.equals("T")){
				evento.setTipo("taverna");
			}else if (blocoAtual.equals("$")){
				evento.setTipo("loja");
			}else if (blocoAtual.equals("*")){
				evento.setTipo("placa");
			}else if (blocoAtual.equals("_")){
				evento.setTipo("transição");
			}
			return evento;
	}
	
  //===
}