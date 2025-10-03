import java.util.Scanner;
import java.util.Properties;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;

/*
* @autor Carlos S. Rehem
* Bró I
*/

//Problemas conhecidos: transição de mapa inconsistente no Bosqueverde_4.

public class Main{
	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream arquivo = new FileInputStream(
				"./System/config.properties");
		props.load(arquivo);
		return props;
	}
	
    public static void main(String[] args) throws IOException{	
		Properties config = getProp();

		String título = "Bró I";
		String nomeVersão = config.getProperty("game.version");
		
		String debugInicialValor = config.getProperty("initial.debug");
		boolean debugInicial;
		
		if (debugInicialValor.equals("false")){
			debugInicial = false;
		}else debugInicial = true;
		
		String[] opçõesTítulo = {"1. Novo Jogo", "2. Continuar", "3. Sair"};
		String os = System.getProperty("os.name").toLowerCase();
		final String VAZIO = "Vazio";
		
		ArrayList<String> mapas = new ArrayList<String>();
		mapas.add("Teste_0");
		mapas.add("Bosqueverde_1");
		mapas.add("Bosqueverde_2");
		mapas.add("Bosqueverde_3");
		mapas.add("Bosqueverde_4");
		mapas.add("IlhaParaíso_5");
		mapas.add("IlhaParaíso_6");
		mapas.add("IlhaParaíso_7");
		
		Eventos.setMapas(mapas);
		
		String mapaInicial = config.getProperty("initial.map");
		int posJogador_x = Integer.parseInt(config.getProperty("initial.player.x"));
		int posJogador_y = Integer.parseInt(config.getProperty("initial.player.y"));
		
		Scanner teclado = new Scanner(System.in);
		Banco arquivista = new Banco(posJogador_x, posJogador_y);
		Visual visualg = new Visual(nomeVersão, título, opçõesTítulo, mapas, VAZIO, os);
		Controle mestre = new Controle(visualg, arquivista, teclado, mapaInicial, mapas, VAZIO, os, debugInicial);
    }
}