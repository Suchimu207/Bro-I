import java.util.Scanner;
import java.util.Hashtable;
import java.util.ArrayList;

/*
* @autor Carlos S. Rehem
* Bró I
*/

public class Main{
    public static void main(String[] args){
		String título = "Bró I";
		String nomeVersão = "0.32";
		String[] opçõesTítulo = {"1. Novo Jogo", "2. Continuar", "3. Sair"};
		final String VAZIO = "Vazio";
		
		Hashtable<String, String> tipoEventos = new Hashtable<String, String>();
		tipoEventos.put("Báu","b");
		tipoEventos.put("Loja","$");
		tipoEventos.put("Taverna","T");
		tipoEventos.put("Transição","_");
		tipoEventos.put("Placa","*");
		tipoEventos.put("Casa","H");
	
		ArrayList<String> mapas = new ArrayList<String>();
		mapas.add("Teste_0");
		mapas.add("Bosqueverde_1");
		mapas.add("Bosqueverde_2");
		mapas.add("Bosqueverde_3");
		
		String mapaInicial = mapas.get(1);
		int posJogador_x = 8;
		int posJogador_y = 2;
		
		Scanner teclado = new Scanner(System.in);
		Banco arquivista = new Banco(tipoEventos, posJogador_x, posJogador_y);
		Visual visualg = new Visual(tipoEventos, nomeVersão, título, opçõesTítulo, mapas, VAZIO);
		Controle mestre = new Controle(visualg, arquivista, teclado, tipoEventos, mapaInicial, mapas, VAZIO);
    }
}
