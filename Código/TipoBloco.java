public enum TipoBloco {
    PAREDE("#", true, false),
    PAREDE_DIREITA("]", true, false),
    PAREDE_ESQUERDA("[", true, false),
    AGUA("~", true, false),
    TOCHA("&", true, false),
    BAU("b", false, true),
    LOJA("$", false, true),
    TAVERNA("T", false, true),
    TRANSICAO("_", false, true),
    PLACA("*", false, true),
    CASA("H", false, true),
    GRAMA("¨", false, false),
    VAZIO(".", false, false),
    JOGADOR("@", false, false);

    private final String simbolo;
    private final boolean bloqueante;
    private final boolean evento;

    TipoBloco(String simbolo, boolean bloqueante, boolean evento){
        this.simbolo = simbolo;
        this.bloqueante = bloqueante;
        this.evento = evento;
    }

    public String getSimbolo(){
        return simbolo;
    }

    public boolean isBloqueante(){
        return bloqueante;
    }

    public boolean isEvento(){
        return evento;
    }

    public static TipoBloco procuraBloco(String simbolo){
        for (TipoBloco tipo : values()){
            if (tipo.getSimbolo().equals(simbolo)){
                return tipo;
            }
        }
        return VAZIO;
    }
	
	public static boolean isExisteBloco(String simbolo){
        for (TipoBloco tipo : values()){
            if (tipo.getSimbolo().equals(simbolo) && tipo.isEvento() == true){
                return true;
            }
        }
        return false;
    }

  //===	
}