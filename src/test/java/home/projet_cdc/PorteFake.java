package home.projet_cdc;

public class PorteFake implements PorteInterface{

    private boolean estBloque;

    public PorteFake(boolean estBloque){
        this.estBloque = estBloque;
    }

    @Override
    public void ouvrir() {

    }

    @Override
    public void bloquer() {
        this.estBloque = true;
    }

    @Override
    public void debloquer() {
        this.estBloque = false;
    }

    @Override
    public boolean isEstBloque() {
        return this.estBloque;
    }
}
