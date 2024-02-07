package home.projet_cdc;

public class PorteSpy implements PorteInterface{
    public PorteSpy(){
    }

    private boolean _ouvertureDemandee = false;

    private boolean estBloque = false;

    public boolean isEstBloque() {
        return estBloque;
    }

    @Override
    public void bloquer() {
        this.estBloque = true;
    }

    @Override
    public void debloquer() {
        this.estBloque = false;
    }

    public boolean verifierOuvertureDemandee() {
        return _ouvertureDemandee;
    }

    @Override
    public void ouvrir() {
        _ouvertureDemandee = true;
    }
}
