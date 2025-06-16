import java.util.Scanner;

/*
* @author Carlos S. Rehem
* Bró I
* Protagonistas:
* Elisabeth Rehem (vermelho), Lisa.
* Neromilia Silva (branco), Nero.
* Takahashi Yosai (verde musgo), Yosai.
* Geny Arado (laranja), Geny.
*/

public class Main{
    public static void main(String[] args){
        Visual visualg = new Visual();
        Banco arquivista = new Banco();
        Scanner teclado = new Scanner(System.in);
        Controle mestre = new Controle(visualg, arquivista, teclado);
        
      //===
    }
}
