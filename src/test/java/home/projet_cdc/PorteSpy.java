package home.projet_cdc;

public class PorteSpy implements PorteInterface{
    public PorteSpy(){
    }

    private boolean _ouvertureDemandee = false;

    public boolean verifierOuvertureDemandee() {
        return _ouvertureDemandee;
    }

    @Override
    public void ouvrir() {
        _ouvertureDemandee = true;
    }
}
