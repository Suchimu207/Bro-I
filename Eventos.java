import java.util.Hashtable;
import java.util.ArrayList;

//Responsável pelos eventos chamados pelo jogador.

public class Eventos{
	private static Hashtable tipoDosEventos;
	private static String tipoEvento, mapaNome, textoPlaca;
	private static ArrayList<String> mapas;
	private static int eventoAtualId, posJogador_x, posJogador_y;
	
	private int id;
	private String tipo;
    private int posEvento_x, posEvento_y;
	
	public Eventos(int id, String tipo, int posEvento_x, int posEvento_y){
        this.id = id;
        this.posEvento_x = posEvento_x;
        this.posEvento_y = posEvento_y;
		this.tipo = tipo;
    }
	
	public int getId(){
		return this.id;
	}
	
	public int getPosEvento_x(){
		return this.posEvento_x;
	}
	
	public int getPosEvento_y(){
		return this.posEvento_y;
	}
		
	public String getTipo(){
		return this.tipo;
	}
	
	public static void setMapas(ArrayList<String> mapasDoJogo){
		mapas = mapasDoJogo;
	}
	
	public static String getMapaNome(){	
		return mapaNome;
	}
	
	public static int getPosJogador_x(){
		return posJogador_x;
	}
	
	public static int getPosJogador_y(){
		return posJogador_y;
	}
	
	public static Eventos criarEvento(String blocoAtual, int linha, int coluna, int id, Hashtable tipoEventos){
		tipoDosEventos = tipoEventos;
		tipoEvento = "";
		
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
			}else if (blocoAtual.equals("H")){
				tipoEvento = "Casa";
			}
		}
        return new Eventos(id, tipoEvento, linha, coluna);
	}
	
	public static void chamarTransição(String nomeDoMapa, int atualIdEvento, int posiçãoJogador_x, int posiçãoJogador_y){
			mapaNome = nomeDoMapa;
			eventoAtualId = atualIdEvento;
			posJogador_x = posiçãoJogador_x;
			posJogador_y = posiçãoJogador_y;
				
			//Vilarejo Bosqueverde - Centro
			if (mapaNome.equals(mapas.get(1))){
				if (eventoAtualId == 1){
					mapaNome = mapas.get(2);
					posJogador_x = 12;
					posJogador_y = 13;
				}
				if (eventoAtualId == 2){
					mapaNome = mapas.get(2);
					posJogador_x = 12;
					posJogador_y = 14;
				}
				if (eventoAtualId == 5){
					mapaNome = mapas.get(4);
					posJogador_x = 5;
					posJogador_y = 27;
				}
				if (eventoAtualId == 6){
					mapaNome = mapas.get(3);
					posJogador_x = 5;
					posJogador_y = 1;
				}
				if (eventoAtualId == 7){
					mapaNome = mapas.get(4);
					posJogador_x = 6;
					posJogador_y = 27;
				}
				if (eventoAtualId == 8){
					mapaNome = mapas.get(3);
					posJogador_x = 6;
					posJogador_y = 1;
				}
			}
			//Vilarejo Bosqueverde - Plantação
			if (mapaNome.equals(mapas.get(2))){
				if (eventoAtualId == 4){
					mapaNome = mapas.get(1);
					posJogador_x = 1;
					posJogador_y = 13;
				}
				if (eventoAtualId == 5){
					mapaNome = mapas.get(1);
					posJogador_x = 1;
					posJogador_y = 14;
				}
			}
			//>>Vilarejo Bosqueverde - Arredores
			if (mapaNome.equals(mapas.get(3))){
				if (eventoAtualId == 2){
					mapaNome = mapas.get(1);
					posJogador_x = 5;
					posJogador_y = 27;
				}
				if (eventoAtualId == 3){
					mapaNome = mapas.get(1);
					posJogador_x = 6;
					posJogador_y = 27;
				}
			}
			//>>Vilarejo Bosqueverde - Estrada
			if (mapaNome.equals(mapas.get(4))){
				if (eventoAtualId == 4){
					mapaNome = mapas.get(1);
					posJogador_x = 5;
					posJogador_y = 1;
				}
				if (eventoAtualId == 6){
					mapaNome = mapas.get(1);
					posJogador_x = 6;
					posJogador_y = 1;
				}
			}
	  //===
	}
	
	public static String chamarPlaca(String nomeDoMapa, int atualIdEvento){
		mapaNome = nomeDoMapa;
		eventoAtualId = atualIdEvento;
		textoPlaca = "[...]";
		
			//Vilarejo Bosqueverde - Centro
			if (mapaNome.equals(mapas.get(1))){
				if (eventoAtualId == 10){
					textoPlaca = """
					"Na natureza, a luz cria a cor; na pintura, a cor cria a luz. Cada tom de cor 
					emana uma luz muito característica - não há substituto possível."
					-Hans Hofmann """;
				}
			}
			//>>Vilarejo Bosqueverde - Arredores
			if (mapaNome.equals(mapas.get(3))){
				if (eventoAtualId == 1){
					textoPlaca = """
					"Seja feliz por este momento. Este
					momento é a sua vida." """;
				}
				if (eventoAtualId == 4){
					textoPlaca = """
					"Acredite nos sonhos, pois neles jazem
					os portões para a eternidade." """;
				}
				if (eventoAtualId == 5){
					textoPlaca = """
					"Morte é o desejo de alguns, o alívio de
					muitos, e o fim de todos." """;
				}
			}
			
		return textoPlaca;
	}
	
  //===
}