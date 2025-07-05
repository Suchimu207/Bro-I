import java.util.Scanner;
import java.util.Hashtable;

/*
* @autor Carlos S. Rehem
* Bró I
*/

public class Main{
    public static void main(String[] args){
		String título = "Bró I";
		String nomeVersão = "0.3";
		String[] opçõesTítulo = {"1. Novo Jogo", "2. Continuar", "3. Sair"};
		
		Hashtable<String, String> tipoEventos = new Hashtable<String, String>();
		tipoEventos.put("Báu","b");
		tipoEventos.put("Loja","$");
		tipoEventos.put("Taverna","T");
		tipoEventos.put("Transição","_");
		tipoEventos.put("Placa","*");
	
		int posJogador_x = 3;
		int posJogador_y = 9;
		
		Banco arquivista = new Banco(tipoEventos, posJogador_x, posJogador_y);
		Visual visualg = new Visual(tipoEventos, nomeVersão, título, opçõesTítulo); 
	
		Scanner teclado = new Scanner(System.in);
        Controle mestre = new Controle(visualg, arquivista, teclado, tipoEventos);
    }
}
