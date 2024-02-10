package home.projet_cdc;

public class Badge {

    private Integer id = 0;
    private boolean estBloque = false;

    public Badge(Integer id) {
        this.id = id;
    }

    public boolean isEstBloque() {
        return estBloque;
    }

    public Integer getId() {
        return id;
    }

    public void bloque() {
        //this.estBloque = true;
    }

    public void debloque() {
        this.estBloque = false;
    }

}
