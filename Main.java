import java.util.Scanner;

/*
* @author Carlos S. Rehem
* Bró I
* Protagonistas:
* Elisabeth Rehem (vermelho), Lisa.
* Neromilia Silva (branco), Nero.
* Takahashi Yosai (verde musgo), Yosai.
* Geny Arado (laranja), Geny.

"Na natureza, a luz cria a cor; na pintura, a cor cria a luz. Cada tom de cor 
emana uma luz muito característica - não há substituto possível." ~ Hans Hofmann 
(plaquinha da cidade inicial).
*/

//TODO: sistema de eventos e inventário; loja, taverna, baú e porta.

public class Main{
    public static void main(String[] args){
        Visual visualg = new Visual();
        Banco arquivista = new Banco();
        Scanner teclado = new Scanner(System.in);
        Controle mestre = new Controle(visualg, arquivista, teclado);
        
      //===
    }
}
