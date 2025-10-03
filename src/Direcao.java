public enum Direcao{
    ESQUERDA(1, 0, -1),
    DIREITA(2, 0, 1),
    CIMA(3, -1, 0),
    BAIXO(4, 1, 0);

    private final int codigo;
    private final int deltaX;
    private final int deltaY;

    Direcao(int codigo, int deltaX, int deltaY){
        this.codigo = codigo;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getCodigo(){
        return codigo;
    }

    public int getDeltaX(){
        return deltaX;
    }

    public int getDeltaY(){
        return deltaY;
    }

    public static Direcao procuraDirecao(int codigo){
        for (Direcao dir : values()) {
            if (dir.getCodigo() == codigo) {
                return dir;
            }
        }
        return null;
    }
  //===	
}