package home.projet_cdc;

public class Badge {


    private boolean estBloque = false;

    public boolean isEstBloque() {
        return estBloque;
    }

    public void bloque() {
        this.estBloque = true;
    }

    public void debloque() {
        this.estBloque = false;
    }

}
