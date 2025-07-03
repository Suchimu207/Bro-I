import java.util.Scanner;

/*
* @author Carlos S. Rehem
* Bró I
*/

public class Main{
    public static void main(String[] args){
		String[] tipoEventos = {"b","$","T","_","*"};
		
        Visual visualg = new Visual(tipoEventos);
        Banco arquivista = new Banco();
        Scanner teclado = new Scanner(System.in);

        Controle mestre = new Controle(visualg, arquivista, teclado, tipoEventos);
    }
}
